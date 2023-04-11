package boardex.newboard.controller;

import boardex.newboard.SessionConst;
import boardex.newboard.controller.form.CommentForm;
import boardex.newboard.controller.form.PostForm;
import boardex.newboard.domain.Comment;
import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import boardex.newboard.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/posts/new")
    public String savePostForm(Model model) {

        log.info("in postForm");
        model.addAttribute("postForm", new PostForm());

        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String savePost(@Valid @ModelAttribute("postForm") PostForm form,
                           BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "posts/createPostForm";
        }

        // 로그인한 회원 정보 찾기
//        HttpSession session = request.getSession();
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Member loginMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        postService.savePost(loginMember.getId(), form.getTitle(), form.getContent());

        return "redirect:/";
    }

    @GetMapping("posts/edit/{postId}")
    public String updatePostForm(@PathVariable Long postId, Model model,
                                 @AuthenticationPrincipal Member member) {

        boolean login = false;
        Long loginId = -1L;

//        Member principal = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long loginId = principal.getId();
        if (member != null) {
            loginId = member.getId();
        }


        Post findPost = postService.findFetchMember(postId);
        Member findMember = findPost.getMember();

        if (!findMember.getId().equals(loginId)) {
            return "home";
        }

        PostForm postForm = new PostForm();
        postForm.setId(findPost.getId());
        postForm.setTitle(findPost.getTitle());
        postForm.setContent(findPost.getContent());
        postForm.setMemberId(findMember.getId());

        model.addAttribute("form", postForm);

        return "posts/updatePostForm";
    }

    @PostMapping("posts/edit/{postId}")
    public String updatePost(@ModelAttribute("form") PostForm form) {

        postService.updatePost(form.getId(), form.getTitle(), form.getContent()); // 엔티티를 어설프게 넘기는 것이 아닌 폼에서 필요한 정보만 넘긴다

        return "redirect:/";
    }

    @GetMapping("/posts")
    public String posts(@RequestParam(defaultValue = "no") String cond,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(defaultValue = "1") Long page,
                                          Model model) {

        List<Post> posts = postService.findAllFetchDynamic(cond, keyword, page);
        model.addAttribute("posts", posts);

        return "posts/postList";
    }

    @GetMapping("/posts/{id}")
    public String post(@PathVariable(name = "id") Long id, Model model,
                       @AuthenticationPrincipal Member member) {

        boolean login = false;
        List<Long> myCommentId = new ArrayList<>();
        Long loginId = -1L;

        if (member != null) {
            loginId = member.getId();
        }

//        Member principal = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long loginId = principal.getId();

        Post findPost = postService.findByIdFetchMemberComment(id);
        model.addAttribute("post", findPost);

        CommentForm commentForm = new CommentForm();
        commentForm.setPostId(findPost.getId());
        commentForm.setMemberId(loginId);
        model.addAttribute("commentForm", commentForm);

        if (loginId.equals(findPost.getMember().getId())) {
            login = true;
            model.addAttribute("login", login);
        }

        List<Comment> findComments = findPost.getComments();
        for (Comment findComment : findComments) {
            if (findComment.getMember().getId().equals(loginId)) {
                myCommentId.add(findComment.getId());
                model.addAttribute("myCommentId", myCommentId);
            }
        }


        return "posts/post";
    }

}
