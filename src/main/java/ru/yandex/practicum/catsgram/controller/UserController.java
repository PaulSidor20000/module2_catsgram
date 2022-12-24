package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<String, User> users = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @GetMapping
    public List<User> findAll() {
        log.trace("Quantity of users {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) {
        if (isCreated(user)) {
            throw new UserAlreadyExistException("User already exists");
        } else if (isEmailEmpty(user)) {
            throw new InvalidEmailException("Invalid email input");
        } else {
            users.put(user.getEmail(), user);
            log.trace("The new user {}, was append to database", user.getNickname());
            return user;
        }
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (isEmailEmpty(user)) {
            throw new InvalidEmailException("Invalid email input");
        } else {
            users.put(user.getEmail(), user);
            return user;
        }
    }

    private boolean isCreated(User user) {
        return users.containsKey(user.getEmail());
    }

    private boolean isEmailEmpty(User user) {
        return user.getEmail().isEmpty() || user.getEmail() == null;
    }

}