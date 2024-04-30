package com.miraiyashi.service;

import com.miraiyashi.dto.LuckyDto;
import com.miraiyashi.dto.TarotDto;
import com.miraiyashi.dto.TarotResultDto;
import com.miraiyashi.entity.*;
import com.miraiyashi.libs.util;
import com.miraiyashi.repository.*;
import com.miraiyashi.response.CustomException;
import com.miraiyashi.response.ErrorCode;
import com.miraiyashi.response.ListReponse;
import com.miraiyashi.response.SingleReponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TarotService {

    private static class five {
        int h = 0;
        int s = 0;
        int m = 0;
        int g = 0;
        int t = 0;
    }

    @Autowired
    UserTarotHistoryRepository history;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BotCategoryRepository botCategory;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired(required = false)
    QBotCategory qbc;

    @Autowired
    EntityManager em;

    // 봇 별 타로 카테고리 리스트
    public ListReponse<BotCategory> botCategory(int bIdx,int page){
        ListReponse<BotCategory> res = new ListReponse<>();
        List<BotCategory> data =
                queryFactory
                        .selectFrom(qbc)
                        .where(qbc.bIdx.eq(bIdx))
                        .orderBy(qbc.bcIdx.desc())
                        .offset(page)
                        .limit(10)
                        .fetch();
        if(data.isEmpty()){
            res.setCode(404);
            res.setMessage("데이터가 없습니다.");
        }else{
            res.setData(data);
        }
        return res;
    }


    // 봇 메세지
    public SingleReponse<List> botMsg(int idx,int bIdx,int bcIdx,int type){
        System.out.println(idx+"!@#!@#@!#!@");
        SingleReponse<List> res = new SingleReponse<>();
        Optional<User> user = userRepository.findById(idx);
        System.out.println(user+"!@#!@#@!#!@#");
        if(user.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        if(user.get().getMineral()==0){
            throw new CustomException(ErrorCode.NOT_HAVE_COUNT);
        }

        Query query = em.createQuery(
                "SELECT b.botName, b.botImage, bm.message, bc.mineral, bc.title FROM Bot as b "
                            +"JOIN BotMessage as bm ON (b.bIdx = bm.bIdx) "
                            +"JOIN BotCategory as bc ON (bm.bcIdx = bc.bcIdx) "
                            +"WHERE bm.bIdx =:bIdx "
                            +"AND bm.type =:type "
                            +"AND bm.bcIdx =:bcIdx ")
                .setParameter("bIdx",bIdx)
                .setParameter("type",type)
                .setParameter("bmIdx",bcIdx);
        if(query.getResultList().isEmpty()){
            res.setCode(404);
            res.setMessage("데이터가 없습니다.");
        }else{
//            String data = query.getResultList().toString();
            res.setData(query.getResultList());
        }

        return res;
    }

    // 타로 플레이
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public SingleReponse<TarotResultDto> tarot(HashMap<String,Object> params, HttpServletRequest request){
        System.out.println(params.get("idx")+"IDX!");
        if(params.get("idx").toString().isEmpty()
        ||params.get("bcIdx").toString().isEmpty()
        ||params.get("type").toString().isEmpty()
        ||params.get("mineral").toString().isEmpty()
        ){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        int idx = Integer.parseInt(params.get("idx").toString());
        int bcIdx = Integer.parseInt(params.get("bcIdx").toString());
        int type = Integer.parseInt(params.get("type").toString());
        int mineral = Integer.parseInt(params.get("mineral").toString());
        SingleReponse<TarotResultDto> res = new SingleReponse<>();
       Optional<User> user = userRepository.findById(idx);
        if(user.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        if(user.get().getMineral()==0){
            throw new CustomException(ErrorCode.NOT_HAVE_COUNT);
        }
        // mineral -
        String ip = util.getIp(request);
        int beforeMineral = user.get().getMineral();
        User afterUser = User.builder()
                .idx(idx)
                .deviceUuid(user.get().getDeviceUuid())
                .joinDt(user.get().getJoinDt())
                .joinType(user.get().getJoinType())
                .joinIp(user.get().getJoinIp())
                .platform(user.get().getPlatform())
                .userState(user.get().getUserState())
                .leaveDt(user.get().getLeaveDt())
                .leaveIp(user.get().getLeaveIp())
                .userId(user.get().getUserId())
                .mineral(beforeMineral-mineral)
                .build();
        userRepository.save(afterUser);
        System.out.println(beforeMineral+"BEFORE");
        System.out.println(user.get().getMineral()+"AFTER");
        if(beforeMineral==user.get().getMineral()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        // tarot play
        TypedQuery<TarotCard> card = em.createQuery(
                "SELECT tacIdx FROM TarotCard WHERE useYn = 1"
                        +"ORDER BY RAND() LIMIT 1",TarotCard.class);
        if(String.valueOf(card.getSingleResult()).isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_DATA);
        }
        // tarotcard
        int tacIdx = Integer.parseInt(String.valueOf(card.getSingleResult()));
        TypedQuery<TarotDto> msg = em.createQuery(
                "SELECT b.botImage, b.botName, tc.cardImage, tc.title, m.message FROM Message as m "
                    +"JOIN TarotCard as tc ON(m.tacIdx = tc.tacIdx) "
                    +"JOIN BotCategory as bc ON(m.bcIdx = bc.bcIdx) "
                    +"JOIN Bot as b ON(bc.bIdx = b.bIdx) "
                    +"WHERE bc.bcIdx =:bcIdx "
                    +"AND tc.tacIdx =:tacIdx "
                    +"AND m.type =:type "
                        +"ORDER BY m.orderNo ASC",TarotDto.class)
                .setParameter("bcIdx",bcIdx)
                .setParameter("tacIdx",tacIdx)
                .setParameter("type",type);
        List<TarotDto> resultList = msg.getResultList();
        if(resultList.isEmpty()){
            throw  new CustomException(ErrorCode.NOT_FOUND_DATA);
        }
        // tarotMessage
       ArrayList<String> msgResult = new ArrayList<String>();
       TarotResultDto trd = new TarotResultDto();

        for (TarotDto tarotResult : resultList) {
            trd.setTitle(tarotResult.getTitle());
            trd.setBotName(tarotResult.getBotName());
            trd.setCardImage(tarotResult.getCardImage());
            trd.setBotImage(tarotResult.getBotImage());
            msgResult.add(tarotResult.getMessage());
        }
        trd.setMessage(msgResult);

        // history
        UserTarotHistory uth = UserTarotHistory
                .builder()
                .idx(idx)
                .regDt(new Date())
                .regIp(ip)
                .tacIdx(tacIdx)
                .result(msgResult.toString())
                .build();
        history.save(uth);
        // result
        res.setData(trd);

        return res;
    }


    // 음양오행
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Optional<FiveMovementResult> fiveMovement(String year, String month, String day, String hour, Integer sl, String time,Integer idx,HttpServletRequest request){

        five five = new five();
        String cntQuery = "SELECT group_concat(kyganjee,kmganjee,kdganjee) as res FROM";
        cntQuery += "CalendaData cd";
        cntQuery += "WHERE cd.cdSy = :year";
        cntQuery += "AND cd.cdSm = :month";
        cntQuery += "AND cd.cdSd = :day";
        cntQuery += "limit "+1;
        TypedQuery<CalendaData> sganjee = em.createQuery(cntQuery,CalendaData.class);
        sganjee.setParameter("year",year);
        sganjee.setParameter("month",month);
        sganjee.setParameter("day",day);

        String tganjee ="";
        String resultQuery ="";
        if(sl ==0){

        String dGanjee = String.valueOf(sganjee.getSingleResult().toString().charAt(4));
        tganjee = switch (hour) {
            case "23", "24", "0" ->
                // 자
                    switch (dGanjee) {
                        case "갑", "기" -> "갑자";
                        case "을", "경" -> "병자";
                        case "병", "신" -> "무자";
                        case "정", "임" -> "경자";
                        case "무", "계" -> "임자";
                        default -> tganjee;
                    };
            case "1", "2" ->
                // 축
                    switch (dGanjee) {
                        case "갑", "기" -> "을축";
                        case "을", "경" -> "정축";
                        case "병", "신" -> "기축";
                        case "정", "임" -> "신축";
                        case "무", "계" -> "계축";
                        default -> tganjee;
                    };
            case "3", "4" ->
                // 인
                    switch (dGanjee) {
                        case "갑", "기" -> "병인";
                        case "을", "경" -> "무인";
                        case "병", "신" -> "경인";
                        case "정", "임" -> "임인";
                        case "무", "계" -> "갑인";
                        default -> tganjee;
                    };
            case "5", "6" ->
                // 묘
                    switch (dGanjee) {
                        case "갑", "기" -> "정묘";
                        case "을", "경" -> "기묘";
                        case "병", "신" -> "신묘";
                        case "정", "임" -> "계묘";
                        case "무", "계" -> "을묘";
                        default -> tganjee;
                    };
            case "7", "8" ->
                // 진
                    switch (dGanjee) {
                        case "갑", "기" -> "무진";
                        case "을", "경" -> "경진";
                        case "병", "신" -> "임진";
                        case "정", "임" -> "갑진";
                        case "무", "계" -> "병진";
                        default -> tganjee;
                    };
            case "9", "10" ->
                // 사
                    switch (dGanjee) {
                        case "갑", "기" -> "기사";
                        case "을", "경" -> "신사";
                        case "병", "신" -> "계사";
                        case "정", "임" -> "을사";
                        case "무", "계" -> "정사";
                        default -> tganjee;
                    };
            case "11", "12" ->
                // 오
                    switch (dGanjee) {
                        case "갑", "기" -> "경오";
                        case "을", "경" -> "임오";
                        case "병", "신" -> "갑오";
                        case "정", "임" -> "병오";
                        case "무", "계" -> "무오";
                        default -> tganjee;
                    };
            case "13", "14" ->
                // 미
                    switch (dGanjee) {
                        case "갑", "기" -> "신미";
                        case "을", "경" -> "계미";
                        case "병", "신" -> "을미";
                        case "정", "임" -> "정미";
                        case "무", "계" -> "거미";
                        default -> tganjee;
                    };
            case "15", "16" ->
                // 신
                    switch (dGanjee) {
                        case "갑", "기" -> "임신";
                        case "을", "경" -> "갑신";
                        case "병", "신" -> "병신";
                        case "정", "임" -> "무신";
                        case "무", "계" -> "경신";
                        default -> tganjee;
                    };
            case "17", "18" ->
                // 유
                    switch (dGanjee) {
                        case "갑", "기" -> "계유";
                        case "을", "경" -> "을유";
                        case "병", "신" -> "정유";
                        case "정", "임" -> "기유";
                        case "무", "계" -> "신유";
                        default -> tganjee;
                    };
            case "19", "20" ->
                // 술
                    switch (dGanjee) {
                        case "갑", "기" -> "갑술";
                        case "을", "경" -> "병술";
                        case "병", "신" -> "무술";
                        case "정", "임" -> "경술";
                        case "무", "계" -> "임술";
                        default -> tganjee;
                    };
            case "21", "22" ->
                // 해
                    switch (dGanjee) {
                        case "갑", "기" -> "을해";
                        case "을", "경" -> "정해";
                        case "병", "신" -> "기해";
                        case "정", "임" -> "신해";
                        case "무", "계" -> "계해";
                        default -> tganjee;
                    };
            default -> "없다";
        };
    String ss = sganjee.getSingleResult().toString().concat(tganjee);
    String[] ganjee = ss.split("");
            for (String s : ganjee) {
                switch (s) {
                    case "병":
                    case "정":
                    case "사":
                    case "오":
                        //화
                        five.h += 1;
                        break;

                    case "임":
                    case "계":
                    case "자":
                    case "해":
                        //수
                        five.s += 1;
                        break;

                    case "갑":
                    case "을":
                    case "인":
                    case "묘":
                        //목
                        five.m += 1;
                        break;

                    case "경":
                    case "신":
                    case "유":
                        //금
                        five.g += 1;
                        break;

                    case "무":
                    case "기":
                    case "축":
                    case "진":
                    case "미":
                    case "술":
                        // 토
                        five.t += 1;
                        break;
                }
            }

        }
        if(sl ==1){

            String dGanjee = String.valueOf(sganjee.getSingleResult().toString().charAt(4));
            tganjee = switch (hour) {
                case "23", "24", "0" ->
                    // 자
                        switch (dGanjee) {
                            case "갑", "기" -> "갑자";
                            case "을", "경" -> "병자";
                            case "병", "신" -> "무자";
                            case "정", "임" -> "경자";
                            case "무", "계" -> "임자";
                            default -> tganjee;
                        };
                case "1", "2" ->
                    // 축
                        switch (dGanjee) {
                            case "갑", "기" -> "을축";
                            case "을", "경" -> "정축";
                            case "병", "신" -> "기축";
                            case "정", "임" -> "신축";
                            case "무", "계" -> "계축";
                            default -> tganjee;
                        };
                case "3", "4" ->
                    // 인
                        switch (dGanjee) {
                            case "갑", "기" -> "병인";
                            case "을", "경" -> "무인";
                            case "병", "신" -> "경인";
                            case "정", "임" -> "임인";
                            case "무", "계" -> "갑인";
                            default -> tganjee;
                        };
                case "5", "6" ->
                    // 묘
                        switch (dGanjee) {
                            case "갑", "기" -> "정묘";
                            case "을", "경" -> "기묘";
                            case "병", "신" -> "신묘";
                            case "정", "임" -> "계묘";
                            case "무", "계" -> "을묘";
                            default -> tganjee;
                        };
                case "7", "8" ->
                    // 진
                        switch (dGanjee) {
                            case "갑", "기" -> "무진";
                            case "을", "경" -> "경진";
                            case "병", "신" -> "임진";
                            case "정", "임" -> "갑진";
                            case "무", "계" -> "병진";
                            default -> tganjee;
                        };
                case "9", "10" ->
                    // 사
                        switch (dGanjee) {
                            case "갑", "기" -> "기사";
                            case "을", "경" -> "신사";
                            case "병", "신" -> "계사";
                            case "정", "임" -> "을사";
                            case "무", "계" -> "정사";
                            default -> tganjee;
                        };
                case "11", "12" ->
                    // 오
                        switch (dGanjee) {
                            case "갑", "기" -> "경오";
                            case "을", "경" -> "임오";
                            case "병", "신" -> "갑오";
                            case "정", "임" -> "병오";
                            case "무", "계" -> "무오";
                            default -> tganjee;
                        };
                case "13", "14" ->
                    // 미
                        switch (dGanjee) {
                            case "갑", "기" -> "신미";
                            case "을", "경" -> "계미";
                            case "병", "신" -> "을미";
                            case "정", "임" -> "정미";
                            case "무", "계" -> "거미";
                            default -> tganjee;
                        };
                case "15", "16" ->
                    // 신
                        switch (dGanjee) {
                            case "갑", "기" -> "임신";
                            case "을", "경" -> "갑신";
                            case "병", "신" -> "병신";
                            case "정", "임" -> "무신";
                            case "무", "계" -> "경신";
                            default -> tganjee;
                        };
                case "17", "18" ->
                    // 유
                        switch (dGanjee) {
                            case "갑", "기" -> "계유";
                            case "을", "경" -> "을유";
                            case "병", "신" -> "정유";
                            case "정", "임" -> "기유";
                            case "무", "계" -> "신유";
                            default -> tganjee;
                        };
                case "19", "20" ->
                    // 술
                        switch (dGanjee) {
                            case "갑", "기" -> "갑술";
                            case "을", "경" -> "병술";
                            case "병", "신" -> "무술";
                            case "정", "임" -> "경술";
                            case "무", "계" -> "임술";
                            default -> tganjee;
                        };
                case "21", "22" ->
                    // 해
                        switch (dGanjee) {
                            case "갑", "기" -> "을해";
                            case "을", "경" -> "정해";
                            case "병", "신" -> "기해";
                            case "정", "임" -> "신해";
                            case "무", "계" -> "계해";
                            default -> tganjee;
                        };
                default -> "없다";
            };
            String ss = sganjee.getSingleResult().toString().concat(tganjee);
            String[] ganjee = ss.split("");
            for (String s : ganjee) {
                switch (s) {
                    case "병":
                    case "정":
                    case "사":
                    case "오":
                        //화
                        five.h += 1;
                        break;

                    case "임":
                    case "계":
                    case "자":
                    case "해":
                        //수
                        five.s += 1;
                        break;

                    case "갑":
                    case "을":
                    case "인":
                    case "묘":
                        //목
                        five.m += 1;
                        break;

                    case "경":
                    case "신":
                    case "유":
                        //금
                        five.g += 1;
                        break;

                    case "무":
                    case "기":
                    case "축":
                    case "진":
                    case "미":
                    case "술":
                        // 토
                        five.t += 1;
                        break;
                }
            }

        }
        resultQuery = "SELECT movement,count FROM";
        resultQuery += "FiveMovementResult";
        resultQuery += "WHERE (movement,count) (('m',:result.m),('h',:result.h),('t',:result.t),('g,:result.g),('s',:result.s),('maxKey',100))order by count desc";

        TypedQuery<FiveMovementResult> re = em.createQuery(resultQuery, FiveMovementResult.class);
        re.setParameter("result",five);
        Optional<FiveMovementResult> fi = Optional.ofNullable(re.getSingleResult());
        if(fi.isEmpty()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        String ip = util.getIp(request);
        UserTarotHistory uth = UserTarotHistory.builder()
                .idx(idx)
                .regDt(new Date())
                .result(fi.toString())
                .regIp(ip)
                .build();
        UserTarotHistory uthSave = history.save(uth);
        if(!uth.equals(uthSave)){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return fi;
    }


}
