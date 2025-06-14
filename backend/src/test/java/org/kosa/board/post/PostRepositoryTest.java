package org.kosa.board.post;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.kosa.board.post.summary.PostDetailSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Nested
    @DisplayName("findDetailByPostNo")
    class FindDetailByPostNo {
        private Post testPost;

        @BeforeEach
        void setup() {
            Post post = Post.builder()
                .title("test title")
                .password("test password")
                .content("test content")
                .views(123)
                .build();
            testPost = entityManager.persist(post);
        }

        @Test
        void success() {
            Optional<PostDetailSummary> result = postRepository.findDetailByPostNo(testPost.getPostNo());

            assertTrue(result.isPresent());
            PostDetailSummary summary = result.get();

            assertEquals(testPost.getPostNo(), summary.getPostNo());
            assertEquals(testPost.getTitle(), summary.getTitle());
            assertEquals(testPost.getContent(), summary.getContent());
            assertEquals(testPost.getViews(), summary.getViews());
            assertEquals(testPost.getCreateDate(), summary.getCreateDate());
        }

        @Test
        void notFound() {
            Optional<PostDetailSummary> result = postRepository.findDetailByPostNo(-1);
            assertTrue(result.isEmpty());
        }
    }
}
