package com.miraiyashi.repository;

import com.miraiyashi.entity.UserRewardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRewardHistoryRepository extends JpaRepository<UserRewardHistory,Integer> {
}
