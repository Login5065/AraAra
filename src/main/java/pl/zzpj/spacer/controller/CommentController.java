package pl.zzpj.spacer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.dto.mapper.CommentMapper;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.service.CommentServiceImpl;
import pl.zzpj.spacer.service.interfaces.CommentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @PostMapping("comment")
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) {
        try {
            commentService.addComment(commentMapper.commentDtoToComment(commentDto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("comment/{id}")
    public ResponseEntity getComment(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(commentService.getComment(UUID.fromString(id)));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("comments/picture/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByPictureId(@PathVariable("id") String pictureId) {
        List<Comment> comments = commentService.getCommentsByPictureId(pictureId);
        List<CommentDto> commentsDto = comments.stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @GetMapping("comments/username/{username}")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(@PathVariable("username") String username) {
        List<Comment> comments = commentService.getCommentsByUsername(username);
        List<CommentDto> commentsDto = comments.stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @GetMapping("comments")
    public ResponseEntity<List<CommentDto>> getCommentsAll() {
        List<Comment> comments = commentService.getAll();
        List<CommentDto> commentsDto = comments.stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @PutMapping("comment/edit")
    public ResponseEntity editComment(@RequestBody CommentDto comment) {
        try {
            List<Comment> comments = commentService.getCommentsByUsername(comment.getOwner())
                    .stream().filter((Comment candidate) -> (
                            candidate.getPictureId().equals(comment.getPictureId()) &&
                                    candidate.getOwner().equals(comment.getOwner()) &&
                                    candidate.getDate().toString().equals(comment.getDate())))
                    .collect(Collectors.toList());

            if (comments.size() > 1)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Found more than one comment. Unexpected");
            else if (comments.size() == 0)
                throw new AccountException("No comment found");

            Comment editable = comments.get(0);

            editable.setContent(comment.getContent());
            commentService.editComment(editable);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
