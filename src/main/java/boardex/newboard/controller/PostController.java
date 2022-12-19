package boardex.newboard.controller;

import boardex.newboard.SessionConst;
import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
import boardex.newboard.repository.SearchCondition;
import boardex.newboard.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Post post = new Post(form.getTitle(), form.getContent(), loginMember);

        postService.savePost(post);

        return "redirect:/";
    }

    @GetMapping("posts/{postId}/edit")
    public String updatePostForm(@PathVariable Long postId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Post findPost = postService.findFetchMember(postId);
        Member findMember = findPost.getMember();

        if (!findMember.getId().equals(loginMember.getId())) {
            log.info("sessionError");
            log.info("findMember = {}", findMember.getNickName());
            log.info("loginMember = {}", loginMember.getNickName());
            return "hello";
        }

        PostForm postForm = new PostForm();
        postForm.setId(findPost.getId());
        postForm.setTitle(findPost.getTitle());
        postForm.setContent(findPost.getContent());
        postForm.setMemberId(findMember.getId());

        model.addAttribute("form", postForm);

        return "posts/updatePostForm";
    }

    @PostMapping("posts/{postId}/edit")
    public String updatePost(@ModelAttribute("form") PostForm form) {

        postService.updatePost(form.getId(), form.getTitle(), form.getContent()); // 엔티티를 어설프게 넘기는 것이 아닌 폼에서 필요한 정보만 넘긴다

        return "redirect:/";
    }

    @GetMapping("/posts")
    public String listPost(@ModelAttribute("searchCondition") SearchCondition cond, Model model) {
        List<Post> posts = postService.findAllFetchDynamic(cond);
        model.addAttribute("posts", posts);

        return "posts/postList";
    }

}
