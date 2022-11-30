package boardex.newboard.repository;

import boardex.newboard.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryImplTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember() {
        //given
        Member member = new Member();
        member.simpleMember("member1", "pass1", "nickName1");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

    }
}