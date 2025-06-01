package org.kosa.board.post;

import java.util.ArrayList;
import java.util.List;

import org.kosa.board.page.PageResponseVO;
import org.kosa.board.post.dto.PostCreateDTO;
import org.kosa.board.post.dto.PostDeleteDTO;
import org.kosa.board.post.dto.PostUpdateDTO;
import org.kosa.board.post.summary.PostDetailSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PageResponseVO<Post> list(int pageNo, int size, String searchValue) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(pageNo, size, Sort.by(sorts));
		Page<Post> page = searchValue == null
			? this.postRepository.findAll(pageable)
			: this.postRepository.findByTitleContaining(searchValue, pageable);
		return new PageResponseVO<>(page, 10);
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
        return this.postRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 없습니다"));
    }

    public PostDetailSummary detail(int id) {
        return this.postRepository.findDetailByPostNo(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 없습니다"));
    }

    public void incrementViews(int id) {
        Post post = this.get(id);
        post.changeViews(post.getViews() + 1);
        this.postRepository.save(post);
    }

    public void update(int id, PostUpdateDTO postUpdate) {
        Post post = this.get(id);
        if (!post.getPassword().equals(postUpdate.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 틀렸습니다.");
        }
        post.changeTitle(postUpdate.getTitle());
        post.changeContent(postUpdate.getContent());
        this.postRepository.save(post);
    }

    public void adminDelete(int id) {
        Post post = this.get(id);
        this.postRepository.delete(post);
    }

    public void delete(int id, PostDeleteDTO postDelete) {
        Post post = this.get(id);
        if (!post.getPassword().equals(postDelete.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 틀렸습니다.");
        }
        this.postRepository.delete(post);
    }
}
