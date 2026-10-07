package com.io.github.cawodevelopment.urlshortener;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    public UrlMapping getUrlMappingById(Long id) {
        return urlMappingRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No UrlMapping found with id " + id));
    }

    public Page<UrlMapping> getUrlMappings(Pageable pageable) {
        return urlMappingRepository.getUrlMappings(pageable);
    }

    public void deleteUrlMappingById(Long id) {
        urlMappingRepository.deleteById(id);
    }
}
