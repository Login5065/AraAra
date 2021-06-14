package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
     void addComment(Comment comment) throws AppBaseException;

     Comment getComment(UUID uuid) throws AccountException;

     List<Comment> getCommentsByPictureId(String id);

     List<Comment> getCommentsByUsername(String username);

     List<Comment> getAll();

     void editComment(Comment comment) throws AppBaseException;
}
