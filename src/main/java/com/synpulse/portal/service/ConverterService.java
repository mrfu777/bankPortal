package com.synpulse.portal.service;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @desc
 */
@Service
public class ConverterService {

    /**
     * USD
     * CNY
     * GBP
     * EUR
     * HKD
     * MOP
     * JPY
     * CAD
     * AUD
     * SGD
     * KRW
     * PHP
     * THB
     * CHF
     * NZD
     * DKK
     * NOK
     * SEK
     * RUB
     * MYR
     * ZAR
     */
    private static final String URL = "http://api.it120.cc/gooking/forex/rate?fromCode={0}&toCode={1}";
    public ApiResult convert(String sourceCurrency, String targetCurrency) {
        String realUrl = MessageFormat.format(URL, sourceCurrency, targetCurrency);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(realUrl)
                .get()
                .build();
        Response response;

        try {
            response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string(),ApiResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class ApiResult{
        private String code;
        private String msg;
        private ResultData data;
    }

    @Data
    public static class ResultData{
        private String rate;
        private String toCode;
        private String fromCode;
    }
}
