package boardex.newboard.service;

import boardex.newboard.domain.Member;
import boardex.newboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void join() {
        // given
        Member member = new Member();
        member.simpleMember("id1", "pass1", "nickname1");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // insert 쿼리 확인
        assertEquals(member, memberRepository.findById(savedId));
    }

    @Test
    public void checkDuplicateNickName() {
        // given
        Member memberA = new Member();
        Member memberB = new Member();

        memberA.simpleMember("id1", "pass1", "nickname1");
        memberB.simpleMember("id2", "pass2", "nickname1");

        // when
        memberService.join(memberA);
        try {
            memberService.join(memberB); // 예외 발생 예상
        } catch (IllegalStateException e) {
            return;
        }

        // then
        fail("예외가 발생해야 한다.");
    }


}