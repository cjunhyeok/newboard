package boardex.newboard.service;

import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class PostServiceImplTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    EntityManager em;

    @Test
    public void findAllPost() {
        // given
        Member memberA = new Member();
        memberA.simpleMember("id1", "pass1", "nickname1");

        Post post1 = new Post("title1", "content1", memberA);
        Post post2 = new Post("title2", "content2", memberA);

        // when
        postService.savePost(post1);
        postService.savePost(post2);
        memberService.join(memberA);

        List<Post> findPost = postService.findAll();

        //then
        assertEquals(2, findPost.size());
    }
}