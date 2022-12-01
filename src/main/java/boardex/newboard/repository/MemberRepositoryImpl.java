package boardex.newboard.repository;

import boardex.newboard.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    @PersistenceContext // Entity Manager 자동주입
    private EntityManager em;

    // 저장
    @Override
    public Long save(Member member) {
        em.persist(member);
        return member.getId(); // 커멘드와 쿼리를 분리한다.
    }

    // Id로 찾기
    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    // 모든 회원 찾기
    @Override
    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 닉네임으로 찾기
    @Override
    public List<Member> findByNickName(String nickName) {
        return em.createQuery("select m from Member m where m.nickName = :nickName", Member.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }

    // 회원 Id로 찾기
    @Override
    public Member findByUserId(Long userId) {
        return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }


}
