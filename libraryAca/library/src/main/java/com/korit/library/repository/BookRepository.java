package com.korit.library.repository;

import com.korit.library.entity.BookImage;
import com.korit.library.entity.BookMst;
import com.korit.library.entity.CategoryView;
import com.korit.library.web.dto.BookReqDto;
import com.korit.library.web.dto.DeleteBooksReqDto;
import com.korit.library.web.dto.SearchNumberListReqDto;
import com.korit.library.web.dto.SearchReqDto;
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

    public int getBookTotalCount(SearchNumberListReqDto searchNumberListReqDto);
    public List<BookMst> searchBook(SearchReqDto searchReqDto);
    public BookMst findBookByBookCode(String bookCode);

    public List<CategoryView> findAllCategory();

    public int saveBook(BookReqDto bookReqDto);

    public int updateBookByBookCode(BookReqDto bookReqDto);

    public int maintainUpdateBookByBookCode(BookReqDto bookReqDto);

    public int deleteBook(String bookCode);

    public int deleteBooks(List<Integer> userIds);

    public int registerBookImages(List<BookImage> bookImages);

    public List<BookImage> findBookImageAll(String bookCode);

    public BookImage findBookImageByImageId(int imageId);

    public int deleteBookImage(int imageId);
}
