package boardex.newboard.controller.form;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CommentForm {

    private String content;
    private Long memberId;
    private Long postId;
}
