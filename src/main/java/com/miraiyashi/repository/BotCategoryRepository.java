package com.miraiyashi.repository;

import com.miraiyashi.entity.BotCategory;
import com.miraiyashi.entity.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotCategoryRepository extends JpaRepository<BotCategory,Integer> {
}
