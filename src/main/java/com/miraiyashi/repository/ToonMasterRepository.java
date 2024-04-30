package com.miraiyashi.repository;

import com.miraiyashi.entity.ToonMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToonMasterRepository extends JpaRepository<ToonMaster,Integer> {
}
