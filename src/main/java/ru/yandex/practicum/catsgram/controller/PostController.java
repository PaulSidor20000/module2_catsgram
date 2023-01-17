package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> findAll(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "desc", required = false) String sort
            ) {
        if (!SORTS.contains(sort)) {
            throw new IncorrectParameterException("Wrong parameter sort: ", sort);
        }
        if (size <= 0) {
            throw new IncorrectParameterException("Wrong parameter size: ", String.valueOf(size));
        }
        if (page < 0) {
            throw new IncorrectParameterException("Wrong parameter page: ", String.valueOf(page));
        }
        int from = size * page;

        return postService.findAll(sort, size, from);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @GetMapping("/{id}")
    public Post findPostById(@PathVariable("id") int urlId) {
        return postService.findPostById(urlId);
    }

}