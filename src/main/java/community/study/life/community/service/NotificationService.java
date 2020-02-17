package community.study.life.community.service;

import community.study.life.community.dto.NotificationDTO;
import community.study.life.community.dto.PaginationDTO;
import community.study.life.community.enums.NotificationStatusEnum;
import community.study.life.community.enums.NotificationTypeEnum;
import community.study.life.community.exception.CustomizeErrorCode;
import community.study.life.community.exception.CustomizeException;
import community.study.life.community.mapper.NotificationMapper;
import community.study.life.community.model.Notification;
import community.study.life.community.model.NotificationExample;
import community.study.life.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDTO list(Long id, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
//        Integer totalCount = questionMapper.countByUserId(userId);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }
        else {
            totalPage = totalCount / size + 1;
        }
        if(page<1){
            page=1;
        }

        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        //size *(page -1)
        Integer offset = size*(page-1);
//        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        NotificationExample example = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification:
            notifications ) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }



        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public int unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId)
        .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
