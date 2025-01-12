package com.kocak.kcache.service;

import com.kocak.kcache.core.KCacheManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestServiceTest {

    @Autowired
    private TestService testService;

    private final KCacheManager cacheManager = KCacheManager.getInstance();

    @BeforeEach
    public void setUp() {
        // Her testten önce cache'i temizle
        cacheManager.clear();
    }

    @Test
    public void testKCacheable() {
        // İlk çalıştırmada metot çağrılmalı
        String result1 = testService.getCachedData();
        assertEquals("Cached Data", result1);
        assertTrue(cacheManager.containsKey("exampleKey"));

        // İkinci çalıştırmada cache kullanılmalı
        String result2 = testService.getCachedData();
        assertEquals("Cached Data", result2);
    }

    @Test
    public void testKCachePut() {
        // Cache'i güncelle
        String updatedData = testService.updateCachedData();
        assertEquals("Updated Cached Data", updatedData);
        assertEquals("Updated Cached Data", cacheManager.get("exampleKey"));
    }

    @Test
    public void testKCacheEvict() {
        // Cache'i temizle
        testService.evictCachedData();
        assertFalse(cacheManager.containsKey("exampleKey")); // contains yerine containsKey kullanıldı
    }
}
