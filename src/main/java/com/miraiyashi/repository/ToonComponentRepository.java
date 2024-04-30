package com.miraiyashi.repository;

import com.miraiyashi.entity.ToonComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToonComponentRepository extends JpaRepository<ToonComponent,Integer> {


}
