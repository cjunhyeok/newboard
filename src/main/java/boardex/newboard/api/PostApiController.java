package boardex.newboard.api;

import boardex.newboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Result posts(@RequestParam String cond,
                        @RequestParam String keyword,
                        @RequestParam Long page) {

        List<PostsRequest> collect = postService.findAllFetchDynamic(cond, keyword, page).stream()
                .map(p -> new PostsRequest(p.getMember().getNickName(), p.getTitle(), page))
                .collect(Collectors.toList());

        return new Result(collect);
    }

//    @GetMapping("/api/posts/{id}")
//    public void post(@PathVariable(name = "id") Long id) {
//        Post findPosts = postService.findById(id);
//        new PostsResponse(findPosts.getTitle(), findPosts.getMember().getNickName(), findPosts.getLastModifiedDate())
//    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    // posts Dto
    @Data
    @AllArgsConstructor
    static class PostsResponse {
        private String title;
        private String nickName;
        private LocalDateTime lastModifiedTime;
    }

    @Data
    @AllArgsConstructor
    static class PostsRequest {
        private String nickName;
        private String title;
        private Long page;
    }

    // Post Dto
    @Data
    @AllArgsConstructor
    static class PostResponse {
        // post
        private String title;
        private String content;
        private LocalDateTime lastModifiedTime;

        // member
        private String nickName;

        // comment
        private String commentNickName;
        private String commentContent;

    }
}
