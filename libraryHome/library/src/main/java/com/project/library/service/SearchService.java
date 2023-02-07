package com.project.library.service;

import com.project.library.entity.SearchBook;
import com.project.library.repository.SearchRepository;
import com.project.library.web.dto.SearchBookReqDto;
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
