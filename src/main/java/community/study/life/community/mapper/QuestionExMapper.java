package community.study.life.community.mapper;

import community.study.life.community.model.Question;

import java.util.List;

public interface QuestionExMapper {
    int incView(Question record);
    int incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}