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
public class RentalDtl {
    private int rentalDtl;
    private int rentalId;
    private int bookId;
    private LocalDate returnDate;

}
