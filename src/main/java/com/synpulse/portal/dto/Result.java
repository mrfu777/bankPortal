package com.synpulse.portal.dto;

import com.synpulse.portal.exception.ErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Result<T> {

    private String msg;
    private T data;
    private Integer code;

    private Long total;

    public static Result success(){
        return new Result().setCode(1000).setMsg("success");
    }

    public static <T> Result success(T data){
        return new Result().setCode(1000).setMsg("success").setData(data);
    }
    public static <T> Result success(T data,Long total){
        return new Result().setCode(1000).setMsg("success").setData(data).setTotal(total);
    }

    public static Result fail(Integer code, String msg){
        return new Result().setCode(code).setMsg(msg);
    }

    public static Result fail(){
        return fail(ErrorCode.BUSY);
    }

    public static Result fail(ErrorCode errorCode){
        return new Result().setCode(errorCode.getCode()).setMsg(errorCode.getMsg());
    }
}
