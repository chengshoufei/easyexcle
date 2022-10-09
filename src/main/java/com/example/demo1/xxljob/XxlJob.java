package com.example.demo1.xxljob;

import com.xxl.job.core.biz.model.ReturnT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @ClassName XxlJob
 * @Date 2022/10/9 14:46
 * @Author chengshoufei
 * @Description TODO
 */
@Component
@EnableScheduling
public class XxlJob {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobTest.class);

    @com.xxl.job.core.handler.annotation.XxlJob("xxlJob")
    public ReturnT<String> helloJobHandler(String param) throws Exception {
        System.out.println("成==========功");
        return ReturnT.SUCCESS;
    }
}
