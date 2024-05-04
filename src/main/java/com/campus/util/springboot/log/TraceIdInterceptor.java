package com.campus.util.springboot.log;

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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = TraceIdUtil.getTraceId(request);
        MDC.put(TraceIdUtil.MDC_NAME, traceId);
        response.setHeader(TraceIdUtil.HEADER_NAME, traceId);
        return true;
    }
}
