package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "text") // 텍스트 타입
    private String content;

    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 댓글 -> 게시글 양방향 (게시글이 다 게시글에서 fk 관리)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 댓글 수정
    public void updateComment(String content) {
        this.content = content;
    }

    // 연관관계 편의 메서드
    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public Comment() {
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    public Comment(String content, Member member) {
        this.content = content;
        this.member = member;
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }
}
