package ru.rosbank.javaschool.finalprojectback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;
import ru.rosbank.javaschool.finalprojectback.mapper.PostMapper;
import ru.rosbank.javaschool.finalprojectback.mapper.PostMapperImpl;
import ru.rosbank.javaschool.finalprojectback.repository.PostRepository;
import ru.rosbank.javaschool.finalprojectback.service.PostService;

import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class FinalProjectBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(PostRepository repository) {
        return args -> repository.saveAll(List.of(
                new PostEntity(0, 1, "ivan", LocalDate.now(), "First", null, false, 0),
                new PostEntity(0, 1, "ivan", LocalDate.now(), "Second", null, false, 0),
                new PostEntity(0, 1, "ivan", LocalDate.now(), "Third", null, false, 0)
        ));
    }
}
