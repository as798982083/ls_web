package com.zcj.ls.ls_web.dao;

import com.zcj.ls.ls_web.entity.ContectUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContectUsRepository extends JpaRepository<ContectUs,Long> {

    @Query("select u from ContectUs u where u.title = :title")
    ContectUs findByTitle(@Param("title") String title);

    @Transactional
    @Modifying
    @Query(value = "update ContectUs set companyName=?1,companyAddress=?2, " +
            "companyPhone=?3,peoplePhone=?4 where title=?5 ")
    int updateContectUs(String companyName, String companyAddress,
                              String companyPhone, String peoplePhone,
                              String title);

}
