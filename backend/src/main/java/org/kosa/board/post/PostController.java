package org.kosa.board.post;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.kosa.board.post.dto.PostCreateDTO;
import org.kosa.board.post.dto.PostDeleteDTO;
import org.kosa.board.post.dto.PostUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "searchValue", required = false) String searchValue
	) {
        Map<String, Object> map = new HashMap<>();
        map.put("paging", postService.list(page, size, searchValue));
		return ResponseEntity.ok().body(map);
    }

    @GetMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable("id") int id) {
        Map<String, Object> map = new HashMap<>();
        Post post = postService.detail(id);
        map.put("post", post);
        return map;
    }

    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody @Valid PostCreateDTO post) {
        Map<String, Object> map = new HashMap<>();
		postService.create(post);
		map.put("status", "ok");
        return map;
    }

	@PostMapping("/update/{id}")
	public Map<String, Object> update(@PathVariable("id") int id, @RequestBody @Valid PostUpdateDTO post) {
		Map<String, Object> map = new HashMap<String, Object>();
		postService.update(id, post);
		map.put("status", "ok");
		return map;
	}

    @PostMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable("id") int id, @RequestBody @Valid PostDeleteDTO post) {
		Map<String, Object> map = new HashMap<String, Object>();
		postService.delete(id, post);
		map.put("status", "ok");
		return map;
    }

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/delete/{id}")
    public Map<String, Object> adminDelete(@PathVariable("id") int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		postService.adminDelete(id);
		map.put("status", "ok");
		return map;
    }
}
