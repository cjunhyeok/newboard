package boardex.newboard.service;

import boardex.newboard.domain.Comment;


public interface CommentService {

    Long saveComment(Long memberId, Long postId, String content);

    Comment findById(Long id);

    void updateComment(Long id, String content);
}
