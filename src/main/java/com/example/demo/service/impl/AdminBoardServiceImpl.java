package com.example.demo.service.impl;

import com.example.demo.repository.AdminBoardMapper;
import com.example.demo.service.AdminBoardService;
import com.example.demo.service.EmailService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService {

    private final AdminBoardMapper adminMapper;
    private final EmailService emailService;

    @Value("${site.name}")
    private String siteName;

    @Override
    public List<Map<String,Object>> findAllBoardMaster(Criteria criteria) {
        return adminMapper.findAllBoardMaster(criteria);
    }

    @Override
    public int insertBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.insertBoardMaster(paramMap);
    }

    @Override
    public int existsBoardId(Map<String,Object> paramMap) {
        return adminMapper.existsBoardId(paramMap);
    }

    @Override
    public int updateBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.updateBoardMaster(paramMap);
    }

    @Override
    public BoardMaster findByIdxBoardMaster(int idx) {
        return adminMapper.findByIdxBoardMaster(idx);
    }

    @Override
    public int deleteBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.deleteBoardMaster(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllContent(Criteria criteria) {
        return adminMapper.findAllContent(criteria);
    }

    @Override
    public Content findByIdxContent(int idx) {
        return adminMapper.findByIdxContent(idx);
    }

    @Override
    public int insertContent(Map<String, Object> paramMap) {
        return adminMapper.insertContent(paramMap);
    }

    @Override
    public int existsContentId(Map<String, Object> paramMap) {
        return adminMapper.existsContentId(paramMap);
    }

    @Override
    public int updateContent(Map<String, Object> paramMap) {
        return adminMapper.updateContent(paramMap);
    }

    @Override
    public int deleteContent(Map<String, Object> paramMap) {
        return adminMapper.deleteContent(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllQna(Criteria criteria) {
        return adminMapper.findAllQna(criteria);
    }

    @Override
    public int countQna(Criteria criteria) {
        return adminMapper.countQna(criteria);
    }

    @Override
    public List<Qna> findByIdxQna(int idx) {
        return adminMapper.findByIdxQna(idx);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int answerQna(Map<String, Object> paramMap, HttpServletRequest request) {
        int result = 0;
        // originalIdx : 첫 질문의 idx
        // Quesion - Answer - ReQuestion - ReAnswer
        String originalIdx = String.valueOf(paramMap.get("originalIdx"));

        // 이메일 여부 확인 하고 이메일 보내기
        if(adminMapper.updateOriginalQna(paramMap) ==1 ){

            //
            Map<String, Object> resultMap = adminMapper.findQaEmailRecvYnQaStatusCreateIdXByIdxQna(originalIdx);

            if(resultMap.get("qaEmailRecvYn").equals("Y")){

                int createIdx = Integer.parseInt(String.valueOf(resultMap.get("createIdx")));

                if(resultMap.get("qaStatus").equals("ReQuestion")){

                }

//                -- 상태에 따라서 insert - parents_idx 수정하기


                String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");;
                String to = adminMapper.findUserEmailByIdx(createIdx);
                String subject = "[ "+siteName+" Qna 답변처리]";
                String text = "안녕하세요.<br>" +
                        "회원님께서 질문하신 내용에 대한 답변이 등록되었습니다. <br>" +
                        "<b><a href=\""+domain+"\">홈페이지</a></b> Q&A에서 답변을 확인하세요.";
                if(emailService.sendMail(to, subject, text)){
                    result = adminMapper.answerQna(paramMap);
                }else{
                    result = 0;
                }

            }else{
                result = adminMapper.answerQna(paramMap);
            }
        }
        return result;
    }

}
