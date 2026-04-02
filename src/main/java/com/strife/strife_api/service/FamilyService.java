package com.strife.strife_api.service;

import com.strife.strife_api.dto.FamilyDto;
import com.strife.strife_api.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;

    public List<FamilyDto> getFamiliesByDomainAndCountry(String domainSlug, String countrySlug) {
        return familyRepository.findByDomainSlugAndCountrySlugOrderByName(domainSlug, countrySlug)
                .stream()
                .map(f -> new FamilyDto(f.getId(), f.getSlug(), f.getName(), f.getImageUrl(), f.getDescription()))
                .toList();
    }

    public List<FamilyDto> getAllFamilies() {
        return familyRepository.findAll()
                .stream()
                .map(f -> new FamilyDto(f.getId(), f.getSlug(), f.getName(), f.getImageUrl(), f.getDescription()))
                .toList();
    }
}