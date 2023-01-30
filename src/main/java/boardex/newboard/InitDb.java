package boardex.newboard;

import boardex.newboard.domain.Member;
import boardex.newboard.service.CommentService;
import boardex.newboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PostService postService;
        private final CommentService commentService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public void dbInit() {

            String title = "title";
            String content = "content";
            String commentContent = "댓글입니다.";


            Member member = new Member();
            member.simpleMember("userId", passwordEncoder.encode("password123"), "nick", "ROLE_USER");
            em.persist(member);

            Member member2 = new Member();
            member2.simpleMember("userId2", passwordEncoder.encode("password123"), "nick2", "ROLE_USER");
            em.persist(member2);

            Member member3 = new Member();
            member3.simpleMember("userId3", passwordEncoder.encode("password123"), "nick3", "ROLE_USER");
            em.persist(member3);

            Member member4 = new Member();
            member4.simpleMember("userId4", passwordEncoder.encode("password123"), "nick4", "ROLE_USER");
            em.persist(member4);

            Long postId1 = postService.savePost(member.getId(), title, content);
            Long postId2 = postService.savePost(member.getId(), title + "1", content + "1");
            Long postId3 = postService.savePost(member.getId(), title + "2", content + "2");
            Long postId4 = postService.savePost(member.getId(), title + "3", content + "3");
            Long postId5 = postService.savePost(member.getId(), title + "4", content + "4");
            Long postId6 = postService.savePost(member.getId(), title + "5", content + "5");
            Long postId7 = postService.savePost(member2.getId(), title + "6", content + "6");
            Long postId8 = postService.savePost(member2.getId(), title + "7", content + "7");
            Long postId9 = postService.savePost(member2.getId(), title + "8", content + "8");
            Long postId10 = postService.savePost(member2.getId(), title + "9", content + "9");
            Long postId11 = postService.savePost(member3.getId(), title + "10", content + "10");
            Long postId12 = postService.savePost(member3.getId(), title + "11", content + "11");
            Long postId13 = postService.savePost(member3.getId(), title + "12", content + "12");
            Long postId14 = postService.savePost(member3.getId(), title + "13", content + "13");

            commentService.saveComment(member2.getId(), postId1, commentContent);
            commentService.saveComment(member3.getId(), postId1, "두 번째" + commentContent);
            commentService.saveComment(member4.getId(), postId2, "세 번째" + commentContent);
            commentService.saveComment(member4.getId(), postId3, "네 번째" + commentContent);
            commentService.saveComment(member4.getId(), postId4, "다섯 번째" + commentContent);
        }
    }
}
