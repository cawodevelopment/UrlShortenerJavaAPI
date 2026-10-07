package com.io.github.cawodevelopment.urlshortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlMappingControllerTest {

    @Mock
    private UrlMappingService urlMappingService;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<UrlMapping> urlMappings;

    @InjectMocks
    private UrlMappingController urlMappingController;

    @Test
    void redirectToUrlReturnsRedirectToMappedLongUrl() {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("https://example.com");
        when(urlMappingService.getUrlMappingByShortUrl("abc12")).thenReturn(urlMapping);

        RedirectView redirectView = urlMappingController.redirectToUrl("abc12");

        assertEquals("https://example.com", redirectView.getUrl());
        verify(urlMappingService).getUrlMappingByShortUrl("abc12");
    }

    @Test
    void createUrlMappingDelegatesLongUrlToService() {
        CreateUrlMappingRequest request = new CreateUrlMappingRequest("https://example.com");
        UrlMapping createdMapping = new UrlMapping();
        when(urlMappingService.createUrlMapping(request.longUrl())).thenReturn(createdMapping);

        UrlMapping result = urlMappingController.createUrlMapping(request);

        assertSame(createdMapping, result);
        verify(urlMappingService).createUrlMapping("https://example.com");
    }

    @Test
    void getUrlMappingByIdDelegatesIdToService() {
        UrlMapping mapping = new UrlMapping();
        when(urlMappingService.getUrlMappingById(42L)).thenReturn(mapping);

        UrlMapping result = urlMappingController.getUrlMappingById(42L);

        assertSame(mapping, result);
        verify(urlMappingService).getUrlMappingById(42L);
    }

    @Test
    void getUrlMappingsDelegatesPageableToService() {
        when(urlMappingService.getUrlMappings(pageable)).thenReturn(urlMappings);

        Page<UrlMapping> result = urlMappingController.getUrlMappings(pageable);

        assertSame(urlMappings, result);
        verify(urlMappingService).getUrlMappings(pageable);
    }

    @Test
    void deleteUrlMappingByIdDelegatesIdToService() {
        urlMappingController.deleteUrlMappingById(42L);

        verify(urlMappingService).deleteUrlMappingById(42L);
    }
}
