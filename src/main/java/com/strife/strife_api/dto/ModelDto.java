package com.strife.strife_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ModelDto {
    private String slug;
    private String name;
    private Integer yearIntroduced;
    private Integer yearRetired;
    private String imageUrl;
    private String article;
    private Map<String, Object> specs;
    private List<OperatorDto> operators;
}