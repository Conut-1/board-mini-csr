package org.kosa.board.post.summary;

import java.time.LocalDateTime;

public interface PostDetailSummary {
    int getPostNo();
    String getTitle();
    String getContent();
    LocalDateTime getCreateDate();
    int getViews();
}
