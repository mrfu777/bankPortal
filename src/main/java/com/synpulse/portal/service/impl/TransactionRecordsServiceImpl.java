package com.synpulse.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.synpulse.portal.config.LoginUserUtil;
import com.synpulse.portal.dto.Result;
import com.synpulse.portal.dto.TransactionQuery;
import com.synpulse.portal.exception.BusinessException;
import com.synpulse.portal.exception.ErrorCode;
import com.synpulse.portal.message.msg.TransactionMsg;
import com.synpulse.portal.po.Account;
import com.synpulse.portal.po.TransactionRecords;
import com.synpulse.portal.repository.AccountRepository;
import com.synpulse.portal.repository.TransactionRecordsRepository;
import com.synpulse.portal.repository.condition.TransactionRecordCondition;
import com.synpulse.portal.service.ConverterService;
import com.synpulse.portal.service.TransactionRecordsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 */
@Service
@Slf4j
public class TransactionRecordsServiceImpl implements TransactionRecordsService {

    @Autowired
    private TransactionRecordsRepository transactionRecordsRepository;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private static final String TOPIC = "transactionTopic";

    @Override
    public void handlerMsg(String value) {
        TransactionMsg TransactionMsg = null;
        try {
            TransactionMsg = JSON.parseObject(value, TransactionMsg.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        TransactionRecords transactionRecords = new TransactionRecords();

        transactionRecords.setAccountId(TransactionMsg.getAccountId());
        transactionRecords.setAmount(TransactionMsg.getAmount());
        transactionRecords.setSourceCurrency(TransactionMsg.getSourceCurrency());
        transactionRecords.setTargetCurrency(TransactionMsg.getTargetCurrency());
        transactionRecords.setSourceBankCode(TransactionMsg.getSourceBankCode());
        transactionRecords.setCreateTime(LocalDateTime.now());
        transactionRecords.setSourceId(TransactionMsg.getSourceId());
        transactionRecords.setTransactionGuid(TransactionMsg.getGuid());

        try {
            doTransaction(transactionRecords);
            transactionRecords.setIsSuccess(true);
        }catch (BusinessException be){
            transactionRecords.setErrorCode(String.valueOf(be.getCode()));
            transactionRecords.setIsSuccess(false);
        }catch (Exception e) {
            transactionRecords.setErrorCode(String.valueOf(ErrorCode.TRANSACTION_FAIL.getCode()));
            log.error("Transaction fail:{}",e);
            transactionRecords.setIsSuccess(false);
        }

        transactionRecordsRepository.save(transactionRecords);
    }

    /**
     * do transaction process
     * @param transactionRecords
     * @throws BusinessException
     */
    public void doTransaction(TransactionRecords transactionRecords) throws BusinessException {
        Long sourceId = transactionRecords.getSourceId();
        Long accountId = transactionRecords.getAccountId();

        Account source = accountRepository.getById(sourceId);
        Account target = accountRepository.getById(accountId);

        if(source == null || target == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        String sourceAmount = "";
        if(!source.getCurrency().equals(target.getCurrency())) {
            //same currency don't need convert
            //different current
            sourceAmount = convert(source.getCurrency(),target.getCurrency(),transactionRecords.getAmount());
        }else{
            sourceAmount = transactionRecords.getAmount();
        }
        //subtract source account balance
        subtract(source,transactionRecords.getAmount());
        //plus target account balance
        plus(target,sourceAmount);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountRepository.updateBalance(source,target);
            }
        });
    }

    private void plus(Account target, String afterConvert) {
        target.setBalance(String.valueOf(Long.parseLong(target.getBalance()) + Long.parseLong(afterConvert)));
    }

    private void subtract(Account source, String amount) throws BusinessException {

        Long l1 = Long.parseLong(source.getBalance());
        Long l2 = Long.parseLong(amount);

        if(l2.compareTo(l1) > 0) {
            throw new BusinessException(ErrorCode.BANLANCE_NOT_ENOUGH);
        }else{
           source.setBalance(String.valueOf(l1 - l2));
        }
    }

    private String convert(String sourceCurrency, String targetCurrency, String amount) {
        ConverterService.ApiResult result = converterService.convert(sourceCurrency, targetCurrency);
        String rate = result.getData().getRate();

        log.info("convert sourceCurrency to targetCurrency rate:{}",rate);
        BigDecimal b1 = new BigDecimal(rate);
        BigDecimal b2 = new BigDecimal(amount);

        BigDecimal multiply = b2.divide(b1,BigDecimal.ROUND_HALF_UP);

        return String.valueOf(multiply.longValue());
    }

    @Override
    public Result transactions(TransactionQuery TransactionQuery) {
        TransactionRecordCondition TransactionRecordCondition = new TransactionRecordCondition();

        BeanUtils.copyProperties(TransactionQuery,TransactionRecordCondition);

        IPage<TransactionRecords> list = transactionRecordsRepository.pageList(TransactionRecordCondition);

        return Result.success(list.getRecords());
    }

    @Override
    public Result createTransactions(TransactionQuery transactionQuery) throws BusinessException {
        if(transactionQuery.getAccountId().equals(LoginUserUtil.getLoginUserId())) {
            throw new BusinessException(ErrorCode.SAME_ACCOUNT);
        }

        Account source = accountRepository.getById(transactionQuery.getSourceId());
        Account target = accountRepository.getById(transactionQuery.getAccountId());

        if(source == null || target == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Long l = Long.parseLong(source.getBalance());
        Long l2 = Long.parseLong(transactionQuery.getAmount());

        if(l.compareTo(l2) < 0){
            throw new BusinessException(ErrorCode.BANLANCE_NOT_ENOUGH);
        }

        TransactionMsg transactionMsg = new TransactionMsg();

        String guid = UUID.randomUUID().toString().replaceAll("-","");
        transactionMsg.setGuid(guid);
        transactionMsg.setAccountId(transactionQuery.getAccountId());
        transactionMsg.setAmount(transactionQuery.getAmount());
        transactionMsg.setSourceBankCode(transactionQuery.getSourceBankCode());
        transactionMsg.setSourceCurrency(source.getCurrency());
        transactionMsg.setTargetCurrency(target.getCurrency());
        transactionMsg.setSourceId(LoginUserUtil.getLoginUserId());
        kafkaTemplate.send(TOPIC,guid,JSON.toJSONString(transactionMsg));

        return Result.success();
    }

    @Override
    public Result getById(String guid) {
        return Result.success(transactionRecordsRepository.getByGuid(guid));
    }

    @Override
    public Result deleteTransactions(String guid) {
        transactionRecordsRepository.deleteByGuid(guid);
        return Result.success();
    }
}
