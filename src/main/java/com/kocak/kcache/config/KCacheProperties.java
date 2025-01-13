package com.kocak.kcache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/*
 * @author Yusuf Kocak
 */
@Component
@ConfigurationProperties(prefix = "kcache")
public class KCacheProperties {

    private String contextPath = "/kcache";

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
