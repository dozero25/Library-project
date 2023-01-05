package com.project.library.web.api;

import com.project.library.aop.annotation.ValideAspect;
import com.project.library.web.dto.CMRespDto;
import com.project.library.web.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountApi {

    @ValideAspect
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        return ResponseEntity.created(null)
                .body(new CMRespDto<>("Create a new User", null));
    }
}
