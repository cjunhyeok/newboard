package boardex.newboard.api;

import boardex.newboard.domain.Comment;
import boardex.newboard.domain.Post;
import boardex.newboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/posts")
    public Result posts(@RequestParam(required = false) String cond,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "0") Long page) {

        List<PostsResponse> collect = postService.findAllFetchDynamic(cond, keyword, page).stream()
                .map(p -> new PostsResponse(p.getId(), p.getTitle(), p.getMember().getNickName(), p.getLastModifiedDate()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping("/api/posts/{id}")
    public Result post(@PathVariable(name = "id") Long id) {
        Post findPost = postService.findByIdFetchMemberComment(id);
        PostResponse response = new PostResponse(findPost);

        return new Result(response);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    // posts Dto
    @Data
    @AllArgsConstructor
    static class PostsResponse {
        private Long id;
        private String title;
        private String nickName;
        private LocalDateTime lastModifiedTime;
    }

    // Post Dto
    @Data
    static class PostResponse {
        // post
        private Long id;
        private String title;
        private String content;
        private LocalDateTime lastModifiedTime;

        // member
        private String nickName;

        // comment
        private List<CommentDto> comments;

        public PostResponse(Post post) {
            id = post.getId();
            title = post.getTitle();
            content = post.getContent();
            lastModifiedTime = post.getLastModifiedDate();
            nickName = post.getMember().getNickName();
            comments = post.getComments().stream()
                    .map(comment -> new CommentDto(comment))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class CommentDto {
        private Long id;
        private String content;

        public CommentDto(Comment comment) {
            id = comment.getId();
            content = comment.getContent();
        }
    }
}
