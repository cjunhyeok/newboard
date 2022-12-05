package boardex.newboard.controller;

import boardex.newboard.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {

    private String title;
    private String content;
    private Member member;
}
