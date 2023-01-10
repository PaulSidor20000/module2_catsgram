package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }


    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());

        return posts;
    }

    public Post create(Post post) {
        if (isEmailExist(post.getAuthor())) {
            log.trace("The new post of {}, was append to database", post.getAuthor());
            posts.add(post);
            return post;
        }
        throw new UserNotFoundException(String.format("Пользователь %s не найден", post.getAuthor()));
    }

    private boolean isEmailExist(String email) {
        return userService.findUserByEmail(email) != null;
    }

}
