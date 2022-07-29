package com.example.demo.service.impl;

import com.example.demo.repository.MypageMapper;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.service.MypageService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.SingletonData;
import com.example.demo.vo.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MypageMapper mypageMapper;
    private final EmailService emailService;

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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int sendVerificationMail(HttpServletRequest request, Map<String, Object> map) {
        int result = 0;
        String ranPw = RandomString.randomStr();
        String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String userId = map.get("userId").toString();
        String to = map.get("userEmail").toString();
        String subject = SingletonData.getInstance().getConfigData().get("homepageName") +" 인증 처리";
        String text = "안녕하세요.<br>" +
                "이메일을 변경하시려면 하단의 코드번호를 복사하여 입력하세요. <br>"+
                "<b>"+ranPw+"</b>";

        boolean res = emailService.sendMail(to, subject, text);

        Map<String,Object> paraMap = new HashMap<>();

        paraMap.put("VerificationCode",ranPw);
        paraMap.put("userId",userId);

        if (res) {
            if(updateVerificationCode(paraMap)){
                result = 1;
            }else {
                result = 0;
            }
        } else {
            result = 0;
        }

        return result;
    }

    @Override
    public boolean updateVerificationCode(Map<String,Object> paramMap) {
        return mypageMapper.updateVerificationCode(paramMap);
    }

    @Override
    public int updateEmail(Map<String, Object> paramMap) {
        int result = 0;
        if(mypageMapper.findVerificationCodeByIdxCode(paramMap) == 1){
            result = mypageMapper.updateEmail(paramMap);
        }
        return result;
    }

}
