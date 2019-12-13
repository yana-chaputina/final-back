package ru.rosbank.javaschool.finalprojectback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int authorId;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private LocalDate created;
    private String content;
    private String media;
    private boolean removed;
    private int likes;
}
