package com.korit.library.service;

import com.korit.library.exception.CustomValidationException;
import com.korit.library.repository.AccountRepository;
import com.korit.library.entity.UserMst;
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
        // DB에 저장하기 전에 암호화, 암호화가 되면 글자수가 길어지기 때문에 mysql에 user_mst의 password를 100으로 설정한다.
        accountRepository.saveUser(userMst);
        accountRepository.saveRole(userMst);
        return userMst;
    }

    public void duplicateUsername(String username){
        UserMst user = accountRepository.findUserByUsername(username);
        if (user != null){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("username", "이미 존재하는 사용자이름입니다.");

            throw new CustomValidationException(errorMap);
        }
    }

    public void compareToPassword(String password, String repassword){
        if (!password.equals(repassword)){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("repassword", "비밀번호가 일치하지 않습니다.");

            throw new CustomValidationException(errorMap);
        }
    }

    public UserMst getUser(int userId){
        return accountRepository.findUserByUserId(userId);
    }
}
