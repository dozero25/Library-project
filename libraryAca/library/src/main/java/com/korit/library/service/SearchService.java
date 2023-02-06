package com.korit.library.service;

import com.korit.library.entity.SearchBook;
import com.korit.library.repository.SearchRepository;
import com.korit.library.web.dto.SearchBookReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public int getSearchTotalCount(SearchBookReqDto searchBookReqDto) {
        return searchRepository.getUserSearchBookTotalCount(searchBookReqDto);
    }

    public List<SearchBook> getSearchBooks(SearchBookReqDto searchBookReqDto){
        searchBookReqDto.setIndex();
        return searchRepository.userSearchBook(searchBookReqDto);
    }
}
