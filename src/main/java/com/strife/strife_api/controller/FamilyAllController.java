package com.strife.strife_api.controller;

import com.strife.strife_api.dto.FamilyDto;
import com.strife.strife_api.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FamilyAllController {

    private final FamilyService familyService;

    @GetMapping("/api/families")
    public List<FamilyDto> getAllFamilies() {
        return familyService.getAllFamilies();
    }
}