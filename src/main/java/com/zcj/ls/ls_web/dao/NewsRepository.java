package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    //根据Id查找文章
    @Query("select u from News u where u.id = :id")
    Optional<News> findById(@Param("id") Long id);

    //查找置顶文章
    @Query("select u from News u where u.isTop = 1")
    Optional<News> findByTop();

//    //查找已发布、未删除文章
//    @Query("select u from News u where u.isPublish = 1 and u.delFlag = 0")
//    List<News> findByIsPublishAndDelFlag(Pageable pageable);

    //根据内容和标题关键词查找文章：用于文章列表页面的搜索功能

    //更新文章标题、作者、正文和大图
    @Transactional
    @Modifying
    @Query(value = "update News set title=?1,author=?2,content=?3,imageUrl=?4,updateTime=?5,isPublish=?6  where id=?7")
    int updateNews(String title, String author, String content, String imageUrl,
                   Date updateTime,int isPublish, Long id);

    //更新文章阅读次数
    @Transactional
    @Modifying
    @Query(value = "update News set readNum=?1 where id=?2 ")
    int updateReadNum(int readNum, Long id);

    //更新文章置顶设置
    @Transactional
    @Modifying
    @Query(value = "update News set isTop=?1 where id=?2 ")
    int updateIsTop(int isTop, Long id);

    //更新文章发布状态
    @Transactional
    @Modifying
    @Query(value = "update News set isPublish=?1 where id=?2 ")
    int updateIsPublish(int isPublish, Long id);

    //更新文章逻辑删除字段
    @Transactional
    @Modifying
    @Query(value = "update News set delFlag=?1 where id=?2 ")
    int updateDelFlag(int delFlag, Long id);

    //查找所有已发布的文章默认方法：findAll(Example example)
    //新增文章使用默认方法：save()
    //删除文章使用默认方法：deleteById()
    //查找文章列表使用默认方法：findAll()
}
