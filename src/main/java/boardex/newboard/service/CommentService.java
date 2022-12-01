package boardex.newboard.service;

import boardex.newboard.domain.Comment;

import java.util.List;

public interface CommentService {

    Long saveComment(Comment comment);

    List<Comment> displayComment(Long postId);
}
