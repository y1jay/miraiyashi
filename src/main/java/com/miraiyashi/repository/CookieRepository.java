package com.miraiyashi.repository;

import com.miraiyashi.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookieRepository extends JpaRepository<Cookie,Integer> {
//    Cookie getByIdx(Integer idx);
//    @Query(value =  "SELECT result, SUM(percent) OVER (ORDER BY rand()) as percent " +
//            "FROM Cookie "
//            )
//   List<CookieVo> result();
}
