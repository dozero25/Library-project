package com.korit.library.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookMst {

    @ApiModelProperty(hidden = true)
    private int bookId;

    @ApiModelProperty(value = "도서코드", example = "소록-999")
    private String bookCode;
    @ApiModelProperty(value = "도서명", example = "책이름 테스트")
    private String bookName;
    @ApiModelProperty(value = "저자", example = "테스터")
    private String author;
    @ApiModelProperty(value = "출판사", example = "테스트")
    private String publisher;
    @ApiModelProperty(value = "출판일", example = "2023-01-01")
    private LocalDate publicationDate;
    @ApiModelProperty(value = "분야", example = "테스트")
    private String category;
}
