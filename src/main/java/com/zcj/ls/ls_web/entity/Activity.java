package com.zcj.ls.ls_web.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

/**
 * 活动中心
 */
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //创建时间
    @CreatedDate
    private Long createTime;

    //更新时间
    @LastModifiedDate
    private Long updateTime;

    //标题
    private String title;

    //作者
    private String author;

    //浏览次数
    private int readNum;

    //文章图片
    private String imageUrl;

    //正文
    @Lob
    @Column(columnDefinition="LONGTEXT")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
