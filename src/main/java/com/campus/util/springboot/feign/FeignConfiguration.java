package com.campus.util.springboot.feign;

import com.campus.util.springboot.exception.ErrorMessage;
import com.campus.util.springboot.log.TraceIdUtil;
import com.eggcampus.util.exception.result.FeignException;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author 黄磊
 */
@Slf4j
@Configuration
@EnableFeignClients
public class FeignConfiguration {
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Decoder feignDecoder() {
        return new EggCampusResponseEntityDecoder(objectMapper);
    }

    @Bean
    public RequestInterceptor traceIdRequestInterceptor() {
        return new TraceIdRequestInterceptor();
    }


    public static class FeignErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            System.out.printf("当前状态是<%s>%n", response.status());
            String message = "";
            try {
                if (response.body() != null) {
                    message = Util.toString(response.body().asReader(Util.UTF_8));
                }
            } catch (Exception exception) {
                log.error("解析Feign异常信息失败", exception);
                message = exception.getLocalizedMessage();
            }
            return new FeignException(AliErrorCode.SERVICE_ERROR_C0001, ErrorMessage.UNKNOWN, message);
        }
    }

    public static class EggCampusResponseEntityDecoder implements Decoder {
        private final ObjectMapper objectMapper;

        public EggCampusResponseEntityDecoder(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Object decode(final Response response, Type type) throws IOException, FeignException {
            if (response.status() != 200) {
                throw new RuntimeException("异常返回");
            }
            ReturnResult result = objectMapper.readValue(response.body().asInputStream(), ReturnResult.class);
            if (!AliErrorCode.SUCCESS.equals(result.getStatus())) {
                throw new FeignException(result);
            }
            return result;
        }
    }

    public static class TraceIdRequestInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            template.header(TraceIdUtil.HEADER_NAME, TraceIdUtil.getTraceId());
        }
    }
}
