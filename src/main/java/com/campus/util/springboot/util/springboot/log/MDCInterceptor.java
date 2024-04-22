package com.campus.util.springboot.util.springboot.log;

import cn.hutool.core.util.IdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 黄磊
 */
public class MDCInterceptor implements HandlerInterceptor {
    private static final String NAME = "traceId";
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
