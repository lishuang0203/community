package community.study.life.community.controller;

import community.study.life.community.dto.CommentCreatDTO;
import community.study.life.community.dto.ResultDTO;
import community.study.life.community.exception.CustomizeErrorCode;
import community.study.life.community.model.Comment;
import community.study.life.community.model.User;
import community.study.life.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller

public class CommentContrller {
    @Autowired
    private CommentService commentService;

    /**
     * ①评论增加验证，返回没有登录
     *登录返回封装的service
     * @param commentDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method  = RequestMethod.POST)
    public Object post(@RequestBody CommentCreatDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
