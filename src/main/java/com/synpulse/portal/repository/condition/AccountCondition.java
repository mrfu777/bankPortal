package com.synpulse.portal.repository.condition;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * AccountCondition
 */
@Data
public class AccountCondition extends Page {
    private String userName;
    private String userPwd;
}
