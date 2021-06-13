package pl.zzpj.spacer.service.interfaces;

import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    public void addComment(Comment comment) throws AppBaseException;

    public Comment getComment(UUID uuid) throws AccountException;

    public List<Comment> getCommentsByPictureId(String id);

    public List<Comment> getCommentsByUsername(String username);

    public List<Comment> getAll();

    public void editComment(Comment comment) throws AppBaseException;
}
