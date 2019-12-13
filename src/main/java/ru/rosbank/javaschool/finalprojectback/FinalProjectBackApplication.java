package ru.rosbank.javaschool.finalprojectback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.rosbank.javaschool.finalprojectback.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.mapper.UserMapper;
import ru.rosbank.javaschool.finalprojectback.mapper.UserMapperImpl;
import ru.rosbank.javaschool.finalprojectback.repository.PostRepository;
import ru.rosbank.javaschool.finalprojectback.repository.UserRepository;
import ru.rosbank.javaschool.finalprojectback.service.UserService;

import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class FinalProjectBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(PostRepository repository, UserService service) {
        return args -> {
            repository.saveAll(List.of(
                    new PostEntity(0, 1, "ivan", LocalDate.now(), "First", null, false, 0),
                    new PostEntity(0, 1, "ivan", LocalDate.now(), "Second", null, false, 0),
                    new PostEntity(0, 1, "ivan", LocalDate.now(), "Third", null, false, 0)
            ));
            service.create("Vasya", "vasya", "secret", "vasya@mail.ru", null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")));
        };
    }
}
