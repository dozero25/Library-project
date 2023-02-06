package com.project.library.web.api;

import com.project.library.web.dto.CMRespDto;
import com.project.library.web.dto.SearchBookReqDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchApi {

    @GetMapping("/search")
    public ResponseEntity<CMRespDto<?>> search(SearchBookReqDto searchBookReqDto) {
        return ResponseEntity.ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", null));
    }
}
