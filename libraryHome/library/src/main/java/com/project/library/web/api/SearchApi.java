package com.project.library.web.api;

import com.project.library.aop.annotation.ParamsAspect;
import com.project.library.security.PrincipalDetails;
import com.project.library.service.SearchService;
import com.project.library.web.dto.CMRespDto;
import com.project.library.web.dto.SearchBookReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchApi {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<CMRespDto<?>> search(SearchBookReqDto searchBookReqDto,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null) {
            searchBookReqDto.setUserId(principalDetails.getUser().getUserId());
        }
        return ResponseEntity.ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", searchService.getSearchBooks(searchBookReqDto)));
    }

    @ParamsAspect
    @GetMapping("/search/totalcount")
    public ResponseEntity<CMRespDto<Integer>> getSearchBookTotalCount(SearchBookReqDto searchBookReqDto) {
        return ResponseEntity.ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", searchService.getSearchTotalCount(searchBookReqDto)));
    }
}
