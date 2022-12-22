package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Set<User> users = new HashSet<>();

    @GetMapping
    public Set<User> findAll() {
        return users;
    }

    @PostMapping
    public void create(@RequestBody User user) {
        if (isCreated(user)) {
            throw new UserAlreadyExistException();
        } else if (isEmailEmpty(user)) {
            throw new InvalidEmailException();
        } else {
            users.add(user);
        }
    }

    @PutMapping
    public void update(@RequestBody User user) {
        if (isEmailEmpty(user)) {
            throw new InvalidEmailException();
        } else {
            users.add(user);
        }
    }

    private boolean isCreated(User user) {
        return users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }
    private boolean isEmailEmpty(User user) {
        return user.getEmail().isEmpty() || user.getEmail() == null;
    }

}