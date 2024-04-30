package com.miraiyashi.controller;


import com.miraiyashi.entity.User;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.SingleReponse;
import com.miraiyashi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/join")
    public CommonResponse JoinUser(@RequestBody User user, HttpServletRequest request){
        System.out.println(user);
        return userService.Join(user,request);
    }

    @PostMapping("/login")
    public SingleReponse<User> Loginuser(@RequestBody User user,HttpServletRequest request){
        return userService.Login(user,request);
    }

    @PostMapping("/reward")
    public CommonResponse rewarding(@RequestBody Integer idx,@RequestBody Integer type, HttpServletRequest request){
        return userService.Reward(idx,type,request);
    }
}
