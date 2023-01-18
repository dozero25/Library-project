package com.korit.library.service;

import com.korit.library.entity.RentalDtl;
import com.korit.library.entity.RentalMst;
import com.korit.library.exception.CustomRentalException;
import com.korit.library.repository.RentalRepository;
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

    private void availability(int userId) {
        int rentalCount = rentalRepository.rentalAvaileability(userId);
        if (rentalCount > 2) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("rentalCountError", "대여 횟수를 초과하였습니다.");

            throw new CustomRentalException(errorMap);
        }
    }
}
