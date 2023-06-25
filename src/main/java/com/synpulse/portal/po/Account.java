package com.synpulse.portal.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user name
     */
    private String userName;

    /**
     * user password
     */
    private String userPassword;

    /**
     * created time
     */
    private LocalDateTime createTime;

    /**
     * unique code
     */
    private String accountCode;

    /**
     * balance    12000 7.1
     */
    private String balance;

    private String currency;
}
