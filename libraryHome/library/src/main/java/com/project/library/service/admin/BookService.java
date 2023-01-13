package com.project.library.service.admin;

import com.project.library.repository.BookRepository;
import com.project.library.web.dto.BookMstDto;
import com.project.library.web.dto.SearchReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookMstDto> searchBook(SearchReqDto searchReqDto){
        searchReqDto.setIndex();
        return bookRepository.searchBook(searchReqDto);
    }
}
