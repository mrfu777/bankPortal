package com.synpulse.portal.config;

import com.synpulse.portal.exception.BusinessException;
import com.synpulse.portal.exception.ErrorCode;
import com.synpulse.portal.jwt.JwtUtil;
import com.synpulse.portal.jwt.Security;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws BusinessException {
        // get token from request header
        String token = httpServletRequest.getHeader("token");

        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod= (HandlerMethod)object;

        Method method=  handlerMethod.getMethod();
        //check token
        if (method.isAnnotationPresent(Security.class)) {
            Security Security = method.getAnnotation(Security.class);
            if (Security.required()) {
                if (token == null) {
                    throw new BusinessException(ErrorCode.INVALID_TOKEN);
                }

                try {
                    JwtUtil.checkSign(token);
                    LoginUserUtil.setLoginUserId(Long.parseLong(JwtUtil.getUserId(token)));
                } catch (Exception e) {
                    throw new BusinessException(ErrorCode.INVALID_TOKEN);
                }
            }
        }

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //do nothing
    }
}
