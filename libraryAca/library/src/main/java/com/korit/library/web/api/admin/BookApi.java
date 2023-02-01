package com.korit.library.web.api.admin;

import com.korit.library.aop.annotation.ParamsAspect;
import com.korit.library.aop.annotation.ValidAspect;
import com.korit.library.entity.BookImage;
import com.korit.library.entity.BookMst;
import com.korit.library.entity.CategoryView;
import com.korit.library.service.admin.BookService;
import com.korit.library.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"관리자 도서관리 API"})
@RequestMapping("/api/admin")
@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{bookCode}")
    public ResponseEntity<CMRespDto<Map<String, Object>>> getBook(@PathVariable String bookCode) {

        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookService.getBookAndImage(bookCode)));
    }

    @ParamsAspect
    @ValidAspect
    @GetMapping("/books")
    public ResponseEntity<CMRespDto<List<BookMst>>> searchBook(@Valid SearchReqDto searchReqDto, BindingResult bindingResult){

        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookService.searchBook(searchReqDto)));

    }

    @GetMapping("/books/totalcount")
    public ResponseEntity<CMRespDto<?>> getBookTotalCount(SearchNumberListReqDto searchNumberListReqDto){
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookService.getBookTotalCount(searchNumberListReqDto)));
    }

    @GetMapping("/categories")
    public ResponseEntity<CMRespDto<List<CategoryView>>> getCategories(){
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookService.getCategories()));
    }

    @ParamsAspect
    @ValidAspect
    @PostMapping("/book")
    public ResponseEntity<CMRespDto<?>> registerBook(@Valid @RequestBody BookReqDto bookReqDto, BindingResult bindingResult){
        bookService.registerBook(bookReqDto);
        return ResponseEntity
                .created(null)
                .body(new CMRespDto<>(HttpStatus.CREATED.value(), "Successfully", true));
    }

    @ParamsAspect
    @ValidAspect
    @PutMapping("/book/{bookCode}")
    // 수정은 두가지 방법이 있다.
    // PutMapping() : 아무것도 입력 안할 경우 그대로 저장이된다. 요청 날라온 데이터를 그대로 업데이트 한다.
    // PatchMapping() : 아무것도 입력 안할 경우 기존의 데이터를 유지하고 수정된 데이터만 업데이트 한다.
    public ResponseEntity<CMRespDto<?>> modifyBook(@PathVariable String bookCode, @Valid @RequestBody BookReqDto bookReqDto, BindingResult bindingResult){
        bookService.modifyBook(bookReqDto);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

    @ParamsAspect
    @ValidAspect
    @PatchMapping("/book/{bookCode}")
    public ResponseEntity<CMRespDto<?>> updateBook(@PathVariable String bookCode, @Valid @RequestBody BookReqDto bookReqDto, BindingResult bindingResult){
        bookService.updateBook(bookReqDto);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

    @ParamsAspect
    @DeleteMapping("/book/{bookCode}")
    public ResponseEntity<CMRespDto<?>> removeBook(@PathVariable String bookCode){
        bookService.removeBook(bookCode);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

    @ParamsAspect
    @DeleteMapping("/books")
    public ResponseEntity<CMRespDto<?>> removeBooks(@RequestBody DeleteBooksReqDto deleteBooksReqDto){
        bookService.removeBooks(deleteBooksReqDto);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }


    @ParamsAspect
    @PostMapping("/book/{bookCode}/images")
    public ResponseEntity<CMRespDto<?>> registerBookImg(@PathVariable String bookCode, @ApiParam(required = false) @RequestPart List<MultipartFile> files){
        bookService.registerBookImages(bookCode, files);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

    @ParamsAspect
    @PostMapping("/book/{bookCode}/images/modification")
    public ResponseEntity<CMRespDto<?>> modifyBookImg(@PathVariable String bookCode, @ApiParam(required = false) @RequestPart List<MultipartFile> files){
        bookService.registerBookImages(bookCode, files);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

    @ParamsAspect
    @GetMapping("/book/{bookCode}/images")
    public ResponseEntity<CMRespDto<?>> getImages(@PathVariable String bookCode) {
        List<BookImage> bookImages = bookService.getBooks(bookCode);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookImages));
    }

    @DeleteMapping("/book/{bookCode}/image/{imageId}")
    public ResponseEntity<CMRespDto<?>> removeBookImg(@PathVariable String bookCode, @PathVariable int imageId) {
        bookService.removeBookImage(imageId);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", null));
    }



}

