package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;
import java.util.List;

@RestController
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public List<Post> feedCreator(@RequestBody String feed) throws JsonProcessingException {
        return postService.feedCreator(feed);
    }

}