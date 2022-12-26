package boardex.newboard.api;

import boardex.newboard.domain.Comment;
import boardex.newboard.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class CommentApiController {

    private final CommentService commentService;

    @Autowired
    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PutMapping("api/comment/{id}")
    public UpdateCommentResponse updateComment(@PathVariable(name = "id") Long id,
                                               @RequestBody @Valid UpdateCommentRequest request) {

        commentService.updateComment(id, request.getContent());
        Comment updateComment = commentService.findById(id);

        return new UpdateCommentResponse(updateComment.getId());
    }

    // updateComment Dto
    @Data
    @AllArgsConstructor
    static class UpdateCommentResponse {
        private Long id;
    }

    @Data
    @AllArgsConstructor
    static class UpdateCommentRequest {
        private String content;
    }
}
