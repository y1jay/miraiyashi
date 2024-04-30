package com.miraiyashi.controller;


import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.SingleReponse;
import com.miraiyashi.service.LuckyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lucky")
@RequiredArgsConstructor
public class LuckyController {
    @Autowired
    LuckyService luckyService;

    // 포춘쿠키
    @PostMapping("/cookie")
    public SingleReponse<String> cookies(@RequestBody() String idx, HttpServletRequest request){
        System.out.println("TESTESTESTES"+idx);
        return luckyService.CookiePlay(idx,request);
    }
    // 행운의카드
    @PostMapping("/lucky")
    public SingleReponse<String> lucky(@RequestBody() String idx, HttpServletRequest request){
        System.out.println("TESTESTESTES"+idx);
        return luckyService.LuckCardPlay(idx,request);
    }
//
//
//    // landing get
//    @GetMapping("/getCode")
//    public SingleReponse<String> componentL(@RequestParam("idx") Integer idx){
//        return landingService.getCode(idx);
//    }


}
