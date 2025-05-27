package org.kosa.board.post;

import java.util.List;
import java.util.Optional;

import org.kosa.board.post.dto.PostCreateDTO;
import org.kosa.board.post.dto.PostUpdateDTO;
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

    public Post get(int id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        }
        return null;
    }

    public void update(int id, PostUpdateDTO postUpdate) {
        Post post = this.get(id);
        post.changeTitle(postUpdate.getTitle());
        post.changeContent(postUpdate.getContent());
        this.postRepository.save(post);
    }
}
