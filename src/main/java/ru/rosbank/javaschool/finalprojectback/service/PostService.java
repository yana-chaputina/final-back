package ru.rosbank.javaschool.finalprojectback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;
import ru.rosbank.javaschool.finalprojectback.exception.BadRequestException;
import ru.rosbank.javaschool.finalprojectback.exception.NotFoundException;
import ru.rosbank.javaschool.finalprojectback.mapper.PostMapper;
import ru.rosbank.javaschool.finalprojectback.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;


    public PostResponseDto save(PostSaveRequestDto dto) {
        return mapper.entityToPostResponseDto(repository.save(mapper.dtoToPostEntity(dto)));
    }

    public List<PostResponseDto> getSomePosts(int lastPost, int step) {
        return repository.getReversePosts().stream()
                .skip(lastPost)
                .limit(step)
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());

    }

    public int getCountOfNewPosts(int firstPostId) {
        List<PostEntity> collect = repository.getSomePosts(firstPostId);
        return collect.size();
    }

    public int getFirstId() {
        return repository.findFirst().getId();
    }

    public void removeById(int id) {
        repository.deleteById(id);
    }

    public List<PostResponseDto> searchByContent(String q) {
        return repository.findAllByContentLike(q).stream()
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto likeById(int id) {
        repository.increaseLikesById(id, 1);
        final PostEntity entity = repository.findById(id)
                .orElseThrow(BadRequestException::new);
        return mapper.entityToPostResponseDto(entity);
    }

    public PostResponseDto dislikeById(int id) {
        repository.increaseLikesById(id, -1);
        final PostEntity entity = repository.findById(id)
                .orElseThrow(BadRequestException::new);
        return mapper.entityToPostResponseDto(entity);
    }

}

