package boardex.newboard.service;

import boardex.newboard.domain.Comment;


public interface CommentService {

    Long saveComment(Comment comment);

    Comment findById(Long id);

    void updateComment(Long id, String content);
}
