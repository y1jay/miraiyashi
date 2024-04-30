package com.miraiyashi.repository;

import com.miraiyashi.entity.UserPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentHistoryRepository extends JpaRepository<UserPaymentHistory,Integer> {
}
