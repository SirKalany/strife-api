package com.strife.strife_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModelSummaryDto {
    private String slug;
    private String name;
    private Integer yearIntroduced;
    private Integer yearRetired;
    private String imageUrl;
}