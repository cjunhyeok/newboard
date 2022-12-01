package boardex.newboard.service;

import boardex.newboard.domain.Post;
import boardex.newboard.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public Long savePost(Post post) {
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
