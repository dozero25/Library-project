package com.project.library.repository;

import com.project.library.entity.RentalDtl;
import com.project.library.entity.RentalMst;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalRepository {
    public int rentalAvaileability(int userId);
    public int saveRentalMst(RentalMst rentalMst);
    public int saveRentalDtl(List<RentalDtl> rentalDtlList);
}
