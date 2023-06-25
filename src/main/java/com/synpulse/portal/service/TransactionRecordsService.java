package com.synpulse.portal.service;

import com.synpulse.portal.dto.Result;
import com.synpulse.portal.dto.TransactionQuery;
import com.synpulse.portal.exception.BusinessException;

/**
 * <p>
 *  service
 * </p>
 */
public interface TransactionRecordsService{

    void handlerMsg(String value);

    Result transactions(TransactionQuery TransactionQuery);

    Result getById(String guid);

    Result deleteTransactions(String guid);

    Result createTransactions(TransactionQuery transactionQuery) throws BusinessException;
}
