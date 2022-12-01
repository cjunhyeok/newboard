package boardex.newboard.service;

import boardex.newboard.domain.Comment;
import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Post post = new Post("title1", "content1", member);
        Comment comment = new Comment("content1", post, member);
        Comment comment2 = new Comment("content2", post, member);
        Comment comment3 = new Comment("content3", post, member);

        // when
        Long postId = postService.savePost(post);
        memberService.join(member);
        commentService.saveComment(comment);
        commentService.saveComment(comment2);
        commentService.saveComment(comment3);

        List<Comment> displayComment = commentService.displayComment(postId);

        // then
        assertEquals(3, displayComment.size());

    }

}