package com.korit.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchBook {
    private int bookId;
    private String bookCode;
    private String bookName;
    private String author;
    private String publisher;
    private String publicationDate;
    private String category;
    private String saveName;
    private int rentalDtlId;
    private LocalDate returnDate;
    private int userId;
}
