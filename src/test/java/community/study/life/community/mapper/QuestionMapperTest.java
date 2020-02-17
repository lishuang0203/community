package community.study.life.community.mapper;

import community.study.life.community.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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