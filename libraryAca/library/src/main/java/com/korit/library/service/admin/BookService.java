package com.korit.library.service.admin;

import com.korit.library.exception.CustomValidationException;
import com.korit.library.repository.BookRepository;
import com.korit.library.web.dto.BookMstDto;
import com.korit.library.web.dto.BookReqDto;
import com.korit.library.web.dto.CategoryDto;
import com.korit.library.web.dto.SearchReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookMstDto> searchBook(SearchReqDto searchReqDto){
        searchReqDto.setIndex();
        return bookRepository.searchBook(searchReqDto);
    }

    public List<CategoryDto> getCategories() {
        return bookRepository.findAllCategory();
    }

    public void registerBook(BookReqDto bookReqDto){
        duplicateBookCode(bookReqDto.getBookCode());
        bookRepository.saveBook(bookReqDto);
    }

    private void duplicateBookCode(String bookCode){
        BookMstDto bookMstDto = bookRepository.findBookByBookCode(bookCode);
        if (bookMstDto != null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("bookCode", "이미 존재하는 도서코드입니다.");

            throw new CustomValidationException(errorMap);
        }
    }

    public void modifyBook(BookReqDto bookReqDto) {
        bookRepository.updateBookByBookCode(bookReqDto);
    }


}
