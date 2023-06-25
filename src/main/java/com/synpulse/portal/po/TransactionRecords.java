package com.synpulse.portal.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * entity tranction record
 */
@TableName("transaction_records")
@Data
public class TransactionRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Transaction unique id
     */
    private String transactionGuid;

    private LocalDateTime createTime;

    /**
     * source bank code
     */
    private String sourceBankCode;

    /**
     * transaction amount

     */
    private String amount;
    private Long accountId;
    private Long sourceId;
    /**
     * currencyType

     */
    private String targetCurrency;
    private String sourceCurrency;
    /**
     * TransactionType

     */
    private Boolean isSuccess;
    private String errorCode;
}
