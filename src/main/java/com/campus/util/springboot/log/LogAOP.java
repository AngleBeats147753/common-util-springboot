package com.campus.util.springboot.log;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Order(1)不要去掉，因为这个AOP要在其他AOP之前执行
 */
@Aspect
@Slf4j
@Order(1)
public class LogAOP {
    private static final String LOG_TEMPLATE = "[{}] 请求方式：{}， 请求路径：{}， 接口名：{}， 用户Id：{}， 异常信息：{}， 请求参数：{}， 请求体类型：{}，请求体：{}";
    private final Boolean hasSaToken;
    private final Method getLoginIdAsString;

    {
        Boolean hasSaToken = false;
        Method getLoginIdAsString = null;
        try {
            Class<?> stpUtilClass = Class.forName("cn.dev33.satoken.stp.StpUtil");
            hasSaToken = true;
            getLoginIdAsString = stpUtilClass.getMethod("getLoginId", Object.class);
        } catch (Exception e) {
            log.info("未找到SaToken，日志将无法获取用户Id");
        }
        this.hasSaToken = hasSaToken;
        this.getLoginIdAsString = getLoginIdAsString;
    }

    @Pointcut("@annotation(com.campus.util.springboot.log.Log)")
    public void logPointcut() {
    }

    @Around(value = "logPointcut()")
    public Object run(ProceedingJoinPoint joinPoint) throws Throwable {
        LogMessage message = getRequestMessage(joinPoint);
        try {
            ReturnResult result = (ReturnResult) joinPoint.proceed(joinPoint.getArgs());
            fillResponseMessage(message, result);
            return result;
        } catch (Throwable e) {
            fillResponseMessage(message, e);
            throw e;
        } finally {
            log.info(LOG_TEMPLATE,
                    message.getStatus(),
                    message.getMethod(),
                    message.getUri(),
                    message.getName(),
                    message.getUserId(),
                    message.getErrorMessage(),
                    message.getParams(),
                    message.getContentType(),
                    message.getBody());
        }
    }

    private LogMessage getRequestMessage(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = getHttpRequest();
        LogMessage message = new LogMessage();
        message.setMethod(request.getMethod());
        message.setUri(request.getRequestURI());
        message.setName(getName(joinPoint));
        message.setUserId(getUserId());
        message.setParams(getParam(request));
        message.setContentType(getContentType(request));
        message.setBody(getBody(request));
        return message;
    }

    private void fillResponseMessage(LogMessage message, ReturnResult result) {
        if (result.getStatus() == AliErrorCode.SUCCESS) {
            message.setStatus("成功");
        } else {
            message.setStatus("失败");
            message.setErrorMessage(result.getMessage());
        }
    }

    private void fillResponseMessage(LogMessage message, Throwable e) {
        message.setStatus("失败");
        message.setErrorMessage(e.getMessage());
    }

    private HttpServletRequest getHttpRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return attributes.getRequest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getName(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log sysLog = method.getAnnotation(Log.class);
        return sysLog.value();
    }

    private String getUserId() {
        if (hasSaToken) {
            try {
                return (String) this.getLoginIdAsString.invoke(null, "用户未登录");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return "无SaToken";
        }
    }

    private String getParam(HttpServletRequest request) {
        JSONObject params = new JSONObject();
        for (Map.Entry<String, String[]> entry : ServletUtil.getParams(request).entrySet()) {
            String[] value = entry.getValue();
            if (value.length == 0) {
                params.set(entry.getKey(), null);
            } else if (value.length == 1) {
                params.set(entry.getKey(), value[0]);
            } else {
                params.set(entry.getKey(), value);
            }
        }
        return params.isEmpty() ? "无" : JSONUtil.toJsonStr(params);
    }

    private String getContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType == null ? "无" : contentType;
    }

    private String getBody(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (request.getContentType() == null && request.getContentLength() == -1) {
            return "无";
        } else if ("application/json".equals(contentType) ||
                "application/json;charset=UTF-8".equals(contentType)) {
            return LogUtil.maskPrivacyData(ServletUtil.getBody(request));
        } else if ("application/x-www-form-urlencoded".equals(contentType) ||
                "application/x-www-form-urlencoded;charset=UTF-8".equals(contentType) ||
                "application/octet-stream".equals(contentType) ||
                "multipart/form-data".equals(contentType) ||
                "application/xml".equals(contentType) ||
                "txt/plain".equals(contentType)) {
            return "暂不支持查看";
        }
        throw new IllegalArgumentException("使用了不支持的contentType<%s>，路径<%s>".formatted(contentType, request.getRequestURI()));
    }

    @Data
    private static class LogMessage {
        private String status;
        private String method;
        private String uri;
        private String name;
        private String userId;
        private String errorMessage;
        private String params;
        private String contentType;
        private String body;
    }
}
