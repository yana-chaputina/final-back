package ru.rosbank.javaschool.finalprojectback.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rosbank.javaschool.finalprojectback.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.mapper.UserMapper;
import ru.rosbank.javaschool.finalprojectback.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Test
    void loadUserByUsername() {
        UserRepository repoMock = mock(UserRepository.class);
        UserMapper mapperMock = mock(UserMapper.class);
        PasswordEncoder encoderMock = mock(PasswordEncoder.class);
        PostService postServiceMock = mock(PostService.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        String username = "ivan";
        when(repoMock.findByUsername(username)).thenReturn(Optional.of(user));

        UserService service = new UserService(repoMock, mapperMock, encoderMock, postServiceMock);
        UserEntity actual = (UserEntity) service.loadUserByUsername(username);
        assertEquals(user, actual);
    }

    @Test
    void loadUserByUsernameThrowException() {
        UserRepository repoMock = mock(UserRepository.class);
        UserMapper mapperMock = mock(UserMapper.class);
        PasswordEncoder encoderMock = mock(PasswordEncoder.class);
        PostService postServiceMock = mock(PostService.class);

        String username = "ivan";
        when(repoMock.findByUsername(username)).thenReturn(Optional.empty());

        UserService service = new UserService(repoMock, mapperMock, encoderMock, postServiceMock);
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(username));
    }

    @Test
    void save() {
        UserRepository repoMock = mock(UserRepository.class);
        UserMapper mapperMock = mock(UserMapper.class);
        PasswordEncoder encoderMock = mock(PasswordEncoder.class);
        PostService postServiceMock = mock(PostService.class);

        UserSaveRequestDto dto = new UserSaveRequestDto(0, "Ivan", "ivan", "secret", "ivan@gmail.com", null);
        UserEntity user = new UserEntity(0, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), true, true, true, true);
        UserEntity expected = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), true, true, true, true);


        when(mapperMock.dtoToUserEntity(dto, encoderMock)).thenReturn(user);
        when(repoMock.save(user)).thenReturn(expected);

        UserService service = new UserService(repoMock, mapperMock, encoderMock, postServiceMock);
        UserEntity actual = service.save(dto);
        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        UserRepository repoMock = mock(UserRepository.class);
        UserMapper mapperMock = mock(UserMapper.class);
        PasswordEncoder encoderMock = mock(PasswordEncoder.class);
        PostService postServiceMock = mock(PostService.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), true, true, true, true);
        List<UserEntity> list = new ArrayList<>();
        list.add(user);

        when(repoMock.findAll()).thenReturn(list);

        UserProfileResponseDto dto = new UserProfileResponseDto(1, "Ivan", "ivan", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        List<UserProfileResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);

        when(mapperMock.entityToUserProfileResponseDto(user)).thenReturn(dto);

        UserService service = new UserService(repoMock, mapperMock, encoderMock, postServiceMock);
        List<UserProfileResponseDto> actual = service.getAll();
        assertEquals(listDto, actual);
    }

    @Test
    void searchByUsername() {
        UserRepository repoMock = mock(UserRepository.class);
        UserMapper mapperMock = mock(UserMapper.class);
        PasswordEncoder encoderMock = mock(PasswordEncoder.class);
        PostService postServiceMock = mock(PostService.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), true, true, true, true);
        List<UserEntity> list = new ArrayList<>();
        list.add(user);
        String q = "ivan";

        when(repoMock.findAllByUsernameIs(q)).thenReturn(list);

        UserProfileResponseDto dto = new UserProfileResponseDto(1, "Ivan", "ivan", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        List<UserProfileResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);

        when(mapperMock.entityToUserProfileResponseDto(user)).thenReturn(dto);

        UserService service = new UserService(repoMock, mapperMock, encoderMock, postServiceMock);
        List<UserProfileResponseDto> actual = service.searchByUsername(q);
        assertEquals(listDto, actual);
    }
}