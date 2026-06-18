package com.url.shortener.service;

import com.url.shortener.UrlshortenerApplication;
import com.url.shortener.entity.UrlEntity;
import com.url.shortener.repository.UrlRepository;
import com.url.shortener.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlUtils urlUtils;

    public String shortenUrl(String url){
        // validate url
        boolean isValid =  urlUtils.isValid(url);
        if(!isValid){
            throw new RuntimeException("URL is Invalid");

        }
        // generate short url
        String shortCode = "TODO";
        // save to database
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setMainUrl(url);
        urlEntity.setShortCode(shortCode);

        urlRepository.save(urlEntity);

        // return short url

        return shortCode;
    }


}
