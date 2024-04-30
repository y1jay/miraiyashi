package com.miraiyashi.repository;

import com.miraiyashi.entity.Luck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LuckRepository extends JpaRepository<Luck,Integer> {
}
