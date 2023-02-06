package com.project.library.web.api.admin;

import com.project.library.aop.annotation.ParamsAspect;
import com.project.library.aop.annotation.ValidAspect;
import com.project.library.entity.BookImage;
import com.project.library.entity.BookMst;
import com.project.library.entity.CategoryView;
import com.project.library.service.admin.BookService;
import com.project.library.web.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = {"관리자 도서관리 API"})
@RequestMapping("/api/admin")
@RestController
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
