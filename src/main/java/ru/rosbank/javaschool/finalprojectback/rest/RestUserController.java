package ru.rosbank.javaschool.finalprojectback.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.finalprojectback.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.mapper.UserMapper;
import ru.rosbank.javaschool.finalprojectback.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/me")
    public UserProfileResponseDto me(@AuthenticationPrincipal UserEntity entity) {
        return mapper.entityToUserProfileResponseDto(entity);
    }

    @GetMapping(params = "q")
    public List<UserProfileResponseDto> searchByContent(@RequestParam String q) {
        return service.searchByUsername(q);
    }

    @GetMapping()
    public List<UserProfileResponseDto> getUsers() {
        return service.getAll();
    }

    @PostMapping
    public UserProfileResponseDto save(@RequestBody UserSaveRequestDto dto) {
        return mapper.entityToUserProfileResponseDto(service.save(dto));
    }
}
