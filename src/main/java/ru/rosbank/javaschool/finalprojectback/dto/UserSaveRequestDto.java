package ru.rosbank.javaschool.finalprojectback.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.finalprojectback.constraint.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequestDto {

    @Min(value = 0, message = "error.validation.value")
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    @Size(min = 4, message = "error.validation.min_size")
    private String password;
    @NotNull
    @Email(message = "error.validation.email")
    private String email;

    private String photo;

}
