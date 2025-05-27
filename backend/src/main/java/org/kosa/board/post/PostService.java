package org.kosa.board.post;

import java.util.List;

import org.kosa.board.post.dto.PostCreateDTO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> list() {
        return this.postRepository.findAll();
    }

    public void create(PostCreateDTO postCreate) {
        Post post = Post.builder()
            .password(postCreate.getPassword())
            .title(postCreate.getTitle())
            .content(postCreate.getContent())
            .build();
        this.postRepository.save(post);
    }
}
