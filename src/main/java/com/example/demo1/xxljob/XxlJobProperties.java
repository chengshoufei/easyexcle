package com.example.demo1.xxljob;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName XxlJobProperties
 * @Date 2022/10/9 10:14
 * @Author chengshoufei
 * @Description TODO
 */
@Configuration
public class XxlJobProperties {
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

//    @Value("${xxl.job.accessToken}")
//    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String appname;

//    @Value("${xxl.job.executor.address}")
//    private String address;

//    @Value("${xxl.job.executor.ip}")
//    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    public String getAdminAddresses ( ) {
        return adminAddresses;
    }

    public void setAdminAddresses ( String adminAddresses ) {
        this.adminAddresses = adminAddresses;
    }

    public String getAppname ( ) {
        return appname;
    }

    public void setAppname ( String appname ) {
        this.appname = appname;
    }

    public int getPort ( ) {
        return port;
    }

    public void setPort ( int port ) {
        this.port = port;
    }

    public String getLogPath ( ) {
        return logPath;
    }

    public void setLogPath ( String logPath ) {
        this.logPath = logPath;
    }

    public int getLogRetentionDays ( ) {
        return logRetentionDays;
    }

    public void setLogRetentionDays ( int logRetentionDays ) {
        this.logRetentionDays = logRetentionDays;
    }
}
