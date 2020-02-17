package community.study.life.community.dto;

import community.study.life.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;

    private Long gmtCreate;

    private Integer status;

    private Long notifier;

    private Long outerId;

    private String notifierName;

    private String  outerTitle;

    private String typeName;
    
    private Integer type;

    private String title;

    private User user;

}
