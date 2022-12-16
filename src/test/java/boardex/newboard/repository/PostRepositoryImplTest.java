package boardex.newboard.repository;

import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;

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
        SearchCondition cond = new SearchCondition();
        cond.setTitle("title1");
        cond.setNickName(null);

        // when
        memberRepository.save(member);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        // then
        List<Post> findPost = postRepository.findAllFetchDynamic(cond);
        assertEquals(1, findPost.size());

    }
}