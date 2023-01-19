package com.project.library.service;

import com.project.library.exception.CustomValidationException;
import com.project.library.repository.AccountRepository;
import com.project.library.entity.UserMst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public UserMst registerUser(UserMst userMst){
        userMst.setPassword(new BCryptPasswordEncoder().encode(userMst.getPassword()));

        accountRepository.saveUser(userMst);
        accountRepository.saveRole(userMst);

        return userMst;
    }

    public void duplicateUsername(String username){
        UserMst user = accountRepository.findUserByUsername(username);
        if(user != null){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("username", "이미 존재하는 사용자 이름입니다.");

            throw new CustomValidationException(errorMap);
        }
    }

    public void compareToPassword(String password, String repassword) {
        if(!password.equals(repassword)){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("repassword", "비밀번호가 일치하지 않습니다.");

            throw new CustomValidationException(errorMap);
        }
    }
    public UserMst getUser(int userId){
        return accountRepository.findUserByUserId(userId);
    }
}
