package com.strife.strife_api.controller;

import com.strife.strife_api.dto.*;
import com.strife.strife_api.entity.*;
import com.strife.strife_api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // --- COUNTRY ---

    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry(@RequestBody CountryRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createCountry(req));
    }

    @DeleteMapping("/countries/{slug}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String slug) {
        adminService.deleteCountry(slug);
        return ResponseEntity.noContent().build();
    }

    // --- FAMILY ---

    @PostMapping("/families")
    public ResponseEntity<Family> createFamily(@RequestBody FamilyRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createFamily(req));
    }

    @PutMapping("/families/{slug}")
    public ResponseEntity<Family> updateFamily(
            @PathVariable String slug,
            @RequestBody FamilyRequest req) {
        return ResponseEntity.ok(adminService.updateFamily(slug, req));
    }

    @DeleteMapping("/families/{slug}")
    public ResponseEntity<Void> deleteFamily(@PathVariable String slug) {
        adminService.deleteFamily(slug);
        return ResponseEntity.noContent().build();
    }

    // --- MODEL ---

    @PostMapping("/models")
    public ResponseEntity<Model> createModel(@RequestBody ModelRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createModel(req));
    }

    @PutMapping("/models/{slug}")
    public ResponseEntity<Model> updateModel(@PathVariable String slug, @RequestBody ModelRequest req) {
        return ResponseEntity.ok(adminService.updateModel(slug, req));
    }

    @DeleteMapping("/models/{slug}")
    public ResponseEntity<Void> deleteModel(@PathVariable String slug) {
        adminService.deleteModel(slug);
        return ResponseEntity.noContent().build();
    }
}