package com.korit.library.security;

import com.korit.library.web.dto.RoleDtlDto;
import com.korit.library.web.dto.RoleMstDto;
import com.korit.library.web.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@RequiredArgsConstructor
@AllArgsConstructor
public class PrincipalDetails implements UserDetails {


    @Getter
    private final UserDto user;
    private Map<String, Object> response;


    // 권한을 리스트로 관리하는 부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        List<RoleDtlDto> roleDtlDtoList = user.getRoleDtlDto();
        for(int i = 0; i < roleDtlDtoList.size(); i++) {
            RoleDtlDto dtl = roleDtlDtoList.get(i); // 0 = ROLE_USER, 1 = ROLE_ADMIN
            RoleMstDto roleMstDto = dtl.getRoleMstDto();
            String roleName = roleMstDto.getRoleName();

            GrantedAuthority role = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return roleName;
                }
            };
            authorities.add(role);
        }

//        user.getRoleDtlDto().forEach(dtl -> {
//            authorities.add(() -> dtl.getRoleMstDto().getRoleName());
//        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /*
        계정 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /*
        계정 잠김 여부
    */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /*
        비밀번호 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /*
        사용자 활성화 여부
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
