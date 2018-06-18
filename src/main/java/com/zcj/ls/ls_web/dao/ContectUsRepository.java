package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.ContectUs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContectUsRepository extends JpaRepository<ContectUs,Long> {

    @Override
    ContectUs getOne(Long aLong);

//    @Query("from ContectUs u where u.title = :title")
//    ContectUs findContectUs(@Param("title") String title);

}
