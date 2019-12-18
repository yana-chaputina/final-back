package ru.rosbank.javaschool.finalprojectback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.exception.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PostService postService;

    public boolean isOperationAvailable(int postId, UserEntity entity) {
        PostResponseDto postDto = postService.getPostById(postId);
        if (postDto.getAuthorId() == entity.getId()) {
            return true;
        } else throw new AccessDeniedException();
    }

}
