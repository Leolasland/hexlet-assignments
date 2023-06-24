package exercise.controller;

import exercise.dto.CommentDto;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    // BEGIN

    @GetMapping(path = "/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable Long postId) {
        Iterable<Comment> comments = commentRepository.findCommentsByPostId(postId);
        List<CommentDto> result = new ArrayList<>();
        for (Comment comment : comments) {
            result.add(new CommentDto(comment.getContent()));
        }
        return result;
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
    public CommentDto getCommentByIdAndPostId(@PathVariable Long postId,
                                              @PathVariable Long commentId) {
        Optional<Comment> comment = commentRepository.findCommentByIdAndPostId(commentId, postId);
        if (comment.isEmpty()) {
            throw new ResourceNotFoundException("Comment not found");
        }
        return new CommentDto(comment.get().getContent());
    }

    @PostMapping(path = "/{postId}/comments")
    public void createComment(@PathVariable Long postId,
                              @RequestBody CommentDto commentDto) {
        Optional<Post> byId = postRepository.findById(postId);
        Comment comment = new Comment();
        comment.setContent(commentDto.content());
        comment.setPost(byId.get());
        commentRepository.save(comment);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public void updateCommentByIdAndPostId(@PathVariable Long postId,
                                           @PathVariable Long commentId,
                                           @RequestBody CommentDto commentDto) {
        Optional<Comment> comment = commentRepository.findCommentByIdAndPostId(commentId, postId);
        if (comment.isEmpty()) {
            throw new ResourceNotFoundException("Comment not found");
        }
        comment.get().setContent(commentDto.content());
        commentRepository.save(comment.get());
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void deleteCommentByIdAndPostId(@PathVariable Long postId,
                                           @PathVariable Long commentId) {
        Optional<Comment> comment = commentRepository.findCommentByIdAndPostId(commentId, postId);
        if (comment.isEmpty()) {
            throw new ResourceNotFoundException("Comment not found");
        }
        commentRepository.delete(comment.get());
    }
    // END
}
