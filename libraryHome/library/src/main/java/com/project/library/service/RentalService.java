package com.project.library.service;

import com.project.library.entity.RentalDtl;
import com.project.library.entity.RentalMst;
import com.project.library.exception.CustomRentalException;
import com.project.library.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public void rentalOne(int userId, int bookId) {
        availability(userId);
        availabilityLoan(bookId);

        RentalMst rentalMst = RentalMst.builder()
                .userId(userId)
                .build();

        rentalRepository.saveRentalMst(rentalMst);

        List<RentalDtl> rentalDtlList = new ArrayList<>();
        RentalDtl rentalDtl = RentalDtl.builder()
                .rentalId(rentalMst.getRentalId())
                .bookId(bookId)
                .build();

        rentalDtlList.add(rentalDtl);
        rentalRepository.saveRentalDtl(rentalDtlList);

    }

    public void returnBook(int bookId) {
        notAvailability(bookId);
        rentalRepository.updateReturnDate(bookId);
    }


    private void availability(int userId) {
        int rentalCount = rentalRepository.rentalAvaileability(userId);
        if (rentalCount > 2){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("rentalCountError", "대여 횟수를 초과하였습니다.");

            throw new CustomRentalException(errorMap);
        }
    }

    private void availabilityLoan(int bookId) {
        int loanCount = rentalRepository.loanRental(bookId);
        if (loanCount > 0){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("rentalCountError", "현재 대여중인 도서입니다.");

            throw new CustomRentalException(errorMap);
        }
    }
    private void notAvailability(int userId) {
        int loanCount = rentalRepository.loanRental(userId);
        if (loanCount < 1){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("rentalCountError", "대여중인 도서가 아닙니다.");

            throw new CustomRentalException(errorMap);
        }
    }

}
