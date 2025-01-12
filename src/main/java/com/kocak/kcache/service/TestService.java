package com.kocak.kcache.service;

import com.kocak.kcache.annotations.KCacheEvict;
import com.kocak.kcache.annotations.KCachePut;
import com.kocak.kcache.annotations.KCacheable;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @KCacheable(cacheName = "exampleCache", key = "exampleKey")
    public String getCachedData() {
        return "Cached Data";
    }

    @KCachePut(key = "exampleKey")
    public String updateCachedData() {
        return "Updated Cached Data";
    }

    @KCacheEvict(key = "exampleKey")
    public void evictCachedData() {
    }
}
