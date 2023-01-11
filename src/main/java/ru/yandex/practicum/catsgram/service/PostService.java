package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.*;

@Service
public class PostService {

    private int id;
    private final UserService userService;
    private final Map<Integer, Post> posts = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public int genId() {
        return ++id;
    }

    public List<Post> findAll() {
        log.debug("Actual quantity of posts: {}", posts.size());

        return new ArrayList<>(posts.values());
    }

    public Post create(Post post) {
        if (isEmailExist(post.getAuthor())) {
            post.setId(genId());
            log.trace("The new post of {}, was append to database", post.getAuthor());
            posts.put(post.getId(), post);

            return post;
        }
        throw new UserNotFoundException(String.format("User %s not found", post.getAuthor()));
    }

    public Post findPostById(int urlId) {
        if (posts.containsKey(urlId)) {
            return posts.get(urlId);
        }
        throw new PostNotFoundException(String.format("Post with id - %d not found", urlId));
    }

    private boolean isEmailExist(String email) {
        return userService.findUserByEmail(email) != null;
    }

}
