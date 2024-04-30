package com.miraiyashi.service;

import com.miraiyashi.entity.User;
import com.miraiyashi.entity.UserLoginHistory;
import com.miraiyashi.entity.UserPaymentHistory;
import com.miraiyashi.entity.UserRewardHistory;
import com.miraiyashi.libs.util;
import com.miraiyashi.repository.UserLoginHistoryRepository;
import com.miraiyashi.repository.UserPaymentHistoryRepository;
import com.miraiyashi.repository.UserRepository;
import com.miraiyashi.repository.UserRewardHistoryRepository;
import com.miraiyashi.response.CommonResponse;
import com.miraiyashi.response.CustomException;
import com.miraiyashi.response.ErrorCode;
import com.miraiyashi.response.SingleReponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLoginHistoryRepository userLoginHistoryRepository;

    @Autowired
    UserRewardHistoryRepository userRewardHistoryRepository;

    @Autowired
    UserPaymentHistoryRepository userPaymentHistoryRepository;

    @Autowired(required = false)
    CommonResponse res = new CommonResponse();

    // 회원가입
    public CommonResponse Join(User user, HttpServletRequest request){
//        CommonResponse res = new CommonResponse();
        if(user == null){
//            throw new Error("인자값이 비어있습니다");
            res.setCode(-400);
            res.setMessage("인자값이 비어있습니다.");
            return res;
        }
        if(this.duplicate(user.getIdx())){
           res.setCode(200);
           res.setMessage("이미 가입 된 회원");
            return res;
        }else{
            String ip = util.getIp(request);
            user.setJoinIp(ip);
            userRepository.save(user);
            res.setCode(200);
            res.setMessage("가입 완료");
        }
        return res;
    }

    // 로그인
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public SingleReponse<User> Login(User user, HttpServletRequest request){
        SingleReponse<User> res = new SingleReponse<User>();
        if(!this.duplicate(user.getIdx())){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        SingleReponse<User> info = this.Info(user.getIdx());
        if(info.getData() == null){
            throw new CustomException(ErrorCode.NOT_FOUND);

        }
        String ip = util.getIp(request);

        UserLoginHistory history =
                UserLoginHistory
                        .builder()
                        .idx(info.getData().getIdx())
                        .deviceUuid(info.getData().getDeviceUuid())
                        .loginDt(new Date())
                        .loginIp(ip).build();
      UserLoginHistory uls =  userLoginHistoryRepository.save(history);
      if(uls != history){
          throw new CustomException(ErrorCode.BAD_REQUEST);
      }else{
          res.setCode(200);
          res.setData(info.getData());
          return res;
      }
      // 토큰 ?
    }
    private Boolean duplicate(Integer idx){
        Optional<User> duplicate = userRepository.findById(idx);
        return duplicate.isPresent();
    }

    public SingleReponse<User> Info(Integer idx){
        Optional<User> info = userRepository.findById(idx);
        SingleReponse<User> res = new SingleReponse<User>();
        if(info.isPresent()){
            res.setData(info.get());
        }else{
            res.setCode(404);
            res.setMessage("회원정보가 없습니다.");
        }
        return res;
    }

    // 리워드
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public CommonResponse Reward(Integer idx, Integer type, HttpServletRequest request){
        CommonResponse res = new CommonResponse();
        Optional<User> user = userRepository.findById(idx);
        if(user.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_DATA);

        }
        Integer beforeMineral = user.get().getMineral();
        String ip = util.getIp(request);

        UserRewardHistory history =
                UserRewardHistory
                        .builder()
                        .reward(1)
                        .idx(user.get().getIdx())
                        .beforeMineral(beforeMineral)
                        .afterMineral(beforeMineral+1)
                        .type(type)
                        .regDt(new Date())
                        .regIp(ip).build();
       UserRewardHistory us = userRewardHistoryRepository.save(history);
       if(us != history){
           throw new CustomException(ErrorCode.BAD_REQUEST);
       }
//       user.get().setMineral(beforeMineral+1);
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
               .mineral(beforeMineral+1).build();

        User reward =  userRepository.save(afterUser);
        if(reward!=afterUser){

            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        return res;
    }

    // 영수증
    public UserPaymentHistory receipt(UserPaymentHistory uph, HttpServletRequest request){
        String ip = util.getIp(request);
        uph.setRegIp(ip);
        return userPaymentHistoryRepository.save(uph);
    }


}
