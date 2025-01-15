package com.kocak.kcache.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kcache")
public class WebController {

    @GetMapping("/cacheManager")
    public String getCacheManager() throws IOException {
        var resource = new ClassPathResource("templates/cache-manager.html");

        try (var inputStream = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "Cache Manager HTML file not found or could not be read!";
        }
    }
}
