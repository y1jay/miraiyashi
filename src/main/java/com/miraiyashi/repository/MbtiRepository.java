package com.miraiyashi.repository;

import com.miraiyashi.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MbtiRepository extends JpaRepository<Mbti,Integer> {
    Optional<Mbti> findByIdx(Integer idx);
}
