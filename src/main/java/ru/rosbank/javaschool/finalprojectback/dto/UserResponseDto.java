package ru.rosbank.javaschool.finalprojectback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String name;
    private String username;
    private String email;
}

