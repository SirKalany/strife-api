package com.strife.strife_api.service;

import com.strife.strife_api.dto.*;
import com.strife.strife_api.entity.*;
import com.strife.strife_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CountryRepository countryRepository;
    private final FamilyRepository familyRepository;
    private final ModelRepository modelRepository;
    private final OperatorRepository operatorRepository;
    private final DomainRepository domainRepository;

    // --- COUNTRY ---

    public Country createCountry(CountryRequest req) {
        Country country = new Country();
        country.setSlug(req.getSlug());
        country.setName(req.getName());
        country.setFlagUrl(req.getFlagUrl());
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String slug) {
        Country country = countryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        // Delete all families and their models for this country
        List<Family> families = familyRepository.findByCountrySlug(slug);
        for (Family family : families) {
            List<Model> models = modelRepository.findByFamilySlugOrderByYearIntroduced(family.getSlug());
            for (Model model : models) {
                operatorRepository.deleteByModel(model);
                modelRepository.delete(model);
            }
            familyRepository.delete(family);
        }

        countryRepository.delete(country);
    }

    // --- FAMILY ---

    public Family createFamily(FamilyRequest req) {
        Domain domain = domainRepository.findById(req.getDomainId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Domain not found"));
        Country country = countryRepository.findById(req.getCountryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        Family family = new Family();
        family.setSlug(req.getSlug());
        family.setName(req.getName());
        family.setImageUrl(req.getImageUrl());
        family.setDescription(req.getDescription());
        family.setDomain(domain);
        family.setCountry(country);
        return familyRepository.save(family);
    }

    @Transactional
    public void deleteFamily(String slug) {
        Family family = familyRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Family not found"));

        // Delete all models and their operators in this family
        List<Model> models = modelRepository.findByFamilySlugOrderByYearIntroduced(slug);
        for (Model model : models) {
            operatorRepository.deleteByModel(model);
            modelRepository.delete(model);
        }

        familyRepository.delete(family);
    }

    // --- MODEL ---

    @Transactional
    public Model createModel(ModelRequest req) {
        Family family = familyRepository.findById(req.getFamilyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Family not found"));

        Model model = new Model();
        model.setSlug(req.getSlug());
        model.setName(req.getName());
        model.setYearIntroduced(req.getYearIntroduced());
        model.setYearRetired(req.getYearRetired());
        model.setImageUrl(req.getImageUrl());
        model.setArticle(req.getArticle());
        model.setSpecs(req.getSpecs());
        model.setFamily(family);
        Model saved = modelRepository.save(model);

        saveOperators(saved, req.getOperators());
        return saved;
    }

    @Transactional
    public Model updateModel(String slug, ModelRequest req) {
        Model model = modelRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Model not found"));

        model.setName(req.getName());
        model.setYearIntroduced(req.getYearIntroduced());
        model.setYearRetired(req.getYearRetired());
        model.setImageUrl(req.getImageUrl());
        model.setArticle(req.getArticle());
        model.setSpecs(req.getSpecs());

        if (req.getFamilyId() != null) {
            Family family = familyRepository.findById(req.getFamilyId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Family not found"));
            model.setFamily(family);
        }

        operatorRepository.deleteByModel(model);
        saveOperators(model, req.getOperators());

        return modelRepository.save(model);
    }

    @Transactional
    public void deleteModel(String slug) {
        Model model = modelRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Model not found"));
        operatorRepository.deleteByModel(model);
        modelRepository.delete(model);
    }

    private void saveOperators(Model model, List<OperatorRequest> operators) {
        if (operators == null || operators.isEmpty())
            return;
        for (OperatorRequest op : operators) {
            Country country = countryRepository.findById(op.getCountryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Operator country not found"));
            Operator operator = new Operator();
            operator.setModel(model);
            operator.setCountry(country);
            operator.setNotes(op.getNotes());
            operatorRepository.save(operator);
        }
    }
}