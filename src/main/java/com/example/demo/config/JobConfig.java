package com.example.demo.config;

import com.example.demo.quartz.JobWithdrawUser;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Configuration
public class JobConfig {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void start(){
        JobDetail jobDetail = buildJobDetail(JobWithdrawUser.class, new HashMap());

        try{
            // 매일 0시 1초에 작업 시작
            scheduler.scheduleJob(jobDetail, buildJobTrigger("1 0 0 * * ?"));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public Trigger buildJobTrigger(String scheduleExp){
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, Map params){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return newJob(job).usingJobData(jobDataMap).build();
    }
}