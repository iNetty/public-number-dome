package com.wechat.publicnumberdome.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * direction web配置
 * create by lpf on 2017/9/24 18:16
 */
@Configuration
public class WebConfiguration {

    // 日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    /**
     * 过滤器
     */
    @Bean
    public FilterRegistrationBean ssxFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SSXFilter());
        // registration.addUrlPatterns("/*"); // 拦截全部请求
        registration.addUrlPatterns("/lpf/*");

        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 自定义过滤器(过滤SSX威胁字符)
     */
    public class SSXFilter implements Filter {
        @Override
        public void destroy() {
            // TODO Auto-generated method stub
        }

        @Override
        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
                throws IOException, ServletException {
            // TODO Auto-generated method stub
            HttpServletRequest request = (HttpServletRequest) srequest;
            String path = request.getServletPath();
            logger.info("对请求url :" + request.getRequestURI() + " 过滤非法字符");
            //System.out.println("对请求url :" + request.getRequestURI() + " 过滤非法字符");
            filterChain.doFilter(srequest, sresponse);
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
            // TODO Auto-generated method stub
        }
    }

//    /**
//     * 配置404、500等常见错误页面
//     */
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return (container -> {
//            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, " 404");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "404");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "500");
//            container.addErrorPages(error401Page, error404Page, error500Page);
//        });
//    }
}
