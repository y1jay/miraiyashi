package com.miraiyashi.service;

import com.miraiyashi.entity.Landing;
import com.miraiyashi.entity.Mbti;
import com.miraiyashi.libs.util;
import com.miraiyashi.repository.LandingRepository;
import com.miraiyashi.repository.MbtiRepository;
import com.miraiyashi.response.CommonResponse;
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
public class MbtiService {

    @Autowired
    MbtiRepository mbti;
    @Autowired(required = false)
    CommonResponse res = new CommonResponse();

    // 회원 mbti 조회
    public SingleReponse<String> userMbit(Integer idx){
        SingleReponse<String> res = new SingleReponse<>();
        Optional<Mbti> um = mbti.findByIdx(idx);
        if(um.isEmpty()){
            res.setCode(404);
            res.setMessage("정보가 없습니다.");
        }else{
            res.setData(um.get().getResult());
            res.setCode(200);

        }
        return res;
    }

    // 회원 mbti 입력
    public CommonResponse mbtiInsert(Mbti mbti, HttpServletRequest request){
        CommonResponse res = new CommonResponse();
        String ip = util.getIp(request);
        Mbti m = Mbti.builder()
                .idx(mbti.getIdx())
                .regDt(new Date())
                .regIp(ip)
                .build();
        Mbti save = this.mbti.save(m);
        if(!save.equals(m)){
            res.setMessage("저장에 실패했습니다.");
            res.setCode(400);
        }
      return res;
    }
}
