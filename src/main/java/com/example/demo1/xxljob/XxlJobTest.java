package com.example.demo1.xxljob;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @ClassName XxlJobTest
 * @Date 2022/10/9 10:17
 * @Author chengshoufei
 * @Description TODO
 */

@Component
@EnableScheduling
public class XxlJobTest {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobTest.class);
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) throws Exception {

        logger.info("测试任务1被触发了");
//        XxlJobLogger.log("测试任务1被触发了");
        return ReturnT.SUCCESS;
    }

    @XxlJob("helloJob")
    public ReturnT<String> helloJobHandler(String param) throws Exception {
        System.out.println("定时任务helloJob被执行了-----------");
        return ReturnT.SUCCESS;
    }


}
