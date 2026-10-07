package com.io.github.cawodevelopment.urlshortener;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping getUrlMappingByShortUrl(String shortUrl) {
        return urlMappingRepository.getUrlMappingByShortUrl(shortUrl);
    }

    public UrlMapping createUrlMapping(String longUrl) {
        UrlMapping urlMapping = new UrlMapping();

        urlMapping.setShortUrl(RandomStringUtility.generateCode());
        urlMapping.setLongUrl(longUrl);
        urlMapping.setCreatedAt(LocalDate.now());

        urlMappingRepository.save(urlMapping);

        return urlMapping;
    }
}
