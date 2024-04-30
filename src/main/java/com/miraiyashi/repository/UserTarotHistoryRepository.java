package com.miraiyashi.repository;

import com.miraiyashi.entity.UserLuckyHistory;
import com.miraiyashi.entity.UserTarotHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTarotHistoryRepository extends JpaRepository<UserTarotHistory,Integer> {

}
