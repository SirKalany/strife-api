package com.strife.strife_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperatorDto {
    private String countrySlug;
    private String countryName;
    private String notes;
}