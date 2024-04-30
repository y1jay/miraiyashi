package com.miraiyashi.service;

import com.miraiyashi.dto.LuckyDto;
import com.miraiyashi.entity.QUserLuckyHistory;
import com.miraiyashi.entity.UserLuckyHistory;
import com.miraiyashi.libs.util;
import com.miraiyashi.repository.CookieRepository;
import com.miraiyashi.repository.LuckRepository;
import com.miraiyashi.repository.UserLuckyHistoryRepository;
import com.miraiyashi.response.CustomException;
import com.miraiyashi.response.ErrorCode;
import com.miraiyashi.response.SingleReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LuckyService {

    @Autowired
    CookieRepository cookie;

    @Autowired
    LuckRepository luck;

    @Autowired
    UserLuckyHistoryRepository history;

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired(required = false)
    QUserLuckyHistory qlh =  QUserLuckyHistory.userLuckyHistory;
    // 포춘쿠키
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public SingleReponse<String> CookiePlay(String idx, HttpServletRequest request){

        SingleReponse<String> res = new SingleReponse<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        System.out.println(cal.getTime()+"!@#!@#@!###@!#@!");
            int cnt = queryFactory
                    .selectFrom(qlh)
                    .where(qlh.idx.eq(Integer.parseInt(idx))
                            .and(qlh.regDt.after(cal.getTime()))
                            .and(qlh.type.eq("0"))
                    )
                    .fetch().size();
            System.out.println(cnt + "!@#!@#@!#!@#!");

            if (cnt > 0) {
                throw new CustomException(ErrorCode.NOT_HAVE_COUNT);
            }
            TypedQuery<LuckyDto> query = em.createQuery(
                    "SELECT result, SUM(percent) OVER (ORDER BY rand()) as percent " +
                    "FROM Cookie ", LuckyDto.class);
            List<LuckyDto> re = query.getResultList();


            String result = "";
            for (LuckyDto cookieResult : re) {
                System.out.println("!@#@!#@!#@@!#@!");
                if (cookieResult.getPercent() >= Math.random() * 100) {
                    result = cookieResult.getResult();
                }

            }
            System.out.println(result + "!@#@!#!@#!@#");

            if (result.isEmpty()) {

                throw new CustomException(ErrorCode.NOT_FOUND);
            }
            String ip = util.getIp(request);
            UserLuckyHistory ulh = UserLuckyHistory.builder()
                    .idx(Integer.parseInt(idx))
                    .type("0")
                    .regDt(new Date())
                    .result(result)
                    .regIp(ip)
                    .build();
            UserLuckyHistory ulhSave = history.save(ulh);
            if (!ulh.equals(ulhSave)) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            } else {
                res.setCode(200);
                res.setData(result);
            }


        return res;
    }

    // 행운카드
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public SingleReponse<String> LuckCardPlay(String idx,HttpServletRequest request){

        SingleReponse<String> res = new SingleReponse<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        System.out.println(cal.getTime()+"!@#!@#@!###@!#@!");
        int cnt = queryFactory
                .selectFrom(qlh)
                .where(qlh.idx.eq(Integer.parseInt(idx))
                        .and(qlh.regDt.after(cal.getTime()))
                        .and(qlh.type.eq("1"))
                )
                .fetch().size();
        System.out.println(cnt + "!@#!@#@!#!@#!");

        if (cnt > 0) {
            throw new CustomException(ErrorCode.NOT_HAVE_COUNT);
        }
        TypedQuery<LuckyDto> query = em.createQuery(
                "SELECT result, SUM(percent) OVER (ORDER BY rand()) as percent " +
                        "FROM Lucky ", LuckyDto.class);
        List<LuckyDto> re = query.getResultList();


        String result = "";
        for (LuckyDto luckyResult : re) {
            System.out.println("!@#@!#@!#@@!#@!");
            if (luckyResult.getPercent() >= Math.random() * 100) {
                result = luckyResult.getResult();
            }

        }
        System.out.println(result + "!@#@!#!@#!@#");

        if (result.isEmpty()) {

            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        String ip = util.getIp(request);
        UserLuckyHistory ulh = UserLuckyHistory.builder()
                .idx(Integer.parseInt(idx))
                .type("1")
                .regDt(new Date())
                .result(result)
                .regIp(ip)
                .build();
        UserLuckyHistory ulhSave = history.save(ulh);
        if (!ulh.equals(ulhSave)) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } else {
            res.setCode(200);
            res.setData(result);
        }

        return res;
    }
}
