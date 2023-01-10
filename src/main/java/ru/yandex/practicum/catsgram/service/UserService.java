package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User create(User user) {
        if (isUserExist(user)) {
            throw new UserAlreadyExistException("User already exists");
        } else if (isEmailEmpty(user)) {
            throw new InvalidEmailException("Invalid email input");
        } else {
            users.put(user.getEmail(), user);
            log.trace("The new user {}, was append to database", user.getNickname());
            return user;
        }
    }

    public User update(User user) {
        if (isEmailEmpty(user)) {
            throw new InvalidEmailException("Invalid email input");
        } else {
            users.put(user.getEmail(), user);
            return user;
        }
    }

    private boolean isUserExist(User user) {
        return users.containsKey(user.getEmail());
    }

    private boolean isEmailEmpty(User user) {
        return user.getEmail().isEmpty() || user.getEmail() == null;
    }

    public User findUserByEmail(String email) {
        return users.getOrDefault(email, null);
    }

}
