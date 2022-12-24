package boardex.newboard.repository;

import boardex.newboard.domain.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static boardex.newboard.domain.QMember.*;
import static boardex.newboard.domain.QPost.*;

@Repository
public class PostRepositoryImpl implements PostRepository{

    @PersistenceContext // Entity Manager 자동주입
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

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

    @Override
    public List<Post> findAllFetchDynamic(String cond, String keyword, Long page) {
        return queryFactory
                .select(post)
                .from(post)
                .join(post.member, member).fetchJoin()
                .where(judgeCond(cond, keyword))
                .orderBy(post.lastModifiedDate.desc())
                .offset((page - 1) * 10)
                .limit(10 + (page - 1) * 10)
                .fetch();
    }

    @Override
    public Post findByIdWithComment(Long postId) {
        return em.createQuery("select distinct p from Post p" +
                " join fetch p.member m" +
                " join fetch p.comments c" +
                " join fetch c.member mm", Post.class)
                .getSingleResult();
    }

    private BooleanExpression judgeCond(String cond, String keyword) {
        if (cond.equals("title")) {
            return titleEq(keyword);
        } else if(cond.equals("nickName")) {
            return nickNameEq(keyword);
        } else {
            return null;
        }
    }

    private BooleanExpression titleEq(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        } else {
            return post.title.eq(keyword);
        }
    }

    private BooleanExpression nickNameEq(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        } else {
            return post.member.nickName.eq(keyword);
        }
    }
}
