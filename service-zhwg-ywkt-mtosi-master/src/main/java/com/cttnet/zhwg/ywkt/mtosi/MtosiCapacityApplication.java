package com.cttnet.zhwg.ywkt.mtosi;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.cttnet.common.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 *  南向能力启动类
 * </pre>
 *
 * @author zhangyaomin
 * @date 2019/12/28
 * @since java 1.8
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.cttnet")
@MapperScan(basePackages = "com.cttnet.zhwg.ywkt.**.mapper")
@EnableEurekaClient
@EnableSwagger2
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MtosiCapacityApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MtosiCapacityApplication.class, args);
    }

    /**
     * mybatis-plus分页插件，启动类必须添加
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
