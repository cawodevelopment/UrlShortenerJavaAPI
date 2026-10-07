package com.io.github.cawodevelopment.urlshortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlMappingServiceTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<UrlMapping> urlMappings;

    @InjectMocks
    private UrlMappingService urlMappingService;

    @Test
    void getUrlMappingByShortUrlDelegatesToRepository() {
        UrlMapping mapping = new UrlMapping();
        when(urlMappingRepository.getUrlMappingByShortUrl("abc12")).thenReturn(mapping);

        UrlMapping result = urlMappingService.getUrlMappingByShortUrl("abc12");

        assertSame(mapping, result);
        verify(urlMappingRepository).getUrlMappingByShortUrl("abc12");
    }

    @Test
    void createUrlMappingBuildsAndSavesMapping() {
        String longUrl = "https://example.com";

        UrlMapping result = urlMappingService.createUrlMapping(longUrl);

        ArgumentCaptor<UrlMapping> mappingCaptor = ArgumentCaptor.forClass(UrlMapping.class);
        verify(urlMappingRepository).save(mappingCaptor.capture());
        UrlMapping savedMapping = mappingCaptor.getValue();

        assertSame(savedMapping, result);
        assertEquals(longUrl, savedMapping.getLongUrl());
        assertEquals(LocalDate.now(), savedMapping.getCreatedAt());
        assertTrue(savedMapping.getShortUrl().matches("[A-Za-z0-9]{5}"));
    }

    @Test
    void getUrlMappingByIdReturnsMappingWhenFound() {
        UrlMapping mapping = new UrlMapping();
        when(urlMappingRepository.findById(42L)).thenReturn(Optional.of(mapping));

        UrlMapping result = urlMappingService.getUrlMappingById(42L);

        assertSame(mapping, result);
        verify(urlMappingRepository).findById(42L);
    }

    @Test
    void getUrlMappingByIdThrowsWhenMappingIsMissing() {
        when(urlMappingRepository.findById(42L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> urlMappingService.getUrlMappingById(42L)
        );

        assertEquals("No UrlMapping found with id 42", exception.getMessage());
    }

    @Test
    void getUrlMappingsDelegatesPageableToRepository() {
        when(urlMappingRepository.getUrlMappings(pageable)).thenReturn(urlMappings);

        Page<UrlMapping> result = urlMappingService.getUrlMappings(pageable);

        assertSame(urlMappings, result);
        verify(urlMappingRepository).getUrlMappings(pageable);
    }

    @Test
    void deleteUrlMappingByIdDelegatesToRepository() {
        urlMappingService.deleteUrlMappingById(42L);

        verify(urlMappingRepository).deleteById(42L);
    }
}
