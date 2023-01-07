package com.project.library.repository;

import com.project.library.web.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    public UserDto findUserByUsername(String username);
}
