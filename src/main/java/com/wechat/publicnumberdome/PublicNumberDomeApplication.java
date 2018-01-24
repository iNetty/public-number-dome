package com.wechat.publicnumberdome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@ComponentScan //使用 @ComponentScan 注解搜索beans，自动发现和装配一些Bean。
//@EnableAutoConfiguration //开启自动配置
//@EnableScheduling //开启定时任务
@SpringBootApplication //@SpringBootApplication 注解等价于以默认属性使用 @Configuration,@EnableAutoConfiguration 和 @ComponentScan。
//@EnableJpaRepositories(basePackages = {"com.wechat.publicnumberdome.dao"}) //spring-JPA的数据操作类路径(使用JPA时启用此注解)
public class PublicNumberDomeApplication extends SpringBootServletInitializer {

    // 日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 添加Servlet的支持
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PublicNumberDomeApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PublicNumberDomeApplication.class, args);
    }

}
