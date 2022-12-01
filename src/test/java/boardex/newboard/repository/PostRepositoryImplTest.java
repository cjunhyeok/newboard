package boardex.newboard.repository;

import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;

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
}