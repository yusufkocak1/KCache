package com.kocak.kcache.controller;

import com.kocak.kcache.core.KCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/*
 * @author Yusuf Kocak
 */
@RestController
@RequestMapping("/kcache")
public class CacheController {

    private final KCacheManager cacheManager = KCacheManager.getInstance();

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getCache() {
        return ResponseEntity.ok(cacheManager.getCache());
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearCache() {
        cacheManager.clear();
        return ResponseEntity.ok("Cache has been cleared!");
    }

    @PostMapping("/evict")
    public ResponseEntity<String> evictCache(@RequestBody String key) {
        cacheManager.evict(key);
        return ResponseEntity.ok("Cache has been evicted!");
    }

    @GetMapping("/statistics")
    public ResponseEntity<String> getCacheStatistics() {
        return ResponseEntity.ok(cacheManager.getStatistics());
    }
}
