package com.project.library.repository;

import com.project.library.entity.BookImage;
import com.project.library.entity.BookMst;
import com.project.library.entity.CategoryView;
import com.project.library.web.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookRepository {

    public int getBookTotalCount(SearchNumberListReqDto searchNumberListReqDto);
    public List<BookMst> searchBook(SearchReqDto searchReqDto);
    public BookMst findBookByBookCode(String bookCode);

    public List<CategoryView> findAllCategory();

    public int saveBook(BookReqDto bookReqDto);

    public int updateBookByBookCode(BookReqDto bookReqDto);

    public int maintainUpdateBookByBookCode(BookReqDto bookReqDto);

    public int deleteBookByBookCode(String bookCode);

    public int deleteBooks(List<Integer> userIds);

    public int registerBookImages(List<BookImage> bookImages);

    public List<BookImage> findBookImageAll(String bookCode);

    public BookImage findBookImageByImageId(int imageId);

    public BookImage findBookImageByBookCode(String bookCode);

    public int deleteBookImage(int imageId);
}
