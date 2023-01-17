package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Feed;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.catsgram.Constants.ASC_ORDER;
import static ru.yandex.practicum.catsgram.Constants.DESC_ORDER;

@Service
public class PostService {

    private int id;
    private final UserService userService;
    private final List<Post> posts = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    public PostService(UserService userService) {
        this.userService = userService;
    }

    public int genId() {
        return ++id;
    }

    private boolean isEmailExist(String email) {
        return userService.findUserByEmail(email) != null;
    }

    public List<Post> findAll(String sort, int size, int from) {
        log.debug("Actual quantity of posts: {}", posts.size());

        if (sort.equals("asc")) {
            posts.sort(Comparator.comparing(Post::getCreationDate));
        } else if (sort.equals("desc")) {
            posts.sort(Comparator.comparing(Post::getCreationDate).reversed());
        }

        return posts.stream().skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {
        if (isEmailExist(post.getAuthor())) {
            post.setId(genId());
            log.trace("The new post of {}, was append to database", post.getAuthor());
            posts.add(post);

            return post;
        }
        throw new UserNotFoundException(String.format("User %s not found", post.getAuthor()));
    }

    public Post findPostById(int urlId) {
        return posts.stream()
                .filter(post1 -> post1.getId() == urlId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id - %d not found", urlId)));
    }

    // "{\"sort\":\"desc\",\"size\":3,\"friends\":[\"puss@boots.com\",\"cat@dogs.net\",\"purrr@luv.me\"]}"
    // {"sort":"desc","size":3,"friends":["puss@boots.com","cat@dogs.net","purrr@luv.me"]}
    public List<Post> feedCreator(Feed feed) {
        List<Post> userFeedPosts;
        List<Post> allFeedPosts = new ArrayList<>();

        for (String email : feed.getFriends()) {
            if (userService.findUserByEmail(email) != null) {
                userFeedPosts = posts.stream()
                        .filter(post -> post.getAuthor().equals(email))
                        .collect(Collectors.toList());
            } else {
                throw new UserNotFoundException(String.format("User: %s in feed not found", email));
            }
            if (feed.getSort().equals(ASC_ORDER)) {
                userFeedPosts.sort(Comparator.comparing(Post::getCreationDate));
            } else if (feed.getSort().equals(DESC_ORDER)) {
                userFeedPosts.sort(Comparator.comparing(Post::getCreationDate).reversed());
            }
            allFeedPosts.addAll(userFeedPosts.stream().limit(feed.getSize()).collect(Collectors.toList()));
        }

        if (feed.getSort().equals(DESC_ORDER)) {
            return allFeedPosts.stream()
                    .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                    .collect(Collectors.toList());
        }
        return allFeedPosts.stream()
                .sorted(Comparator.comparing(Post::getCreationDate))
                .collect(Collectors.toList());
    }

}
