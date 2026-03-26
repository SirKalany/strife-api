package com.strife.strife_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryDto {
    private String slug;
    private String name;
    private String flagUrl;
}