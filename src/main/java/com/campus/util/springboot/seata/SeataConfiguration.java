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
    /**
     * Feign拦截器，用于发送请求前当前将XID放入请求头中
     */
    @Bean
    public RequestInterceptor seataIdRequestInterceptor() {
        return new SendSeataIdInterceptor();
    }

    /**
     * 拦截器，用于处理请求前将XID放入请求头中
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ReceiveSeataIdInterceptor());
    }

    public static class SendSeataIdInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            String xid = SeataIdUtil.getXid();
            if (xid != null) {
                template.header(SeataIdUtil.HEADER_NAME, SeataIdUtil.getXid());
            }
        }
    }

    public static class ReceiveSeataIdInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String xid = SeataIdUtil.getXid(request);
            if (xid == null) {
                return true;
            }
            SeataIdUtil.bind(xid);
            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            SeataIdUtil.unbind();
        }
    }
}
