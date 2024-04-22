package com.campus.util.springboot.util.springboot.log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 使请求体可重复读取的过滤器
 *
 * @author 黄磊
 */
@WebFilter(urlPatterns = "/*")
public class ReReadableRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && "application/json".equals(request.getContentType())) {
            ReReadableHttpServletRequestWrapper wrappedRequest = new ReReadableHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(wrappedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public static class ReReadableHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final byte[] body;

        public ReReadableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            InputStream inputStream = request.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            body = byteArrayOutputStream.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

            return new ServletInputStream() {
                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }
}
