package com.kocak.kcache;

import com.kocak.kcache.config.KCacheProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/*
 * @author Yusuf Kocak
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class KCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(KCacheApplication.class, args);
    }

    @Bean
    public ServletWebServerApplicationContext configureContextPath(ApplicationContext context, KCacheProperties properties) {
        ServletWebServerApplicationContext serverContext = (ServletWebServerApplicationContext) context;
        serverContext.setNamespace(properties.getContextPath());
        return serverContext;
    }
}
