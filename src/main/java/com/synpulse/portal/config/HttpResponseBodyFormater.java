package com.synpulse.portal.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpResponseBodyFormater
 */
@Configuration
public class HttpResponseBodyFormater {
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        fastConverter.setFastJsonConfig(config);
        return fastConverter;
    }
}
