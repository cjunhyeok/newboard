package boardex.newboard.controller;

import boardex.newboard.SessionConst;
import boardex.newboard.domain.Member;
import boardex.newboard.domain.Post;
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

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Post findPost = postService.findFetchMember(id);
        Member findMember = findPost.getMember();
        log.info("loginMember : {}", loginMember.getNickName());
        log.info("findMember : {}", findMember.getNickName());

        return "home";
    }

    @GetMapping("/posts")
    public String listPost(Model model) {
        List<Post> posts = postService.findAllFetch();
        model.addAttribute("posts", posts);

        return "posts/postList";
    }

}
