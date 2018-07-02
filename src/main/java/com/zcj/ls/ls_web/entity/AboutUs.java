package com.zcj.ls.ls_web.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Entity
public class AboutUs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //创建时间
    @CreatedDate
    private Long createTime;

    //更新时间
    @LastModifiedDate
    private Long updateTime;

    //作者
    private String author;

    //浏览次数
    private int readNum;

    //正文
    @Lob
    @Column(columnDefinition="LONGTEXT")
    private String content;

    //StringConfig中的aboutUs部分
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
