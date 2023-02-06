package com.korit.library.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchBookReqDto {
    private int page;
    private String searchValue;
    private List<String> categories;
    private int count;
    private int userId;
}
