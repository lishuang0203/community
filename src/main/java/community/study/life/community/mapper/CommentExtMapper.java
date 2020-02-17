package community.study.life.community.mapper;

import community.study.life.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}