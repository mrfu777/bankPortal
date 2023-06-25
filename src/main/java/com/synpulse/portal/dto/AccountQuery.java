package com.synpulse.portal.dto;

import lombok.Data;

/**
 * @date 2023/6/16
 * @desc
 */
@Data
public class AccountQuery extends PageQuery {
    private String userName;
    private String userPwd;
    private String balance;

    private String currency;


}
