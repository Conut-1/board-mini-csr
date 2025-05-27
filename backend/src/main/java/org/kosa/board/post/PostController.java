package org.kosa.board.post;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kosa.board.post.dto.PostCreateDTO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> map = new HashMap<>();
        map.put("postList", postService.list());
        return map;
    }

    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody @Valid PostCreateDTO post, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
		if (bindingResult.hasErrors()) {
			List<String> messages = bindingResult.getFieldErrors().stream().map((err) -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList()); 
			map.put("status", "error");
			map.put("message", messages);
			return map;
		}

		postService.create(post);
		map.put("status", "ok");
        return map;
    }
}
