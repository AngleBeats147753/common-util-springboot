package com.campus.util.springboot.util.springboot.log;

import cn.hutool.json.JSONObject;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Slf4j
public class LogAOP {
    private static final String LOG_TEMPLATE = "[{}] 请求方式：{}, 请求路径：{}, 接口名：{}, 用户Id：{}, 异常信息：{}, 请求参数：{}";
    private static final String NO_LOGIN_LOG_TEMPLATE = "[{}] 请求方式：{}, 请求路径：{}, 接口名：{}, 异常信息：{}, 请求参数：{}";
    private final Boolean hasLoginModule;

    {
        Boolean hasLoginModule;
        try {
            Class.forName("cn.dev33.satoken.stp.StpUtil");
            hasLoginModule = true;
        } catch (ClassNotFoundException e) {
            hasLoginModule = false;
        }
        this.hasLoginModule = hasLoginModule;
    }

    @Pointcut("@annotation(com.eggcampus.util.spring.log.Log)")
    public void logPointcut() {
    }

    @Around(value = "logPointcut()")
    public Object run(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            ReturnResult result = (ReturnResult) joinPoint.proceed(joinPoint.getArgs());
            this.log(joinPoint, result);
            return result;
        } catch (Throwable e) {
            this.log(joinPoint, e);
            throw e;
        }
    }

    private void log(ProceedingJoinPoint joinPoint, ReturnResult result) {
        if (result.getStatus() == AliErrorCode.SUCCESS) {
            this.log(true, joinPoint, "");
        } else {
            this.log(false, joinPoint, result.getMessage());
        }
    }

    private void log(ProceedingJoinPoint joinPoint, Throwable e) {
        this.log(false, joinPoint, e.getMessage());
    }

    private void log(boolean success, ProceedingJoinPoint joinPoint, String errorMessage) {
        HttpServletRequest request = getHttpRequest();
        String status = success ? "成功" : "失败";

        if (hasLoginModule) {
            log.info(LOG_TEMPLATE,
                    status,
                    request.getMethod(),
                    request.getRequestURI(),
                    getName(joinPoint),
                    getUserId(),
                    errorMessage,
                    getParam(joinPoint));
        } else {
            log.info(NO_LOGIN_LOG_TEMPLATE,
                    status,
                    request.getMethod(),
                    request.getRequestURI(),
                    getName(joinPoint),
                    errorMessage,
                    getParam(joinPoint));
        }
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
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            Log controllerLog = realMethod.getAnnotation(Log.class);
            return controllerLog.value();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUserId() {
        try {
            Class<?> stpUtilClass = Class.forName("cn.dev33.satoken.stp.StpUtil");
            Method getLoginIdAsString = stpUtilClass.getMethod("getLoginId", Object.class);
            return (String) getLoginIdAsString.invoke(null, "用户未登录");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getParam(ProceedingJoinPoint joinPoint) {
        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        JSONObject params = new JSONObject();
        for (int i = 0; i < paramNames.length; i++) {
            if (paramValues[i] instanceof BindingResult) {
                continue;
            }
            params.set(paramNames[i], paramValues[i]);
        }
        return params.toString();
    }
}
