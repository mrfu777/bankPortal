package com.synpulse.portal.message.msg;

import lombok.Data;

/**
 */
@Data
public class TransactionMsg {
    private String targetCurrency;
    private String guid;
    private String sourceCurrency;
    private String amount;
    private String sourceBankCode;
    private Long sourceId;

    private Long accountId;
}
