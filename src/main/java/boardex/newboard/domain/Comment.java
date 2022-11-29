package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "text") // 텍스트 타입
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 관계 매핑
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; // 댓글 -> 게시글 단방향

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
