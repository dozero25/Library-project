package com.project.library.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleMstDto {
    private int roleId;
    private String roleName;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
