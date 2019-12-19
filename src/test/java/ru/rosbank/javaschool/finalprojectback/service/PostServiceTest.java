package ru.rosbank.javaschool.finalprojectback.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.exception.NotFoundException;
import ru.rosbank.javaschool.finalprojectback.mapper.PostMapper;
import ru.rosbank.javaschool.finalprojectback.repository.PostRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Test
    void save() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostSaveRequestDto dto = new PostSaveRequestDto(1, user, "content", "");
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);

        when(repoMock.save(post)).thenReturn(post);
        when(mapperMock.dtoToPostEntity(dto)).thenReturn(post);

        PostResponseDto expected = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 0);
        when(mapperMock.entityToPostResponseDto(post)).thenReturn(expected);

        PostService service = new PostService(repoMock, mapperMock);
        PostResponseDto actual = service.save(dto);
        assertEquals(expected, actual);
    }

    @Test
    void getSomePosts() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        PostResponseDto dto = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 0);
        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);
        when(mapperMock.entityToPostResponseDto(post)).thenReturn(dto);

        PostService service = new PostService(repoMock, mapperMock);
        List<PostResponseDto> actual = service.getSomePosts(0, 1);
        assertIterableEquals(actual, listDto);
    }

    @Test
    void getCountOfNewPosts() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);
        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repoMock.findById(1)).thenReturn(Optional.of(post));
        when(repoMock.findAll()).thenReturn(list);

        PostService service = new PostService(repoMock, mapperMock);
        int actual = service.getCountOfNewPosts(1);
        assertEquals(0, actual);
    }

    @Test
    void getFirstId() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);

        when(repoMock.findAll()).thenReturn(list);

        PostResponseDto dto = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 0);
        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);
        when(mapperMock.entityToPostResponseDto(post)).thenReturn(dto);

        PostService service = new PostService(repoMock, mapperMock);
        int actual = service.getFirstId();
        assertEquals(1, actual);
    }

    @Test
    void getPostById() {

        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);

        when(repoMock.findById(1)).thenReturn(java.util.Optional.of(post));

        PostResponseDto dto = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 1);

        when(mapperMock.entityToPostResponseDto(post)).thenReturn(dto);

        PostService service = new PostService(repoMock, mapperMock);
        PostResponseDto actual = service.getPostById(1);
        assertEquals(actual, dto);
    }

    @Test
    void getPostByIdThrowExcept() {

        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        when(repoMock.findById(1)).thenReturn(Optional.empty());

        PostService service = new PostService(repoMock, mapperMock);
        assertThrows(NotFoundException.class, () -> service.getPostById(1));
    }

    @Test
    void searchByContent() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        String q = "content";

        when(repoMock.findAllByContentContainsIgnoreCase(q)).thenReturn(list);

        PostResponseDto dto = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 0);
        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);
        when(mapperMock.entityToPostResponseDto(post)).thenReturn(dto);

        PostService service = new PostService(repoMock, mapperMock);
        List<PostResponseDto> actual = service.searchByContent(q);
        assertIterableEquals(actual, listDto);
    }

    @Test
    void likeById() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);
        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);

        when(repoMock.findById(1)).thenReturn(java.util.Optional.of(post));

        PostResponseDto dto = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 1);

        when(mapperMock.entityToPostResponseDto(post)).thenReturn(dto);

        PostService service = new PostService(repoMock, mapperMock);
        PostResponseDto actual = service.likeById(1);
        assertEquals(actual, dto);
    }

    @Test
    void likeByIdThrowExcept() {

        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        when(repoMock.findById(1)).thenReturn(Optional.empty());

        PostService service = new PostService(repoMock, mapperMock);
        assertThrows(NotFoundException.class, () -> service.likeById(1));
    }

    @Test
    void dislikeById() {
        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);
        UserEntity user = new UserEntity(1, "Ivan", "ivan", "secret", "ivan@gmail.com", null, List.of(new SimpleGrantedAuthority("ROLE_USER")), false, false, false, false);
        PostEntity post = new PostEntity(1, user, LocalDate.now(), "content", "", false, 0);

        when(repoMock.findById(1)).thenReturn(java.util.Optional.of(post));

        PostResponseDto dto = new PostResponseDto(1, 1, "Ivan", LocalDate.now(), "content", "", 1);

        when(mapperMock.entityToPostResponseDto(post)).thenReturn(dto);

        PostService service = new PostService(repoMock, mapperMock);
        PostResponseDto actual = service.dislikeById(1);
        assertEquals(actual, dto);
    }

    @Test
    void dislikeByIdThrowExcept() {

        PostRepository repoMock = mock(PostRepository.class);
        PostMapper mapperMock = mock(PostMapper.class);

        when(repoMock.findById(1)).thenReturn(Optional.empty());

        PostService service = new PostService(repoMock, mapperMock);
        assertThrows(NotFoundException.class, () -> service.dislikeById(1));
    }
}