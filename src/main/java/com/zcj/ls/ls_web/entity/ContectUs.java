package com.zcj.ls.ls_web.entity;

import javax.persistence.*;

/**
 * 联系我们页面
 * 标题：联系我们(固定不变)
 * 副标题：
 *  发布人：南京恒宇社会工作服务中心
 *  发布日期：最后修改日期
 *  浏览次数：200
 * 正文：
 *  机构名称：南京恒宇社会工作服务中心
 *  地址：南京市溧水区财贸北村10号溧水老年大学2楼
 *  联系电话：025-56601234
 *  投诉电话：18061278111
 */
@Entity
public class ContectUs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String updateTime;

    private String readNum;

    private String companyName;

    private String companyAddress;

    private String companyPhone;

    private String peoplePhone;

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
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getPeoplePhone() {
        return peoplePhone;
    }

    public void setPeoplePhone(String peoplePhone) {
        this.peoplePhone = peoplePhone;
    }
}
