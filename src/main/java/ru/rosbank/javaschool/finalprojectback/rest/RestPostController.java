package ru.rosbank.javaschool.finalprojectback.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
    private final PostService service;

    @GetMapping(params = {"lastPostId", "step"})
    public List<PostResponseDto> getSomePosts(@RequestParam int lastPostId, @RequestParam int step) {
        return service.getSomePosts(lastPostId, step);
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
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
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
}

