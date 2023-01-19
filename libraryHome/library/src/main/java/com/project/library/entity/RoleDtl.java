package com.project.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDtl {
    private int roleDtlId;
    private int userId;
    private int roleId;

    private RoleMst roleMst;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
