package boardex.newboard.controller;

import boardex.newboard.controller.form.CommentForm;
import boardex.newboard.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/new")
    public String saveComment(@Valid CommentForm commentForm, BindingResult bindingResult) {

        commentService.saveComment(commentForm.getMemberId(), commentForm.getPostId(), commentForm.getContent());

        return "redirect:/posts/" + commentForm.getPostId();
    }
}
