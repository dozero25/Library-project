package com.project.library.web.api;

import com.project.library.security.PrincipalDetails;
import com.project.library.service.RentalService;
import com.project.library.web.dto.CMRespDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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

    private final RentalService rentalService;

    @PostMapping("rental/{bookId}")
    public ResponseEntity<CMRespDto<?>> rental(@PathVariable int bookId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        rentalService.rentalOne(principalDetails.getUser().getUserId(), bookId);
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Successfully", null));
    }
}
