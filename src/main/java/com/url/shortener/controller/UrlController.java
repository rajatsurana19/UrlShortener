package com.url.shortener.controller;

import com.url.shortener.dto.ShortenUrlRequestDto;
import com.url.shortener.entity.UrlEntity;
import com.url.shortener.entity.UserEntity;
import com.url.shortener.repository.UserRepository;
import com.url.shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final UserRepository userRepository;

    @PostMapping("/api/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody ShortenUrlRequestDto requestDto, 
                                            @AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = null;
        if (userDetails != null) {
            user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        }
        String shortCode = urlService.shortenUrl(requestDto, user);
        return ResponseEntity.ok(shortCode);
    }

    @GetMapping("/{shortCode:[a-zA-Z0-9]{6}}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/api/insights/{shortCode}")
    public ResponseEntity<UrlEntity> getInsights(@PathVariable String shortCode) {
        UrlEntity insights = urlService.getInsights(shortCode);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/api/user/links")
    public ResponseEntity<List<UrlEntity>> getMyLinks(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(urlService.getUrlsByUser(user));
    }
}
