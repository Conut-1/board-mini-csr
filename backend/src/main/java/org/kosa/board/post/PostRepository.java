package org.kosa.board.post;

import java.util.Optional;

import org.kosa.board.post.summary.PostDetailSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findByTitleContaining(String searchValue, Pageable pageable);
    Optional<PostDetailSummary> findDetailByPostNo(int id);
}
