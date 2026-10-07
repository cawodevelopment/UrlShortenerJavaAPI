package com.io.github.cawodevelopment.urlshortener;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToUrl(@PathVariable String shortUrl) {
        UrlMapping urlMapping = urlMappingService.getUrlMappingByShortUrl(shortUrl);

        return new RedirectView(urlMapping.getLongUrl());
    }

    @PostMapping
    public UrlMapping createUrlMapping(
            @RequestBody CreateUrlMappingRequest request) {

        return urlMappingService.createUrlMapping(request.longUrl());
    }

}
