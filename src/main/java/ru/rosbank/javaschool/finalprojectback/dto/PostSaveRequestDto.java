package ru.rosbank.javaschool.finalprojectback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {

    private int id;
    @NotNull
    private UserEntity author;
    @NotNull
    @Size(min = 1, message = "error.validation.min_size")
    @Size(max = 255, message = "error.validation.max_size")
    private String content;
    private String media;
}
