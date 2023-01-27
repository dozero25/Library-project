package com.korit.library.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeleteBooksReqDto {
    private List<Integer> userIds;
}
