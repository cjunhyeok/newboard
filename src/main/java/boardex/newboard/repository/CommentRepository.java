package boardex.newboard.repository;

import boardex.newboard.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Long save(Comment comment);

    Comment findById(Long commentId);

    List<Comment> findAllWithPost(Long postId);
}
