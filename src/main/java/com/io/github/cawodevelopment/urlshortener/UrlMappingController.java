package com.io.github.cawodevelopment.urlshortener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/{shortUrl}")
    public String redirectToUrl(@PathVariable String shortUrl) {
        UrlMapping urlMapping = urlMappingService.getUrlMappingByShortUrl(shortUrl);

        return "redirect:" + urlMapping.getLongUrl();
    }

}
