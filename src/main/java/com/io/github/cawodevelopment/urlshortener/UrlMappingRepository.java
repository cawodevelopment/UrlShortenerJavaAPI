package com.io.github.cawodevelopment.urlshortener;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    @Query("""
        SELECT e
        FROM UrlMapping e
        ORDER BY e.createdAt Desc
    """)
    Page<UrlMapping> getUrlMappings(Pageable pageable);

    UrlMapping getUrlMappingByShortUrl(String shortUrl);
}
