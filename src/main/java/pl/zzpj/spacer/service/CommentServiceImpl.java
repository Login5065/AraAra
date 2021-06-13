package pl.zzpj.spacer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Account;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.repositories.AccountRepository;
import pl.zzpj.spacer.repositories.CommentRepository;
import pl.zzpj.spacer.repositories.PictureRepository;
import pl.zzpj.spacer.service.interfaces.CommentService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final AccountRepository accountRepository;

    private final PictureRepository pictureRepository;

    public void addComment(Comment comment) throws AppBaseException {
        boolean var = true;

        Account account = accountRepository.findByUsername(comment.getOwner()).orElseThrow();
        Picture picture = pictureRepository.findById(comment.getPictureId()).orElseThrow();

        if (!account.getUsername().equals(comment.getOwner()) || !picture.getId().toString().equals(comment.getPictureId())) var = false;

        if (var) { //TODO: Implement checks
            commentRepository.save(comment);
        } else {
            throw AccountException.accountExistsException();
        }
    }

    public Comment getComment(UUID uuid) throws AccountException {
        return commentRepository.findById(uuid).orElseThrow(AccountException::noSuchAccountException);
    }

    public List<Comment> getCommentsByPictureId(String id) {
        return commentRepository.findAllByPictureId(id).orElseThrow();
    }

    public List<Comment> getCommentsByUsername(String username) {
        return commentRepository.findAllByOwner(username).orElseThrow();
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public void editComment(Comment comment) throws AppBaseException {
        Optional<Comment> queryComment = commentRepository.findById(comment.getId());
        if (queryComment.isPresent()) {
            Comment temp = queryComment.get();
            temp.setContent(comment.getContent());
            temp.setOwner(comment.getOwner());
            temp.setPictureId(comment.getPictureId());
            temp.setDate(LocalDateTime.now());
            commentRepository.save(temp);
        } else {
            throw AccountException.noSuchAccountException();
        }
    }


}
