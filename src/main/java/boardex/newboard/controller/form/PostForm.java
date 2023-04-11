package boardex.newboard.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {

    private Long id;
    private String title;
    private String content;
    private Long memberId;

}
