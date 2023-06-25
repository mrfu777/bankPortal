package com.synpulse.portal.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @date 2023/6/16
 * @desc
 */
@Data
public class TransactionQuery extends PageQuery {
    private String guid;
    @NotBlank
    private String amount;
    @NotBlank
    private String sourceBankCode;
    @NotNull
    private Long sourceId;
    @NotNull
    private Long accountId;
}
