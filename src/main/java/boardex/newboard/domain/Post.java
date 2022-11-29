package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text") // 텍스트 타입으로 변경
    private String content;


    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 글 -> 회원 단방향 매핑

}
