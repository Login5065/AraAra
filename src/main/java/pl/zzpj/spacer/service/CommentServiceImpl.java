package pl.zzpj.spacer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.repositories.CommentRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CommentServiceImpl {

    private final CommentRepository commentRepository;

    public void addComment(Comment comment) throws AppBaseException {
        if (true) { //TODO: Implement checks
            commentRepository.save(comment);
        } else {
            throw AccountException.accountExistsException();
        }
    }

    public Comment getComment(UUID uuid) throws AccountException {
        return commentRepository.findByUUID(uuid).orElseThrow(AccountException::noSuchAccountException);
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public void editComment(UUID uuid, Comment comment) throws AppBaseException {
        Optional<Comment> queryComment = commentRepository.findByUUID(uuid);
        if (queryComment.isPresent()) {
            Comment temp = queryComment.get();
            temp.setContent(comment.getContent());
            temp.setOwner(comment.getOwner());
            temp.setParentPost(comment.getParentPost());
            temp.setDate(new Date(System.currentTimeMillis()));
            commentRepository.save(temp);
        } else {
            throw AccountException.noSuchAccountException();
        }
    }


}
