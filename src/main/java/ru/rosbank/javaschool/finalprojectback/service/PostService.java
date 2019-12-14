package ru.rosbank.javaschool.finalprojectback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;
import ru.rosbank.javaschool.finalprojectback.exception.NotFoundException;
import ru.rosbank.javaschool.finalprojectback.mapper.PostMapper;
import ru.rosbank.javaschool.finalprojectback.repository.PostRepository;

import java.util.List;
import java.util.Optional;
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

    public void removePostsWithRemovedUser(int id) {
        repository.findAll().stream()
                .filter(o -> o.getAuthor().getId() == id)
                .forEach(o -> o.setRemoved(true));
    }

    public List<PostResponseDto> getSomePosts(int lastPost, int step) {
        return repository.findAll().stream()
                .filter(o -> o.isRemoved() == false)
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .skip(lastPost)
                .limit(step)
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());

    }

    public int getCountOfNewPosts(int firstPostId) {
        Optional<PostEntity> firstPost = repository.findById(firstPostId);
        ;
        List<Optional<PostEntity>> collect = repository.findAll().stream()
                .filter(o -> o.isRemoved() == false)
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .map(Optional::of)
                .collect(Collectors.toList());
        return collect.indexOf(firstPost);
    }
    public int getFirstId() {
        List<PostResponseDto> collect = repository.findAll().stream()
                .filter(o -> o.isRemoved() == false)
                .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
                .limit(1)
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());
        return collect.get(0).getId();
    }

    public PostResponseDto getPostById(int id) {
        return repository.findById(id)
                .map(mapper::entityToPostResponseDto)
                .orElseThrow(NotFoundException::new);
    }

    public void removeById(int id) {
        repository.setRemovedById(id);
    }

    public List<PostResponseDto> searchByContent(String q) {
        return repository.findAllByContentLike(q).stream()
                .filter(o -> o.isRemoved() == false)
                .map(mapper::entityToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto likeById(int id) {
        repository.increaseLikesById(id, 1);
        final PostEntity entity = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapper.entityToPostResponseDto(entity);
    }

    public PostResponseDto dislikeById(int id) {
        repository.increaseLikesById(id, -1);
        final PostEntity entity = repository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapper.entityToPostResponseDto(entity);
    }

}

