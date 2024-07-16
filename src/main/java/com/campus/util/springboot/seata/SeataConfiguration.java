package com.campus.util.springboot.seata;

import com.campus.util.springboot.feign.EnableEggCampusFeign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Seata配置
 * <p>
 *     <ul>
 *         <li>发送xid：使用Feign发送请求前，传递xid</li>
 *         <li>接收xid：接收不用管，seata包中有TransactionPropagationInterceptor类来接收xid，这个类会自动注入的</li>
 *     </ul>
 * </p>
 *
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

    public static class SendSeataIdInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            String xid = SeataIdUtil.getXid();
            if (xid != null) {
                template.header(RootContext.KEY_XID, SeataIdUtil.getXid());
            }
        }
    }
}
