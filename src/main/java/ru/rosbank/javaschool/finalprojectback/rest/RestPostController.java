package ru.rosbank.javaschool.finalprojectback.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;
import ru.rosbank.javaschool.finalprojectback.service.PermissionService;
import ru.rosbank.javaschool.finalprojectback.service.PostService;
import ru.rosbank.javaschool.finalprojectback.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
    private final PostService service;
    private final UserService userService;
    private final PermissionService permissionService;

    @GetMapping(params = {"lastPost", "step"})
    public List<PostResponseDto> getSomePosts(@RequestParam int lastPost, @RequestParam int step) {
        return service.getSomePosts(lastPost, step);
    }

    @GetMapping(params = {"firstPostId"})
    public int getCountOfNewPosts(@RequestParam int firstPostId) {
        return service.getCountOfNewPosts(firstPostId);
    }

    @GetMapping
    public int getFirstPostId() {
        return service.getFirstId();
    }

    @GetMapping(params = "q")
    public List<PostResponseDto> searchByContent(@RequestParam String q) {
        return service.searchByContent(q);
    }

    @PostMapping
    public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {

        UserEntity user = getAuthorizedUser();
        if (dto.getId() != 0) {
            permissionService.isOperationAvailable(dto.getId(), user);
        }
        dto.setAuthor(user);
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        UserEntity user = getAuthorizedUser();
        permissionService.isOperationAvailable(id, user);
        service.removeById(id);
    }

    @PostMapping("/{id}/likes")
    public PostResponseDto likeById(@PathVariable int id) {
        return service.likeById(id);
    }

    @DeleteMapping("/{id}/likes")
    public PostResponseDto dislikeById(@PathVariable int id) {
        return service.dislikeById(id);
    }


    private UserEntity getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = userService.loadUserByUsername(auth.getName());
        return (UserEntity) user;
    }
}

