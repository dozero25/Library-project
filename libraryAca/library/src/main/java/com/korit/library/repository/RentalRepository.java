package com.korit.library.repository;

import com.korit.library.entity.RentalDtl;
import com.korit.library.entity.RentalMst;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalRepository {

    public int rentalAvaileability(int userId);
    public int loanRental(int bookId);
    public int saveRentalMst(RentalMst rentalMst);
    public int saveRentalDtl(List<RentalDtl> rentalDtlList);
    public int updateReturnDate(int bookId);
}
