package com.miraiyashi.controller;


import com.miraiyashi.entity.ToonComponent;
import com.miraiyashi.entity.ToonMaster;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.ListReponse;
import com.miraiyashi.service.ToonService;
import com.miraiyashi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/toon")
@RequiredArgsConstructor
public class ToonController {
    @Autowired
    ToonService toonService;

    // 웹툰 마스터 정보
    @GetMapping("/MasterList")
    public ListReponse<ToonMaster> masterL(@RequestParam("page") Integer page){
        return toonService.masterList(page);
    }


    // 웹툰 회차
    @GetMapping("/ComponentList")
    public ListReponse<ToonComponent> componentL(@RequestParam("tIdx") Integer tIdx, @RequestParam("page") Integer page){
        return toonService.component(tIdx,page);
    }
    @PutMapping("/MasterHeart")
    public CommonResponse masterH(@RequestParam("tIdx") int tIdx){
        return toonService.masterHeart(tIdx);
    }
    @PutMapping("/ComponentHeart")
    public CommonResponse commonH(@RequestParam("tcIdx") int tcIdx){
        return toonService.componentHeart(tcIdx);
    }
}
