package com.miraiyashi.controller;


import com.miraiyashi.entity.Landing;
import com.miraiyashi.entity.ToonComponent;
import com.miraiyashi.entity.ToonMaster;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.ListReponse;
import com.miraiyashi.response.SingleReponse;
import com.miraiyashi.service.LandingService;
import com.miraiyashi.service.ToonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/landing")
@RequiredArgsConstructor
public class LandingController {
    @Autowired
    LandingService landingService;

    // landing
    @PostMapping("/joinLanding")
    public CommonResponse landing(@RequestBody Integer idx, HttpServletRequest request){
        return landingService.checkIn(idx,request);
    }


    // landing get
    @GetMapping("/getCode")
    public SingleReponse<String> getUserCode(@RequestParam("idx") Integer idx){
        return landingService.getCode(idx);
    }

    @GetMapping("/List")
    public ListReponse<Landing> landingList(@RequestParam("page") Integer idx){
        return landingService.list(idx);
    }


}
