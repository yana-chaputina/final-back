package ru.rosbank.javaschool.finalprojectback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PostService postService;

    public boolean isPostRemoveAvailable(int postId, UserEntity entity) {
        PostResponseDto postDto = postService.getPostById(postId);
        return postDto.getAuthorId() == entity.getId();
    }

    public boolean isPostUpdateAvailable(int postId, UserEntity entity) {
        PostResponseDto postDto = postService.getPostById(postId);
        return postDto.getAuthorId() == entity.getId();
    }
}
