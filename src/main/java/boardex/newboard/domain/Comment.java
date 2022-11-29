package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.*;

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
    private Post post; // 댓글 -> 게시글 단방향

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
