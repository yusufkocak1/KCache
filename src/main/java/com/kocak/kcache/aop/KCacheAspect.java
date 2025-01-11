package com.kocak.kcache.aop;

import com.kocak.kcache.annotations.KCacheable;
import com.kocak.kcache.annotations.KCacheEvict;
import com.kocak.kcache.annotations.KCachePut;
import com.kocak.kcache.core.KCacheManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class KCacheAspect {

    private final KCacheManager cacheManager = KCacheManager.getInstance();

    @Around("@annotation(kCacheable)")
    public Object handleKCacheable(ProceedingJoinPoint joinPoint, KCacheable kCacheable) throws Throwable {
        String key = kCacheable.key();

        if (cacheManager.containsKey(key)) {
            return cacheManager.get(key);
        }

        Object result = joinPoint.proceed();
        cacheManager.put(key, result);
        return result;
    }

    @Around("@annotation(kCachePut)")
    public Object handleKCachePut(ProceedingJoinPoint joinPoint, KCachePut kCachePut) throws Throwable {
        String key = kCachePut.key();

        Object result = joinPoint.proceed();
        cacheManager.put(key, result);
        return result;
    }

    @Before("@annotation(kCacheEvict)")
    public void handleKCacheEvict(KCacheEvict kCacheEvict) {
        String key = kCacheEvict.key();
        cacheManager.evict(key);
    }
}
