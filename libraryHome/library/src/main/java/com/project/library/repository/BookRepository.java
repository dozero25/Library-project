package com.project.library.repository;

import com.project.library.web.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookRepository {
    /*
    C:  도서등록
    R: 1.도서전체조회
         1. 검색
            1. 도서코드
            2. 도서명
            3. 저자
            4. 출판사
         2. 카테고리
                1. 전체조회
                2. 20개씩 가져오기
       2. 도서코드로 조회
    U:  도서수정
    D:  도서삭제
     */

    public List<BookMstDto> searchBook(SearchReqDto searchReqDto);
    public BookMstDto findBookByBookCode(String bookCode);

    public List<CategoryDto> findAllCategory();

    public int saveBook(BookReqDto bookReqDto);

    public int updateBookByBookCode(BookReqDto bookReqDto);

    public int maintainUpdateBookByBookCode(BookReqDto bookReqDto);

    public int deleteBook(String bookCode);

    public int registerBookImages(List<BookImageDto> bookImageDtos);
}
