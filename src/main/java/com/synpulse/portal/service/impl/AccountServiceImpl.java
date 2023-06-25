package com.synpulse.portal.service.impl;

import com.synpulse.portal.config.LoginUserUtil;
import com.synpulse.portal.dto.AccountQuery;
import com.synpulse.portal.dto.Result;
import com.synpulse.portal.exception.BusinessException;
import com.synpulse.portal.exception.ErrorCode;
import com.synpulse.portal.jwt.JwtUtil;
import com.synpulse.portal.po.Account;
import com.synpulse.portal.po.Currency;
import com.synpulse.portal.repository.AccountRepository;
import com.synpulse.portal.repository.condition.AccountCondition;
import com.synpulse.portal.service.AccountService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Result accountPageList(Integer pageNum, Integer pageSize, String userName) {
        AccountCondition accountCondition = new AccountCondition();
        accountCondition.setCurrent(pageNum);
        accountCondition.setSize(pageSize);
        accountCondition.setUserName(userName);

        IPage<Account> page = accountRepository.pageList(accountCondition);

        return Result.success(page.getRecords());
    }

    @Override
    public Result getById() {
        return Result.success(accountRepository.getById(LoginUserUtil.getLoginUserId()));
    }

    @Override
    public Result deleteAccount() {
        accountRepository.removeById(LoginUserUtil.getLoginUserId());
        return Result.success();
    }

    @Override
    public Result createAccount(AccountQuery accountQuery) {
        Account account = new Account();

        account.setUserName(accountQuery.getUserName());
        account.setUserPassword(accountQuery.getUserPwd());
        account.setCreateTime(LocalDateTime.now());
        account.setBalance("0");
        account.setAccountCode(UUID.randomUUID().toString().replace("-",""));
        account.setCurrency(Currency.CNY.name());

        accountRepository.save(account);

        return Result.success(account);
    }

    @Override
    public Result updateTAccount(AccountQuery accountQuery) {

        Account account = accountRepository.getById(LoginUserUtil.getLoginUserId());

        account.setUserName(accountQuery.getUserName());
        account.setBalance(accountQuery.getBalance());
        account.setCurrency(accountQuery.getCurrency());

        accountRepository.updateById(account);

        return Result.success(account);
    }

    @Override
    public Result login(AccountQuery accountQuery) throws BusinessException {
        Account account = accountRepository.login(accountQuery);

        if(account == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        LoginUserUtil.setLoginUserId(account.getId());

        return Result.success(JwtUtil.sign(account.getId().toString()));
    }
}
