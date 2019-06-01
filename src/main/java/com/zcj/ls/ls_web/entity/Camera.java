package com.zcj.ls.ls_web.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //创建时间
    @CreatedDate
    private Date createTime;

    //更新时间
    @LastModifiedDate
    private Date updateTime;

    //创建人
    private String createUser;

    //更新人
    private String updateUser;

    //服务点名称
    private String placeName;

    //服务点等级
    @Column(nullable = false)
    private String placeLevel;

    //联系人姓名
    private String contectsName;

    //联系人电话
    private String contectsPhone;

    //摄像头编号
    private String cameraNum;

    //摄像头管理页面账号
    private String cameraAccount;

    //摄像头管理页面密码
    private String cameraPassword;

    //摄像头序列号
    private String cameraSerialNum;

    //摄像头验证码
    private String cameraValidateCode;

    //逻辑删除标记
    private int delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceLevel() {
        return placeLevel;
    }

    public void setPlaceLevel(String placeLevel) {
        this.placeLevel = placeLevel;
    }

    public String getContectsName() {
        return contectsName;
    }

    public void setContectsName(String contectsName) {
        this.contectsName = contectsName;
    }

    public String getContectsPhone() {
        return contectsPhone;
    }

    public void setContectsPhone(String contectsPhone) {
        this.contectsPhone = contectsPhone;
    }

    public String getCameraNum() {
        return cameraNum;
    }

    public void setCameraNum(String cameraNum) {
        this.cameraNum = cameraNum;
    }

    public String getCameraAccount() {
        return cameraAccount;
    }

    public void setCameraAccount(String cameraAccount) {
        this.cameraAccount = cameraAccount;
    }

    public String getCameraPassword() {
        return cameraPassword;
    }

    public void setCameraPassword(String cameraPassword) {
        this.cameraPassword = cameraPassword;
    }

    public String getCameraSerialNum() {
        return cameraSerialNum;
    }

    public void setCameraSerialNum(String cameraSerialNum) {
        this.cameraSerialNum = cameraSerialNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCameraValidateCode() {
        return cameraValidateCode;
    }

    public void setCameraValidateCode(String cameraValidateCode) {
        this.cameraValidateCode = cameraValidateCode;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
