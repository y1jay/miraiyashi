package com.miraiyashi.service;

import com.miraiyashi.entity.Cookie;
import com.miraiyashi.entity.QToonComponent;
import com.miraiyashi.entity.ToonComponent;
import com.miraiyashi.entity.ToonMaster;
import com.miraiyashi.libs.util;
import com.miraiyashi.repository.ToonComponentCustom;
import com.miraiyashi.repository.ToonComponentRepository;
import com.miraiyashi.repository.ToonMasterRepository;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.ListReponse;
import com.miraiyashi.response.SingleReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Index;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToonService {
    @Autowired
    ToonMasterRepository toonMaster;
    @Autowired
    ToonComponentRepository toonComponent;

    @Autowired
    ToonComponentCustom toonComponentCustom;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired(required = false)
    QToonComponent tc = QToonComponent.toonComponent;

    @Autowired(required = false)
    CommonResponse res ;

    // 웹툰 리스트
    @Transactional(readOnly = true)
    public ListReponse<ToonMaster> masterList(Integer page){
        ListReponse<ToonMaster> res = new ListReponse<>();
        PageRequest pageRequest =
                PageRequest
                        .of(page, 5,
                                Sort.by("tIdx")
                                        .descending()
                        );
        List<ToonMaster> toonM = toonMaster.findAll(pageRequest).toList();
        if(toonM.isEmpty()){
            res.setCode(404);
            res.setMessage("데이터가 없습니다.");
            return res;
        }
       res.setData(toonM);
        return res;
    }

    // 웹툰 내용 리스트 (1화 2화 ~~)
    @Transactional(readOnly = true)
    public ListReponse<ToonComponent> component(Integer tIdx,Integer page){

        ListReponse<ToonComponent> res = new ListReponse<>();
        List<ToonComponent> toonList =
//                toonComponentCustom.list(tIdx,page);
                queryFactory
                        .selectFrom(tc)
                        .where(tc.tIdx.eq(tIdx))
                        .orderBy(tc.tcIdx.desc())
                        .offset(page)
                        .limit(10)
                        .fetch();
        if(toonList.isEmpty()){
           res.setCode(404);
           res.setMessage("데이터가 없습니다.");
        }else{
            res.setData(toonList);
        }
        return res;
    }


    // 웹툰 좋아요
    public CommonResponse masterHeart(Integer tIdx){
        Optional<ToonMaster> toon = toonMaster.findById(tIdx);

        if(toon.isPresent()){
            ToonMaster tm = ToonMaster.builder().toonMasterHeart(toon.get().getToonMasterHeart()+1).build();
             this.toonMaster.save(tm);
            res.setCode(200);
            res.setMessage("좋아요 완료.");
             return res;
        }else {
            res.setCode(404);
            res.setMessage("웹툰 정보가 없습니다.");
            return res;
        }
    }

    // 웹툰 회차별 좋아요
    public CommonResponse componentHeart(Integer tcIdx){
        Optional<ToonComponent> toon = toonComponent.findById(tcIdx);
        if(toon.isPresent()){
            ToonComponent tc = ToonComponent.builder().toonComponentHeart(toon.get().getToonComponentHeart()+1).build();
             this.toonComponent.save(tc);
            res.setCode(200);
            res.setMessage("좋아요 완료.");
            return res;
        }else {
            res.setCode(404);
            res.setMessage("웹툰 정보가 없습니다.");
            return res;
        }
    }



}
