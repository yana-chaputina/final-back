package ru.rosbank.javaschool.finalprojectback.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDto {
    private int id;
    private String name;
    private String username;
    private String email;
    private String photo;
    private Collection<GrantedAuthority> authorities;
}
