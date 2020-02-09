package community.study.life.community.mapper;

import community.study.life.community.model.Question;
import community.study.life.community.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperTest {
    @Autowired
    private QuestionMapper questionMapper;
    @Test
    void create() {
            Question question = new Question();
            question.setTitle("测试1" );
            question.setDescription("内容1");
            question.setTag("标签1");
            question.setCreator(1L);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
    }

}