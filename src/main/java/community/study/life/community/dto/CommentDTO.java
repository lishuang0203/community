package community.study.life.community.dto;

import community.study.life.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentor;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private Integer commentCount;

    private User user;
}
