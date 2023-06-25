package com.synpulse.portal.repository;


import com.synpulse.portal.dto.AccountQuery;
import com.synpulse.portal.po.Account;
import com.synpulse.portal.repository.condition.AccountCondition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface AccountRepository extends IService<Account> {

    IPage<Account> pageList(AccountCondition accountCondition);

    void updateBalance(Account ...source);

    Account login(AccountQuery accountQuery);
}
