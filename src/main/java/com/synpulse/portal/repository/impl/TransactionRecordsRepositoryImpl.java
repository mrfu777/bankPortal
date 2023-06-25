package com.synpulse.portal.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.synpulse.portal.dao.TransactionRecordsMapper;
import com.synpulse.portal.po.TransactionRecords;
import com.synpulse.portal.repository.TransactionRecordsRepository;
import com.synpulse.portal.repository.condition.TransactionRecordCondition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class TransactionRecordsRepositoryImpl extends ServiceImpl<TransactionRecordsMapper, TransactionRecords> implements TransactionRecordsRepository {


    @Override
    public IPage<TransactionRecords> pageList(TransactionRecordCondition TransactionRecordCondition) {
        LambdaQueryWrapper<TransactionRecords> wrapper = Wrappers.lambdaQuery();

        wrapper.like(StrUtil.isNotBlank(TransactionRecordCondition.getSourceCurrency()),TransactionRecords::getSourceCurrency,TransactionRecordCondition.getSourceCurrency());
        wrapper.like(StrUtil.isNotBlank(TransactionRecordCondition.getTargetCurrency()),TransactionRecords::getTargetCurrency,TransactionRecordCondition.getTargetCurrency());
        wrapper.like(StrUtil.isNotBlank(TransactionRecordCondition.getGuid()),TransactionRecords::getTransactionGuid,TransactionRecordCondition.getGuid());

        return super.page(TransactionRecordCondition,wrapper);
    }

    @Override
    public Object getByGuid(String guid) {
        LambdaQueryWrapper<TransactionRecords> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(TransactionRecords::getTransactionGuid,guid);

        return super.getOne(wrapper);
    }

    @Override
    public void deleteByGuid(String guid) {
        LambdaQueryWrapper<TransactionRecords> wrapper = new LambdaQueryWrapper();

        wrapper.eq(TransactionRecords::getTransactionGuid,guid);

        super.baseMapper.delete(wrapper);
    }
}
