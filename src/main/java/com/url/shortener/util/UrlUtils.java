package com.url.shortener.util;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import java.security.SecureRandom;

@Component
public class UrlUtils {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    private static final String URL_REGEX = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public boolean isValid(String url){
        if (url == null || url.isEmpty()) return false;
        return URL_PATTERN.matcher(url).matches();
    }

    public String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
