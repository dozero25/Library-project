package com.project.library.web.api.admin;

import com.project.library.aop.annotation.ParamsAspect;
import com.project.library.aop.annotation.ValidAspect;
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

@Api(tags = {"관리자 도서관리 API"})
@RequestMapping("/api/admin")
@RestController
public class BookApi {

    @Autowired
    private BookService bookService;

    @ParamsAspect
    @ValidAspect
    @GetMapping("/books")
    public ResponseEntity<CMRespDto<List<BookMstDto>>> searchBook(@Valid SearchReqDto searchReqDto, BindingResult bindingResult){
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookService.searchBook(searchReqDto)));
    }

    @GetMapping("/categories")
    public ResponseEntity<CMRespDto<List<CategoryDto>>> getCategories(){
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
                .body(new CMRespDto<>(HttpStatus.OK.value(),"Successfully", true));
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
    public ResponseEntity<CMRespDto<?>> updateBook(@PathVariable String bookCode, @Valid @RequestBody BookReqDto bookReqDto, BindingResult bindingResult) {
        bookService.updateBook(bookReqDto);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

    @ParamsAspect
    @DeleteMapping("book/{bookCode}")
    public ResponseEntity<CMRespDto<?>> deleteBook(@PathVariable String bookCode){
        bookService.removeBook(bookCode);
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
    @GetMapping("/book/{bookCode}/images")
    public ResponseEntity<CMRespDto<?>> getImage(@PathVariable String bookCode){
        List<BookImageDto> bookImageDtos = bookService.getBooks(bookCode);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", bookImageDtos));
    }

    @DeleteMapping("/book/{bookCode}/images/{imageId}")
    public ResponseEntity<CMRespDto<?>> removeBookImg(@PathVariable String bookCode, @PathVariable int imageId) {
        bookService.removeBookImage(imageId);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", true));
    }

}
