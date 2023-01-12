package com.korit.library.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchReqDto {
    @ApiModelProperty(required = false, example = "나는")
    private String searchValue;

    @ApiModelProperty(required = false, example = "소설")
    private String category;

    @ApiModelProperty(required = false, example = "bookName")
    private String order;

    @NotBlank
    @ApiModelProperty(required = true, notes = "조회 전체 = N, 조회제한 = Y")
    private String limit;

    @ApiModelProperty(required = false, example = "1")
    private int page;

    @ApiModelProperty(required = false, example = "20")
    private int count;

    @ApiModelProperty(hidden = true)
    private int index;

    public void setIndex(){
        index = (page-1) * count;
    }

}
