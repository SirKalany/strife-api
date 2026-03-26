package com.strife.strife_api.repository;

import com.strife.strife_api.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DomainRepository extends JpaRepository<Domain, UUID> {
    Optional<Domain> findBySlug(String slug);
}