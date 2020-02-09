package community.study.life.community.model;

public class Question {
    private Long id;

    private String title;

    private String description;

    private String tag;

    private Long gmtCreate;

    private Long gmtModified;

    private Long creator;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    public Question(Long id, String title, String description, String tag, Long gmtCreate, Long gmtModified, Long creator, Integer viewCount, Integer commentCount, Integer likeCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    public Question() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}