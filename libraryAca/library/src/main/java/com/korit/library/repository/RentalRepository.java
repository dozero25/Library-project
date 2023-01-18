package com.korit.library.repository;

import com.korit.library.entity.RentalDtl;
import com.korit.library.entity.RentalMst;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalRepository {

    public int rentalAvaileability(int userid);
    public int saveRentalMst(RentalMst rentalMst);
    public int saveRentalDtl(List<RentalDtl> rentalDtlList);
}
