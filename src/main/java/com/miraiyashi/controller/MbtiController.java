package com.miraiyashi.controller;


import com.miraiyashi.entity.Mbti;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.SingleReponse;
import com.miraiyashi.service.LandingService;
import com.miraiyashi.service.MbtiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mbti")
@RequiredArgsConstructor
public class MbtiController {
    @Autowired
    MbtiService mbtiService;

    // mbti get
    @GetMapping("/getMbti")
    public SingleReponse<String> mbtiget(@RequestParam("idx") Integer idx){
        return mbtiService.userMbit(idx);
    }


    // mbti insert
    @PostMapping("/mbtiInsert")
    public CommonResponse mbtiInsert(@RequestBody Mbti mbti, HttpServletRequest request){
        return mbtiService.mbtiInsert(mbti,request);
    }


}
