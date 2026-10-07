package com.io.github.cawodevelopment.urlshortener;

import org.springframework.stereotype.Service;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping getUrlMappingByShortUrl(String shortUrl) {
        return urlMappingRepository.getUrlMappingByShortUrl(shortUrl);
    }
}
