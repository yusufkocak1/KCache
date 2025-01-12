package com.kocak.kcache.aop;

import com.kocak.kcache.annotations.KCacheable;
import com.kocak.kcache.annotations.KCacheEvict;
import com.kocak.kcache.annotations.KCachePut;
import com.kocak.kcache.core.CacheEntry;
import com.kocak.kcache.core.KCacheManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class KCacheAspect {
    private final KCacheManager cacheManager = KCacheManager.getInstance();

    @Around("@annotation(kCacheable)")
    public Object handleCache(ProceedingJoinPoint joinPoint, KCacheable kCacheable) throws Throwable {
        String key = kCacheable.key();
        long expireAfter = kCacheable.expireAfter();
        int expireAfterAccessCount = kCacheable.expireAfterAccessCount();

        Object cachedValue = cacheManager.get(key);
        if (Objects.nonNull(cachedValue)) {
            return cachedValue;
        }

        Object result = joinPoint.proceed();
        cacheManager.put(key, result, expireAfter, expireAfterAccessCount);
        return result;
    }


    @Around("@annotation(kCachePut)")
    public Object handleKCachePut(ProceedingJoinPoint joinPoint, KCachePut kCachePut) throws Throwable {
        String key = kCachePut.key();

        Object result = joinPoint.proceed();
        CacheEntry cacheEntry = cacheManager.getEntry(key);
        if(cacheEntry != null){
            cacheManager.put(key, result,cacheEntry.getExpireAfter(), cacheEntry.getAccessLimit());
        }else{
            cacheManager.put(key,result);
        }
        return result;
    }

    @Before("@annotation(kCacheEvict)")
    public void handleKCacheEvict(KCacheEvict kCacheEvict) {
        String key = kCacheEvict.key();
        cacheManager.evict(key);
    }
}
