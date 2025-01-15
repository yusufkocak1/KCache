package com.kocak.kcache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/*
 * @author Yusuf Kocak
 */
@Configuration
@ConfigurationProperties(prefix = "kcache")
public class KCacheProperties {

    private String contextPath = "/kcache";
    private String port = "5858";

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
