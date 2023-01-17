package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exceptions.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.Feed;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;
import java.util.List;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@RestController
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    PostFeedController(PostService postService) {
        this.postService = postService;
    }

    // "{\"sort\":\"desc\",\"size\":3,\"friends\":[\"puss@boots.com\",\"cat@dogs.net\",\"purrr@luv.me\"]}"
    // {"sort":"desc","size":3,"friends":["puss@boots.com","cat@dogs.net","purrr@luv.me"]}
    @PostMapping
    public List<Post> feedCreator(@RequestBody String feed) throws JsonProcessingException {
        Feed clientFeed = new ObjectMapper().readValue(feed.replaceAll("(^\"|\"$|\\\\)", ""), Feed.class);
        if (!SORTS.contains(clientFeed.getSort())) {
            throw new IncorrectParameterException("Wrong feed parameter sort ", clientFeed.getSort());
        }
        if (clientFeed.getSize() <= 0) {
            throw new IncorrectParameterException("Wrong feed parameter size ", String.valueOf(clientFeed.getSize()));
        }

        return postService.feedCreator(clientFeed);
    }

}