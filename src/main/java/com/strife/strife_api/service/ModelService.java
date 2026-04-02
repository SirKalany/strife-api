package com.strife.strife_api.service;

import com.strife.strife_api.dto.FamilyDto;
import com.strife.strife_api.dto.ModelDetailDto;
import com.strife.strife_api.dto.ModelSummaryDto;
import com.strife.strife_api.dto.OperatorDto;
import com.strife.strife_api.repository.ModelRepository;
import com.strife.strife_api.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

        private final ModelRepository modelRepository;
        private final OperatorRepository operatorRepository;

        public List<ModelSummaryDto> getModelsByFamily(String familySlug) {
                return modelRepository.findByFamilySlugOrderByYearIntroduced(familySlug)
                                .stream()
                                .map(m -> new ModelSummaryDto(
                                                m.getSlug(),
                                                m.getName(),
                                                m.getYearIntroduced(),
                                                m.getYearRetired(),
                                                m.getImageUrl()))
                                .toList();
        }

        public ModelDetailDto getModelBySlug(String slug) {
                var model = modelRepository.findBySlug(slug)
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Model not found: " + slug));

                List<OperatorDto> operators = operatorRepository.findByModelSlug(slug)
                                .stream()
                                .map(o -> new OperatorDto(
                                                o.getCountry().getSlug(),
                                                o.getCountry().getName(),
                                                o.getNotes()))
                                .toList();

                return new ModelDetailDto(
                                model.getSlug(),
                                model.getName(),
                                model.getYearIntroduced(),
                                model.getYearRetired(),
                                model.getImageUrl(),
                                model.getArticle(),
                                model.getSpecs(),
                                new FamilyDto(
                                                model.getFamily().getId(),
                                                model.getFamily().getSlug(),
                                                model.getFamily().getName(),
                                                model.getFamily().getImageUrl(),
                                                model.getFamily().getDescription()),
                                operators);
        }

        public List<ModelSummaryDto> getAllModels() {
                return modelRepository.findAll()
                                .stream()
                                .map(m -> new ModelSummaryDto(
                                                m.getSlug(),
                                                m.getName(),
                                                m.getYearIntroduced(),
                                                m.getYearRetired(),
                                                m.getImageUrl()))
                                .toList();
        }
}