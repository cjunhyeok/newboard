package boardex.newboard.repository;

import boardex.newboard.domain.Comment;


public interface CommentRepository {

    Long save(Comment comment);

    Comment findById(Long commentId);
}
