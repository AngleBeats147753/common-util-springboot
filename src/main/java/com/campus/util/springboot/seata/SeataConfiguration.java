package com.campus.util.springboot.seata;

import com.campus.util.springboot.feign.EnableEggCampusFeign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 黄磊
 */
@Slf4j
@Configuration
@EnableEggCampusFeign
public class SeataConfiguration implements WebMvcConfigurer {
    @Bean
    public RequestInterceptor seataIdRequestInterceptor() {
        return new SeataIdRequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SeataIdRequestInterceptor1());
    }

    public static class SeataIdRequestInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            template.header(SeataIdUtil.HEADER_NAME, SeataIdUtil.getXid());
        }
    }

    public static class SeataIdRequestInterceptor1 implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String xid = SeataIdUtil.getXid(request);
            if (xid == null) {
                return true;
            }
            SeataIdUtil.bind(xid);
            return true;
        }
    }
}
