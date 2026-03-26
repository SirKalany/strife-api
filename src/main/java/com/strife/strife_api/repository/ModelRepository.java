package com.strife.strife_api.repository;

import com.strife.strife_api.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {
    Optional<Model> findBySlug(String slug);

    List<Model> findByFamilySlugOrderByYearIntroduced(String familySlug);
}