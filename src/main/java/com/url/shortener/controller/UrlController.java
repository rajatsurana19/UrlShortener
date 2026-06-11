package com.url.shortener.controller;

import com.url.shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public String shortenUrl(String url){
        return urlService.shortenUrl(url);
    }
}
