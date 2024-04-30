package com.miraiyashi.repository;

import com.miraiyashi.entity.Landing;
import com.miraiyashi.entity.UserLuckyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserLuckyHistoryRepository extends JpaRepository<UserLuckyHistory,Integer> {
//    Optional<UserLuckyHistory> findByIdx(Integer idx, Date regDt);
//    Optional<UserLuckyHistory> findBy
}
