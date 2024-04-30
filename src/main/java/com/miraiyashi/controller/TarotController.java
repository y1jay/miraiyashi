package com.miraiyashi.controller;


import com.miraiyashi.dto.TarotDto;
import com.miraiyashi.dto.TarotResultDto;
import com.miraiyashi.entity.BotCategory;
import com.miraiyashi.entity.ToonComponent;
import com.miraiyashi.entity.ToonMaster;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.ListReponse;
import com.miraiyashi.response.SingleReponse;
import com.miraiyashi.service.TarotService;
import com.miraiyashi.service.ToonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tarot")
@RequiredArgsConstructor
public class TarotController {
    @Autowired
    TarotService tarotService;

    // 봇 카테고리 리스트
    @GetMapping("/BotCategory")
    public ListReponse<BotCategory>
    botCategory(
            @RequestParam("bIdx") int bIdx,
            @RequestParam("page") int page)
    {
        return  tarotService.botCategory(bIdx,page);
    }

    // 봇 메세지
    @GetMapping("/BotMessage")
    public SingleReponse<List>
    botMsg(
            @RequestParam("idx") int idx,
            @RequestParam("bIdx") int bIdx,
            @RequestParam("bcIdx") int bcIdx,
            @RequestParam("type") int type)
    {
        return  tarotService.botMsg(idx,bIdx,bcIdx,type);
    }

    // 타로 플레이
    @PostMapping("/TarotPlay")
    public SingleReponse<TarotResultDto>
    tarot(
            @RequestBody() HashMap<String, Object> param,
            HttpServletRequest request
    ){
        return tarotService.tarot(param,request);
    }
}
