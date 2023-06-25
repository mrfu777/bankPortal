package com.synpulse.portal.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.synpulse.portal.dao.AccountMapper;
import com.synpulse.portal.dto.AccountQuery;
import com.synpulse.portal.po.Account;
import com.synpulse.portal.repository.AccountRepository;
import com.synpulse.portal.repository.condition.AccountCondition;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class AccountRepositoryImpl extends ServiceImpl<AccountMapper, Account> implements AccountRepository {

    @Override
    public IPage<Account> pageList(AccountCondition accountCondition) {
        LambdaQueryWrapper<Account> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StrUtil.isNotBlank(accountCondition.getUserName()),Account::getUserName,accountCondition.getUserName());
        wrapper.eq(StrUtil.isNotBlank(accountCondition.getUserPwd()),Account::getUserPassword,accountCondition.getUserPwd());

        return super.page(accountCondition,wrapper);
    }

    @Override
    public void updateBalance(Account... source) {
        for (Account account : source) {
            LambdaUpdateWrapper<Account> wrapper = Wrappers.lambdaUpdate();
            wrapper.set(Account::getBalance,account.getBalance());
            wrapper.eq(Account::getId,account.getId());
            super.update(wrapper);
        }
    }

    @Override
    public Account login(AccountQuery accountQuery) {
        LambdaQueryWrapper<Account> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StrUtil.isNotBlank(accountQuery.getUserName()),Account::getUserName,accountQuery.getUserName());
        wrapper.eq(StrUtil.isNotBlank(accountQuery.getUserPwd()),Account::getUserPassword,accountQuery.getUserPwd());

        return super.getOne(wrapper);
    }
}
