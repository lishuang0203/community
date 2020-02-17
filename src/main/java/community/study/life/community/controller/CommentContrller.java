package community.study.life.community.controller;

import community.study.life.community.dto.CommentCreateDTO;
import community.study.life.community.dto.CommentDTO;
import community.study.life.community.dto.ResultDTO;
import community.study.life.community.enums.CommentTypeEnum;
import community.study.life.community.exception.CustomizeErrorCode;
import community.study.life.community.model.Comment;
import community.study.life.community.model.User;
import community.study.life.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SuppressWarnings("unchecked")
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
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
