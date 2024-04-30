package com.miraiyashi.service;

import com.miraiyashi.entity.Landing;
import com.miraiyashi.libs.util;
import com.miraiyashi.repository.LandingRepository;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.ListReponse;
import com.miraiyashi.response.SingleReponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LandingService {

    @Autowired
    LandingRepository landing;

    @Autowired(required = false)
    CommonResponse res;


    // 랜딩 신청
    public CommonResponse checkIn(Integer idx, HttpServletRequest request){
        Optional<Landing> ld = this.landing.findByIdx(idx);
        if(ld.isPresent()){
            res.setCode(400);
            res.setMessage("이미 신청 한 회원입니다.");
            return res;
        }
        String landingCode = util.hash(idx.toString());
        String ip = util.getIp(request);
        Landing landing = Landing
                .builder()
                .landingCode(landingCode)
                .idx(idx)
                .regIp(ip)
                .regDt(new Date())
                .build();
        Landing save = this.landing.save(landing);
        if(save.equals(landing)){
            res.setCode(200);
            res.setMessage("Success");
        }else{
            res.setCode(400);
            res.setMessage("랜딩신청에 실패했습니다.");
            return res;
        }
        return res;
    }

    // 리스트
    public ListReponse<Landing> list(Integer page){
        ListReponse<Landing> res = new ListReponse<>();
        PageRequest pageRequest =
                PageRequest
                        .of(page, 5,
                                Sort.by("lIdx")
                                        .descending()
                        );
        res.setData(landing.findAll(pageRequest).toList());
        return res;
    }

    // 코드조회
    public SingleReponse<String> getCode(Integer idx){
        Optional<Landing> cd = landing.findByIdx(idx);
        SingleReponse<String> res = new SingleReponse<>();
        if(cd.isEmpty()){
            res.setCode(400);
            res.setMessage("내역이 없습니다.");
        }else{
            res.setData(cd.get().getLandingCode());
            res.setCode(200);
        }
        return res;
    }
}
