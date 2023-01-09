package boardex.newboard.service;

import boardex.newboard.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @Test
    @Transactional
    public void findAllWithPost() {
        // given
        Member member = new Member();
        member.simpleMember("id1", "pass1", "nickname1");

    }

}