package com.example.demo.quartz;

import com.example.demo.repository.AdminMapper;
import com.example.demo.repository.CommonMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class JobWithdrawUser extends QuartzJobBean {

    @Autowired
    CommonMapper commonMapper;

    private static final Logger log = LoggerFactory.getLogger(JobWithdrawUser.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("==매일 0시 1초에 작업 시작==");

        int result = commonMapper.deleteWithdrawUser();
        if(result == 1){
            log.debug("탈퇴 한지 한달 된 유저 삭제 완료");
        }else{
            log.debug("탈퇴 한지 한달 된 유저 없음");
        }

        log.debug("==매일 0시 1초에 작업 끝==");
    }
}