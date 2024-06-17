package com.campus.util.springboot.seata;

import io.seata.core.context.RootContext;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * XId工具
 *
 * @author 黄磊
 */
public class SeataIdUtil {
    public static final String HEADER_NAME = "XID";

    /**
     * 获取 XID
     */
    @Nullable
    public static String getXid() {
        return RootContext.getXID();
    }

    /**
     * 从请求头中获取 XID
     */
    @Nullable
    public static String getXid(HttpServletRequest request) {
        return request.getHeader(HEADER_NAME);
    }

    /**
     * 绑定 XID
     */
    public static void bind(String xid) {
        RootContext.bind(xid);
    }

    /**
     * 解绑 XID
     */
    public static void unbind() {
        RootContext.unbind();
    }
}
