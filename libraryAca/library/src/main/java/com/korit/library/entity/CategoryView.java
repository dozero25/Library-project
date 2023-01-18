package com.korit.library.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryView {
    @ApiModelProperty(value = "카테고리 순번", example = "1")
    private int category_id;
    @ApiModelProperty(value = "카테고리 이름", example = "가정/생활")
    private String category;
}
