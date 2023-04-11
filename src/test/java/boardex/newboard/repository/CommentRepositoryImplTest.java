package boardex.newboard.repository;

import boardex.newboard.domain.Comment;
import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class CommentRepositoryImplTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void saveComment() {
        // given
        Member member = new Member();
        member.simpleMember("memberA", "pass1", "nickname1", "ROLE_USER");
        Post post = new Post();
        post.createPost(member, "title1", "content1");
        Comment comment = new Comment("content1", member);
        comment.setPost(post);

        // when
        Long savedId = commentRepository.save(comment);
        Comment findComment = commentRepository.findById(savedId);

        // then
        Assertions.assertThat(findComment.getId()).isEqualTo(savedId);


    }
}