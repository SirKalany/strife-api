package com.strife.strife_api.repository;

import com.strife.strife_api.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findBySlug(String slug);

    @Query("""
        SELECT DISTINCT c FROM Country c
        JOIN Family f ON f.country = c
        JOIN Domain d ON f.domain = d
        WHERE d.slug = :domainSlug
        ORDER BY c.name
    """)
    List<Country> findCountriesByDomainSlug(String domainSlug);
}