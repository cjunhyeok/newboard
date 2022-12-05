package boardex.newboard.repository;

import boardex.newboard.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository{

    @PersistenceContext // Entity Manager 자동주입
    private EntityManager em;

    @Override
    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    @Override
    public Post findFetchMember(Long postId) {
        return em.createQuery("select p from Post p join fetch p.member m where p.id = :postId", Post.class)
                .setParameter("postId", postId)
                .getSingleResult();
    }

    @Override
    public List<Post> findAllFetch() {
        return em.createQuery("select p from Post p join fetch p.member", Post.class)
                .getResultList();
    }

    // 동적쿼리는 차후 querydsl로 사용

}
