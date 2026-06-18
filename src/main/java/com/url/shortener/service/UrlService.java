package com.url.shortener.service;

import com.url.shortener.dto.ShortenUrlRequestDto;
import com.url.shortener.entity.UrlEntity;
import com.url.shortener.entity.UserEntity;
import com.url.shortener.repository.UrlRepository;
import com.url.shortener.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlUtils urlUtils;

    @Transactional
    public String shortenUrl(ShortenUrlRequestDto requestDto, UserEntity user) {
        String url = requestDto.getUrl();
        if (!urlUtils.isValid(url)) {
            throw new RuntimeException("URL is Invalid");
        }

        String shortCode;
        do {
            shortCode = urlUtils.generateShortCode();
        } while (urlRepository.findByShortCode(shortCode).isPresent());

        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setMainUrl(url);
        urlEntity.setShortCode(shortCode);
        urlEntity.setCreatedAt(LocalDateTime.now());
        urlEntity.setClickCount(0L);
        urlEntity.setUser(user);

        urlRepository.save(urlEntity);

        return shortCode;
    }

    public java.util.List<UrlEntity> getUrlsByUser(UserEntity user) {
        return urlRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        UrlEntity urlEntity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        urlEntity.setClickCount(urlEntity.getClickCount() + 1);
        urlRepository.save(urlEntity);

        return urlEntity.getMainUrl();
    }

    public UrlEntity getInsights(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }
}
