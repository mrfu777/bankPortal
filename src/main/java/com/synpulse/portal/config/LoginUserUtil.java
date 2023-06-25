package com.synpulse.portal.config;

public class LoginUserUtil {
    private static ThreadLocal<Long> loginUser = new ThreadLocal<Long>();

    public static Long getLoginUserId(){
        return loginUser.get();
    }

    public static void setLoginUserId(Long userId){
        loginUser.set(userId);
    }
}
