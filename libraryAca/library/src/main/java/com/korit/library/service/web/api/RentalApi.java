package com.korit.library.service.web.api;

import com.korit.library.security.PrincipalDetails;
import com.korit.library.service.RentalService;
import com.korit.library.service.web.dto.CMRespDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"도서 대여 API"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalApi {
    /*
        /rental/{bookId}
        대여 요청 -> 대여 요청 날린 사용자의 대여가능 여부확인
        ->  가능함(대여가능 횟수 3권 미만일 때) -> 대여 정보 추가 rental_mst(대여코드), rental_dtl
        ->  불가능함(대여가능 횟수 0이면) -> 예외처리
     */

    private final RentalService rentalService;
    // @RequiredArgsConstructor 와 final을 달아주면 @Autowired가 된다.
    // 무조건 final을 달아주고 @RequiredArgsConstructor을 사용한다.

    @PostMapping("rental/{bookId}")
    public ResponseEntity<CMRespDto<?>> rental(@PathVariable int bookId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        rentalService.rentalOne(principalDetails.getUser().getUserId(), bookId);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully",  null));
    }

}
