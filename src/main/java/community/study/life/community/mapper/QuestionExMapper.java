package community.study.life.community.mapper;

import community.study.life.community.model.Question;

public interface QuestionExMapper {
    int incView(Question record);
    int incCommentCount(Question question);
}