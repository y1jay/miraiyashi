package com.miraiyashi.repository;

import com.miraiyashi.entity.Landing;
import com.miraiyashi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LandingRepository extends JpaRepository<Landing,Integer> {
    Optional<Landing> findByIdx(Integer idx);
}
