package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    //根据Id查找文章
    @Query("select u from News u where u.id = :id")
    Optional<News> findById(@Param("id") Long id);

    //根据内容和标题关键词查找文章：用于文章列表页面的搜索功能

    //根据Id更新文章

    @Transactional
    @Modifying
    @Query(value = "update News set title=?1,author=?2,content=?3  where id=?4 ")
    int updateNews(String title, String author, String content, Long id);

    //新增文章使用默认方法：save()
    //删除文章使用默认方法：deleteById()
    //查找文章列表使用默认方法：findAll()
}
