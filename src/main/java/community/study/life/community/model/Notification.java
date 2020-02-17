package community.study.life.community.model;

public class Notification {
    private Long id;

    private Long notifier;

    private Long receiver;

    private Long outerId;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

    private String outerTitle;

    private String notifierName;

    public Notification(Long id, Long notifier, Long receiver, Long outerId, Integer type, Long gmtCreate, Integer status, String outerTitle, String notifierName) {
        this.id = id;
        this.notifier = notifier;
        this.receiver = receiver;
        this.outerId = outerId;
        this.type = type;
        this.gmtCreate = gmtCreate;
        this.status = status;
        this.outerTitle = outerTitle;
        this.notifierName = notifierName;
    }

    public Notification() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotifier() {
        return notifier;
    }

    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Long getOuterId() {
        return outerId;
    }

    public void setOuterId(Long outerId) {
        this.outerId = outerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName == null ? null : notifierName.trim();
    }
}