package com.example.demo.service.impl;

import com.example.demo.repository.MypageMapper;
import com.example.demo.service.CommonService;
import com.example.demo.service.EmailService;
import com.example.demo.service.MypageService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.SingletonData;
import com.example.demo.vo.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MypageMapper mypageMapper;
    private final CommonService commonServive;

    @Override
    public int withdrawUser(Map<String, Object> paramMap) {
        return mypageMapper.withdrawUser(paramMap);
    }

    @Override
    public Users findUserInfoByIdx(String idx) {
        return mypageMapper.findUserInfoByIdx(idx);
    }

    @Override
    public int updateProfile(MultipartFile[] files, String idx) {
        //TODO 이미지 로직
        return 0;
    }

    @Override
    public int updateNicknm(Map<String, Object> paramMap) {
        return mypageMapper.updateNicknm(paramMap);
    }

    @Override
    public int updateEmail(Map<String, Object> paramMap) {
        int result = 0;

        String mailVerification= SingletonData.getInstance().getConfigData().get("mailVerification").toString();

        // 메일 인증 사용 여부
        if(mailVerification.equals("N")){
            result = mypageMapper.updateEmail(paramMap);
        }else{
            if(mypageMapper.findVerificationCodeByIdxCode(paramMap) == 1){
                result = mypageMapper.updateEmail(paramMap);
            }
        }

        return result;
    }

    @Override
    public int sendVerificationMail(HttpServletRequest request, Map<String, Object> map) {
        Map<String,Object> mailMap = new HashMap<>();
        String ranPw = RandomString.randomStr();
        String userId = map.get("userId").toString();

        String subject = SingletonData.getInstance().getConfigData().get("homepageName") +" 이메일 인증";

        String text = "안녕하세요.<br>" +
                "이메일을 인증하시려면 하단의 코드 번호를 복사하여 입력하세요. <br>"+
                "[<b> "+ranPw+" </b>]";

        mailMap.put("ranPw", ranPw);
        mailMap.put("userId", userId);
        mailMap.put("userEmail", map.get("userEmail"));
        mailMap.put("subject",subject);
        mailMap.put("text",text);

        return commonServive.sendVerificationMail(request, mailMap);
    }

}
