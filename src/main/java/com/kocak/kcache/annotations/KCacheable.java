package com.kocak.kcache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KCacheable {
    String cacheName();
    String key();
    long expireAfter() default -1; // Süre (ms), varsayılan -1 (sınırsız süre)
    int expireAfterAccessCount() default -1; // Kullanım sayısı, varsayılan -1 (sınırsız kullanım)
}
