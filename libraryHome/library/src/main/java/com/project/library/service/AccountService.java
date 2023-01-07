package com.project.library.service;

import com.project.library.exception.CustomValidationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    public void duplicateUsername(String username){}

    public void compareToPassword(String password, String repassword) {
        if(!password.equals(repassword)){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("repassword", "비밀번호가 일치하지 않습니다.");

            throw new CustomValidationException(errorMap);
        }
    }
}
