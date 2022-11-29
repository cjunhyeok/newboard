package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text") // 텍스트 타입으로 변경
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 관계 매핑
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 글 -> 회원 단방향 매핑

}
