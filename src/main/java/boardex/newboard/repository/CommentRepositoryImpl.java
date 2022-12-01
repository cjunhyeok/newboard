package boardex.newboard.repository;

import boardex.newboard.domain.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public Comment findById(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public List<Comment> findAllWithPost(Long postId) {
        return em.createQuery("select c from Comment c where c.post.id = :post", Comment.class)
                .setParameter("post", postId)
                .getResultList();
    }
}
