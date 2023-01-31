package com.korit.library.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class BookReqDto {
    @ApiModelProperty(value = "도서 코드", example = "소록-999", required = true)
    @NotBlank
    private String bookCode;

    @ApiModelProperty(value = "도서 이름", example = "테스트 도서명", required = true)
    @NotBlank
    private String bookName;

    @ApiModelProperty(value = "저자", example = "테스터")
    private String author;

    @ApiModelProperty(value = "출판사", example = "테스트 출판사")
    private String publisher;

    @ApiModelProperty(value = "출판일", example = "2023-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate publicationDate;

    @ApiModelProperty(value = "카테고리", example = "가정/생활", required = true)
    @NotBlank
    private String category;

}
