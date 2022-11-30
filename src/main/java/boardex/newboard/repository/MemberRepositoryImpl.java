package boardex.newboard.repository;

import boardex.newboard.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); // 커멘드와 쿼리를 분리한다.
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }
}
