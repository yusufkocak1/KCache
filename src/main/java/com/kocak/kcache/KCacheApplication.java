package com.kocak.kcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}
