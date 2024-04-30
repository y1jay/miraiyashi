package com.miraiyashi.repository;

import com.miraiyashi.entity.QToonComponent;
import com.miraiyashi.entity.QToonMaster;
import com.miraiyashi.entity.ToonComponent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ToonComponentSp implements ToonComponentCustom {
    private final JPAQueryFactory query;
    private  final QToonComponent tc = QToonComponent.toonComponent;

    @Override
    public List<ToonComponent> list(Integer tIdx,Integer page){
        return
                query
                        .selectFrom(tc)
                        .where(tc.tIdx.eq(tIdx))
                        .orderBy(tc.tcIdx.desc())
                        .offset(page)
                        .limit(10)
                        .fetch();
    }
}
