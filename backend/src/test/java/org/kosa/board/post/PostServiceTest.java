package org.kosa.board.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Nested
    @DisplayName("get")
    class Get {
        private Post testPost;

        @BeforeEach
        void setup() {
            testPost = Post.builder()
                .postNo(1)
                .title("test title")
                .password("test password")
                .content("test content")
                .build();
        }

        @Test
        void givenPostNo_whenPostExist_thenReturnPost() {
            when(postRepository.findById(testPost.getPostNo()))
                .thenReturn(Optional.of(testPost));
    
            Post result = postService.get(testPost.getPostNo());
    
            assertSame(testPost, result);
            verify(postRepository, times(1)).findById(testPost.getPostNo());
            verifyNoMoreInteractions(postRepository);
        }

        @Test
        void givenPostNo_whenPostNotExist_thenThrowNotFound() {
            when(postRepository.findById(testPost.getPostNo()))
                .thenReturn(Optional.empty());

            ResponseStatusException exception = assertThrows(
                    ResponseStatusException.class,
                    () -> postService.get(testPost.getPostNo())
                );
            assertEquals("게시글이 없습니다", exception.getReason());
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            verify(postRepository, times(1)).findById(testPost.getPostNo());
            verifyNoMoreInteractions(postRepository);
        }
    }
}
