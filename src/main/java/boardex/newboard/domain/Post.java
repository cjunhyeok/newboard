package boardex.newboard.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    // create
    public void createPost(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.setCreatedDate(LocalDateTime.now());
        this.setLastModifiedDate(LocalDateTime.now());
    }

    // update
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        this.setLastModifiedDate(LocalDateTime.now());
    }
}
