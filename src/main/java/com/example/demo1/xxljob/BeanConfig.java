package com.example.demo1.xxljob;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanConfig
 * @Date 2022/10/9 10:16
 * @Author chengshoufei
 * @Description TODO
 */
@Configuration
public class BeanConfig {
    private final Logger logger = LoggerFactory.getLogger ( BeanConfig.class );

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor ( ) {
        logger.info ( ">>>>>>>>>>> xxl-job config init." );
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor ( );
        xxlJobSpringExecutor.setAdminAddresses ( adminAddresses );
        xxlJobSpringExecutor.setAppname ( appName );
        xxlJobSpringExecutor.setPort ( port );
        xxlJobSpringExecutor.setLogPath ( logPath );
        xxlJobSpringExecutor.setLogRetentionDays ( logRetentionDays );
        return xxlJobSpringExecutor;
    }


}
