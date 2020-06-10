package community.study.life.community.service;

import community.study.life.community.dto.CommentDTO;
import community.study.life.community.enums.CommentTypeEnum;
import community.study.life.community.enums.NotificationStatusEnum;
import community.study.life.community.enums.NotificationTypeEnum;
import community.study.life.community.exception.CustomizeErrorCode;
import community.study.life.community.exception.CustomizeException;
import community.study.life.community.mapper.*;
import community.study.life.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExMapper questionExMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private  NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentor) {
        /**
         * ②登录成功增加校验 通过throw exception
         */
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());

            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //回复问题
            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
           commentExtMapper.incCommentCount(parentComment);
           //创建通知
            createNotify(comment, dbComment.getCommentor(), commentor.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, comment.getParentId());

        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());

            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //回复问题
            //④问题评论成功并且评论数+1
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExMapper.incCommentCount(question);
            //创建通知
            createNotify(comment,question.getCreator(),  commentor.getName(), question.getTitle(),NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationTypeEnum, Long outerId) {
        if (receiver == comment.getCommentor()){
            return;
        }
        Notification notification = new Notification();
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentor());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    /**
     * 查询
     *
     * @param id
     * @param type
     * @return
     */

    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");//按时间倒序排序
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);


        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
