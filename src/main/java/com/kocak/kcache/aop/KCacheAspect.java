package com.kocak.kcache.aop;

import com.kocak.kcache.annotations.KCacheable;
import com.kocak.kcache.core.CacheStore;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class KCacheAspect {
    private final CacheStore cacheStore = new CacheStore();

    @Around("@annotation(kCacheable)")
    public Object handleCache(ProceedingJoinPoint joinPoint, KCacheable kCacheable) throws Throwable {
        String key = kCacheable.key();
        String cacheName = kCacheable.cacheName();
        long expireAfter = kCacheable.expireAfter();
        int expireAfterAccessCount = kCacheable.expireAfterAccessCount();

        Object cachedValue = cacheStore.get(cacheName + ":" + key);
        if (Objects.nonNull(cachedValue)) {
            return cachedValue;
        }

        Object result = joinPoint.proceed();
        cacheStore.put(cacheName + ":" + key, result, expireAfter, expireAfterAccessCount);
        return result;
    }
}
