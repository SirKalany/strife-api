package com.strife.strife_api.controller;

import com.strife.strife_api.dto.ModelDetailDto;
import com.strife.strife_api.dto.ModelSummaryDto;
import com.strife.strife_api.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @GetMapping("/api/families/{family}/models")
    public List<ModelSummaryDto> getModelsByFamily(@PathVariable String family) {
        return modelService.getModelsByFamily(family);
    }

    @GetMapping("/api/models/{model}")
    public ModelDetailDto getModel(@PathVariable String model) {
        return modelService.getModelBySlug(model);
    }

    @GetMapping("/api/models")
    public List<ModelSummaryDto> getAllModels() {
        return modelService.getAllModels();
    }
}