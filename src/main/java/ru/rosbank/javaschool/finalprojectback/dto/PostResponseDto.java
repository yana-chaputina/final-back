package ru.rosbank.javaschool.finalprojectback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private int id;
    private int authorId;
    private String authorName;
    private LocalDate created;
    private String content;
    private String media;
    private int likes;
}
