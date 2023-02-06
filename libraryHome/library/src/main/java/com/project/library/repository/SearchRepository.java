package com.project.library.repository;

import com.project.library.entity.SearchBook;
import com.project.library.web.dto.SearchBookReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchRepository {

    public int getUserSearchBookTotalCount(SearchBookReqDto searchBookReqDto);

    public List<SearchBook> userSearchBook(SearchBookReqDto searchBookReqDto);

}
