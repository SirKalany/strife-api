package com.strife.strife_api.repository;

import com.strife.strife_api.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Optional<Family> findBySlug(String slug);

    List<Family> findByDomainSlugAndCountrySlugOrderByName(
        String domainSlug,
        String countrySlug
    );
}