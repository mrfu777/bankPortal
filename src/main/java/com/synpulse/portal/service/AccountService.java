package com.synpulse.portal.service;

import com.synpulse.portal.dto.AccountQuery;
import com.synpulse.portal.dto.Result;
import com.synpulse.portal.exception.BusinessException;

public interface AccountService {
    Result accountPageList(Integer pageNum, Integer pageSize, String userName);

    Result getById();

    Result deleteAccount();

    Result createAccount(AccountQuery accountQuery);

    Result updateTAccount(AccountQuery accountQuery);

    Result login(AccountQuery accountQuery) throws BusinessException;
}
