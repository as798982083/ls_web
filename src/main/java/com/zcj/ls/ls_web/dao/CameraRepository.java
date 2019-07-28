package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.Camera;
import com.zcj.ls.ls_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface CameraRepository extends JpaRepository<Camera, Long> {

    //根据placeName查找摄像头列表
    @Query("select u from Camera u where u.placeName = :placeName")
    Optional<Camera> finsdByPlaceName(@Param("placeName") String placeName);

    //根据id查找摄像头列表
    @Query("select u from Camera u where u.id = :id")
    Optional<Camera> findById(@Param("id") String id);

    //根据内容和标题关键词查找文章：用于文章列表页面的搜索功能

    //根据Id更新文章

    @Transactional
    @Modifying
    @Query(value = "update Camera set placeName=?2,placeLevel=?3,contectsName=?4,contectsPhone=?5,cameraSerialNum=?6," +
            "cameraValidateCode=?7,cameraAccount=?8,cameraPassword=?9,cameraNum=?10,updateTime=?11,liveAddress=?12 " +
            " where id=?1 ")
    int updateCamera(Long id, String placeName, String placeLevel, String contectsName, String contectsPhone, String cameraSerialNum,
                   String cameraValidateCode, String cameraAccount, String cameraPassword, String cameraNum, Date updateTime,
                   String liveAddress);

    //更新文章逻辑删除字段
    @Transactional
    @Modifying
    @Query(value = "update Camera set delFlag=?1 where id=?2 ")
    int updateDelFlag(int delFlag, Long id);

    //新增文章使用默认方法：save()
    //删除文章使用默认方法：deleteById()
    //查找文章列表使用默认方法：findAll()
}
