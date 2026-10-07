package com.io.github.cawodevelopment.urlshortener;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/url/{shortUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortUrl) {
        UrlMapping urlMapping = urlMappingService.getUrlMappingByShortUrl(shortUrl);

        return new RedirectView(urlMapping.getLongUrl());
    }

    @PostMapping
    public UrlMapping createUrlMapping(
            @RequestBody CreateUrlMappingRequest request) {

        return urlMappingService.createUrlMapping(request.longUrl());
    }

    @GetMapping("/{id}")
    public UrlMapping getUrlMappingById(@PathVariable Long id) {
        return urlMappingService.getUrlMappingById(id);
    }

    @GetMapping
    public Page<UrlMapping> getUrlMappings(@PageableDefault(size = 5, page = 0) Pageable pageable) {
        return urlMappingService.getUrlMappings(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteUrlMappingById(@PathVariable Long id) {
        urlMappingService.deleteUrlMappingById(id);
    }
}
