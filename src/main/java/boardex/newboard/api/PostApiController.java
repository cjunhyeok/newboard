package boardex.newboard.api;

import boardex.newboard.domain.Comment;
import boardex.newboard.domain.Post;
import boardex.newboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/posts")
    public Result posts(@RequestParam(defaultValue = "no") String cond,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "1") Long page) {

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

    @PutMapping("/api/posts/{id}")
    public UpdatePostResponse updatePost(@PathVariable(name = "id") Long id,
                                         @RequestBody @Valid UpdatePostRequest request) {

        postService.updatePost(id, request.getTitle(), request.getContent());
        Post updatePost = postService.findById(id);
        return new UpdatePostResponse(updatePost.getId());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Result<T> {
        private T data;
    }

    // posts Dto
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class PostsResponse {
        private Long id;
        private String title;
        private String nickName;
        private LocalDateTime lastModifiedTime;
    }

    // Post Dto
    @Data
    @NoArgsConstructor
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
    @NoArgsConstructor
    static class CommentDto {
        private Long id;
        private String content;
        private String nickName;
        private Long memberId;

        public CommentDto(Comment comment) {
            id = comment.getId();
            content = comment.getContent();
            nickName = comment.getMember().getNickName();
            memberId = comment.getMember().getId();
        }
    }

    // updatePostDto
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdatePostRequest {
        @NotEmpty
        private String title;
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdatePostResponse {
        private Long id;
    }
}
