package com.campus.util.springboot.log;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黄磊
 */
public class TraceIdUtil {
    /**
     * traceId在MDC中的名称
     */
    public static final String MDC_NAME = "traceId";
    /**
     * traceId在HTTP头中的名称
     */
    public static final String HEADER_NAME = "Trace-Id";
    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    /**
     * 获取请求中的TraceId，假如没有就会自动生成
     *
     * @param request 请求
     * @return TraceId
     */
    public static String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(HEADER_NAME);
        if (traceId == null) {
            traceId = getTraceId();
        }
        return traceId;
    }

    /**
     * 获取当前线程的TraceId，假如没有就会自动生成
     *
     * @return TraceId
     */
    public static String getTraceId() {
        String traceId = TRACE_ID.get();
        if (traceId == null) {
            traceId = IdUtil.randomUUID();
            TRACE_ID.set(traceId);
        }
        return traceId;
    }

    /**
     * 移除当前线程的TraceId
     */
    public static void removeTraceId() {
        TRACE_ID.remove();
    }
}
