package ru.rosbank.javaschool.finalprojectback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {
    @Min(value = 0, message = "error.validation value")
    private int id;
    @NotNull
    private int authorId;
    @NotNull
    private String authorName;
    @NotNull
    @Size(min = 1, message = "error.validation.min_size")
    @Size(max = 1000, message = "error.validation.max_size")
    private String content;
    private String media;
}
