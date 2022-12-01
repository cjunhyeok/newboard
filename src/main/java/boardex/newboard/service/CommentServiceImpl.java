package boardex.newboard.service;

import boardex.newboard.domain.Comment;
import boardex.newboard.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Long saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> displayComment(Long postId) {
        return commentRepository.findAllWithPost(postId);
    }
}
