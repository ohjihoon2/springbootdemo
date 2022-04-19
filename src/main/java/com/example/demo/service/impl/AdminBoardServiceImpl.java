package com.example.demo.service.impl;

import com.example.demo.repository.AdminBoardMapper;
import com.example.demo.service.AdminBoardService;
import com.example.demo.service.EmailService;
import com.example.demo.vo.Content;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Qna;
import com.example.demo.vo.QnaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    public Map<String, Object> findByIdxBoardMaster(int idx) {
        return adminMapper.findByIdxBoardMaster(idx);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int deleteBoardMaster(Map<String, Object> paramMap) {
        int result = 0;
        if(adminMapper.deleteBoardMaster(paramMap) == 1){
            adminMapper.deleteBoard(paramMap);

            // TODO 2022-04-19
            //  - 댓글 테이블 생성 후 BOARD 삭제 이후에 관련 댓글도 모두 삭제 코드 작성

            result = 1;
        }

        return result;
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

        QnaConfig qnaConfig = adminMapper.findByIdxQnaConfig(Integer.parseInt(paramMap.get("originalIdx").toString()));

        // insert qna answer
        if(adminMapper.answerQna(paramMap) == 1){
            // QNA_STATUS 업데이트
            if(adminMapper.updateOriginalQna(paramMap) == 1){
                // 수신 여부에 따른 이메일 처리
                if(qnaConfig.getQaEmailRecvYn().equals("Y")){
                    String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");;
                    String to = adminMapper.findUserEmailByIdx(qnaConfig.getCreateIdx());
                    String subject = "[ "+siteName+" Qna 답변처리]";
                    String text = "안녕하세요.<br>" +
                            "회원님께서 질문하신 내용에 대한 답변이 등록되었습니다. <br>" +
                            "<b><a href=\""+domain+"\">홈페이지</a></b> Q&A에서 답변을 확인하세요.";

                    if(emailService.sendMail(to, subject, text)){
                        result = 1;
                    }
                }else{
                        result = 1;
                }
            }
        }
        return result;
    }

    @Override
    public QnaConfig findByIdxQnaConfig(int idx) {
        return adminMapper.findByIdxQnaConfig(idx);
    }

    @Override
    public List<Map<String, Object>> findAllFaqMaster(Criteria criteria) {
        return adminMapper.findAllFaqMaster(criteria);
    }

    @Override
    public int insertFaqMaster(Map<String, Object> paramMap) {
        return adminMapper.insertFaqMaster(paramMap);
    }

    @Override
    public int updateFaqMaster(Map<String, Object> paramMap) {
        return adminMapper.updateFaqMaster(paramMap);
    }

    @Override
    public int deleteFaqMaster(Map<String, Object> paramMap) {
        int result = 0;
        if(adminMapper.deleteFaqMaster(paramMap) == 1){

            // TODO 2022-04-19
            //  - FAQ MASTER 삭제 시 FAQ 삭제 되도록 추가
//            adminMapper.deleteFaq(paramMap);

            result = 1;
        }

        return result;
    }

}
