package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //根据Id查找文章
    @Query("select u from User u where u.userName = :userName")
    Optional<User> findByUserName(@Param("userName") String userName);

    //根据内容和标题关键词查找文章：用于文章列表页面的搜索功能

    //根据Id更新文章

    @Transactional
    @Modifying
    @Query(value = "update User set password=?1  where userName=?2 ")
    int updateUser(String password, String userName);

    //新增文章使用默认方法：save()
    //删除文章使用默认方法：deleteById()
    //查找文章列表使用默认方法：findAll()
}
