package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    //根据Id查找文章
    @Query("select u from Activity u where u.id = :id")
    Optional<Activity> findById(@Param("id") Long id);

    //根据内容和标题关键词查找文章：用于文章列表页面的搜索功能

    //根据Id更新文章

    @Transactional
    @Modifying
    @Query(value = "update Activity set title=?1,author=?2,content=?3,imageUrl=?4  where id=?5 ")
    int updateActivity(String title, String author, String content, String imageUrl, Long id);

    //新增文章使用默认方法：save()
    //删除文章使用默认方法：deleteById()
    //查找文章列表使用默认方法：findAll()
}
