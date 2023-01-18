package com.korit.library.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserMst {
    @ApiModelProperty(hidden = true)
    private int userId;

    @NotBlank
    @ApiModelProperty(name = "username", value = "사용자 이름", example = "abc", required = true)
    private String username;
    @NotBlank
    @ApiModelProperty(name = "password", value = "비밀번호", example = "1234", required = true)
    private String password;
    @NotBlank
    @ApiModelProperty(name = "repassword", value = "비밀번호 확인", example = "1234", required = true)
    private String repassword;
    @NotBlank
    @ApiModelProperty(name = "name", value = "성명", example = "윤도영", required = true)
    private String name;
    @NotBlank
    @Email
    @ApiModelProperty(name = "email", value = "이메일", example = "abc@naver.com", required = true)
    private String email;

    @ApiModelProperty(hidden = true)
    private List<RoleDtl> roleDtl;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createDate;

    @ApiModelProperty(hidden = true)
    private LocalDateTime updateDate;
}
