package com.synpulse.portal.repository.condition;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class TransactionRecordCondition extends Page {
    private Integer pageSize;
    private Integer pageNum;
    private String guid;
    private Integer action;
    private String targetCurrency;
    private String sourceCurrency;
    private Integer accountId;
}
