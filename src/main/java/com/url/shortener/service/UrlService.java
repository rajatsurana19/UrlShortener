package com.url.shortener.service;

import com.url.shortener.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlUtils urlUtils;

    public String shortenUrl(String url){
        // validate url
        boolean isValid =  urlUtils.isValid(url);
        if(!isValid){
            throw new RuntimeException("URL is Invalid");

        }
        // generate short url
        String shortenCode = "TODO";
        // save to database

        // return short url

        return null;
    }


}
