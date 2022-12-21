package boardex.newboard.repository;

import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void savePost() {
        // given
        Member member = new Member();
        member.simpleMember("memberA", "pass1", "nickname1");
        Post post = new Post("title1", "content1", member);

        // when
        Long savedId = postRepository.save(post);
        Post findPost = postRepository.findById(savedId);

        // then
        Assertions.assertThat(findPost.getId()).isEqualTo(savedId);
        System.out.println(findPost.getMember().getNickName());
    }

    @Test
    @Transactional
    public void findAllFetchDynamic() {
        // given
        Member member = new Member();
        member.simpleMember("id1", "pass1", "nick1");
        Post post1 = new Post("title1", "content1", member);
        Post post2 = new Post("title2", "content2", member);
        Post post3 = new Post("title3", "content3", member);
        Post post4 = new Post("title4", "content4", member);
        Post post5 = new Post("title4", "content5", member);
        Post post6 = new Post("title4", "content6", member);
        Post post7 = new Post("title4", "content7", member);
        Post post8 = new Post("title4", "content8", member);
        Post post9 = new Post("title4", "content9", member);
        Post post10 = new Post("title4", "content10", member);
        Post post11 = new Post("title4", "content11", member);
        Post post12 = new Post("title4", "content12", member);

        // when
        memberRepository.save(member);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        postRepository.save(post6);
        postRepository.save(post7);
        postRepository.save(post8);
        postRepository.save(post9);
        postRepository.save(post10);
        postRepository.save(post11);
        postRepository.save(post12);

        // then
        List<Post> findPost = postRepository.findAllFetchDynamic("nickName", "nick1", 2L);
        assertEquals(2, findPost.size());

    }

    @Test
    @Transactional
    public void findByIdTest() {
        // givne
        Member member = new Member();
        member.simpleMember("id1", "pass1", "nick1");
        Post post = new Post("title1", "content1", member);

        // when
        memberRepository.save(member);
        Long savedId = postRepository.save(post);

        em.flush();
        em.clear();
        Post findPost = postRepository.findById(savedId);

        // then
        System.out.println(findPost.getMember().getNickName());
    }
}