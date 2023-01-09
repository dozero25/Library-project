package com.korit.library.web.api;


import com.korit.library.aop.annotation.ValideAspect;
import com.korit.library.service.AccountService;
import com.korit.library.web.dto.CMRespDto;
import com.korit.library.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/account")
public class AccountApi {

    @Autowired
    private AccountService accountService;

    @ValideAspect
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){

        accountService.duplicateUsername(userDto.getUsername());
        accountService.compareToPassword(userDto.getPassword(), userDto.getRepassword());

        UserDto user = accountService.registerUser(userDto);

        return ResponseEntity
                .created(URI.create("/api/account/user/"+user.getUserId()))
                .body(new CMRespDto<>(HttpStatus.CREATED.value(),"Create a new User", user));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId){
        return ResponseEntity
                .ok()
                .body(new CMRespDto<>(HttpStatus.OK.value(), "Success", accountService.getUser(userId)));
    }
}

