package com.project.library.service.admin;

import com.project.library.entity.BookImage;
import com.project.library.entity.BookMst;
import com.project.library.entity.CategoryView;
import com.project.library.exception.CustomValidationException;
import com.project.library.repository.BookRepository;
import com.project.library.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BookService {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private BookRepository bookRepository;

    public Map<String, Object> getBookAndImage(String bookCode) {
        Map<String, Object> result = new HashMap<>();
        result.put("bookMst", bookRepository.findBookByBookCode(bookCode));
        result.put("bookImage", bookRepository.findBookImageByBookCode(bookCode));

        return result;
    }

    public int getBookTotalCount(SearchNumberListReqDto searchNumberListReqDto) {
        return bookRepository.getBookTotalCount(searchNumberListReqDto);
    }

    public List<BookMst> searchBook(SearchReqDto searchReqDto){
        searchReqDto.setIndex();
        return bookRepository.searchBook(searchReqDto);
    }

    public List<CategoryView> getCategories() {
        return bookRepository.findAllCategory();
    }

    public void registerBook(BookReqDto bookReqDto){
        duplicateBookCode(bookReqDto.getBookCode());
        bookRepository.saveBook(bookReqDto);
    }

    private void duplicateBookCode(String bookCode){
        BookMst bookMst = bookRepository.findBookByBookCode(bookCode);
        if (bookMst != null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("bookCode", "이미 존재하는 도서코드입니다.");

            throw new CustomValidationException(errorMap);
        }
    }

    public void modifyBook(BookReqDto bookReqDto) {
        bookRepository.updateBookByBookCode(bookReqDto);
    }

    public void updateBook(BookReqDto bookReqDto) {
        bookRepository.maintainUpdateBookByBookCode(bookReqDto);

    }

    public void removeBook(String bookCode) {
        bookRepository.deleteBookByBookCode(bookCode);
    }

    public void removeBooks(DeleteBooksReqDto deleteBooksReqDto) {
        bookRepository.deleteBooks(deleteBooksReqDto.getUserIds());
    }

    public void registerBookImages(String bookCode, List<MultipartFile> files){
        if (files.size() < 1){
            Map<String, String> errorMap = new HashMap<String, String>();
            errorMap.put("files", "이미지를 선택하세요.");

            throw new CustomValidationException(errorMap);
        }

        List<BookImage> bookImages = new ArrayList<BookImage>();

        files.forEach(file -> {
            String originFileName = file.getOriginalFilename();
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

            Path uploadPath = Paths.get(filePath + "book/" + tempFileName);

            File f = new File(filePath+ "book");
            if (!f.exists()){
                f.mkdirs();
            }
            try {
                Files.write(uploadPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BookImage bookImage = BookImage.builder()
                    .bookCode(bookCode)
                    .saveName(tempFileName)
                    .originName(originFileName)
                    .build();

            bookImages.add(bookImage);
        });

        bookRepository.registerBookImages(bookImages);
    }

    public List<BookImage> getBooks(String bookCode) {
        return bookRepository.findBookImageAll(bookCode);
    }

    public void removeBookImage(int imageId) {
        BookImage bookImage = bookRepository.findBookImageByImageId(imageId);
        if (bookImage == null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "존재하지 않는 이미지 ID입니다");

            throw new CustomValidationException(errorMap);
        }

        if (bookRepository.deleteBookImage(imageId) > 0) {
            File file = new File(filePath + "book/" + bookImage.getSaveName());
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
