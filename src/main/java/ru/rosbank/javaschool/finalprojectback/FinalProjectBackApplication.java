package ru.rosbank.javaschool.finalprojectback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rosbank.javaschool.finalprojectback.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.repository.PostRepository;
import ru.rosbank.javaschool.finalprojectback.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class FinalProjectBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(PostRepository repository, UserService service) throws IOException {
        return args -> {
            UserSaveRequestDto vasyaSave = new UserSaveRequestDto(0, "Василий", "superVasya", "secret", "super-vasya@mail.ru", "user.jpeg");
            UserEntity vasya = service.save(vasyaSave);
            UserSaveRequestDto ivanSave = new UserSaveRequestDto(0, "Иван", "ivan95", "secret", "ivan95@gmail.com", "user.jpeg");
            UserEntity ivan = service.save(ivanSave);
            UserSaveRequestDto mashaSave = new UserSaveRequestDto(0, "Маша", "masha", "secret", "mashenka@mail.ru", "user.jpeg");
            UserEntity masha = service.save(mashaSave);
            repository.saveAll(List.of(
                    new PostEntity(0, masha, LocalDate.of(2019, 12, 1), "С первым днем зимы!", "winter.jpg", false, 5),
                    new PostEntity(0, vasya, LocalDate.of(2019, 12, 5), "Всем привет!", null, false, 1),
                    new PostEntity(0, masha, LocalDate.of(2019, 12, 8), "Вы видели,какая погода за окном? Все гулять!", null, false, 12),
                    new PostEntity(0, ivan, LocalDate.of(2019, 12, 12), "Скоро сессия:(", null, false, 78),
                    new PostEntity(0, vasya, LocalDate.of(2019, 12, 13), "Мы сдадим все экзамены!", null, false, 7),
                    new PostEntity(0, masha, LocalDate.now(), "До Нового года осталось 11 дней!", "new-year.jpg", false, 0)
            ));
        };

    }
}
