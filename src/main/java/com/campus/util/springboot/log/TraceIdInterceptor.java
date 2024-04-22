package com.campus.util.springboot.log;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TraceId拦截器
 * <p>
 * 假如请求的header中没有Trace-Id，那么就生成一个Trace-Id，并且放入日志MDC、返回消息的header中
 * </p>
 *
 * @author 黄磊
 */
public class TraceIdInterceptor implements HandlerInterceptor {
    @Getter
    private static final String NAME = "traceId";
    @Getter
    private static final String Header = "Trace-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(Header);
        if (traceId == null) {
            traceId = IdUtil.randomUUID();
        }
        MDC.put(NAME, traceId);
        response.setHeader(Header, traceId);
        return true;
    }

}
