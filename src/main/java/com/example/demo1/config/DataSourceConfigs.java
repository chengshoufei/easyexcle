//package com.example.demo1.config;
//
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//
//import javax.sql.DataSource;
//
///**
// * @ClassName mybatis-plus s数据源
// * @Date 2022/4/8 14:38
// * @Author chengshoufei
// * @Description TODO
// */
//@Configuration
//// 扫描mapper
//@MapperScan(basePackages = {"mapper"},sqlSessionFactoryRef = "sqlSessionFactoryRef",sqlSessionTemplateRef = "sqlSessionTemplateRef")
//public class DataSourceConfigs {
//
//    @Autowired
//    private DataSource dataSource;
//
//    static String MAPPER_LOCAYION="classpath:mapper/**.xml";
// @Bean(name = "sqlSessionFactoryRef")
//    public SqlSessionFactory sqlSessionFactory()throws Exception{
//     PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//     Resource[] resolverResources = resolver.getResources(MAPPER_LOCAYION);
//     MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//     bean.setDataSource(dataSource);
//     bean.setMapperLocations(resolverResources);
//     //设置分页
//     MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
//     PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//     paginationInterceptor.setOverflow(true);
//     mybatisConfiguration.addInterceptor(paginationInterceptor);
//     bean.setConfiguration(mybatisConfiguration);
//     return bean.getObject();
// }
//
// @Bean(name = "sqlSessionTemplateRef")
//    public SqlSessionTemplate sqlSessionTemplate()throws Exception{
//     return new SqlSessionTemplate(sqlSessionFactory());
// }
// @Bean
//    public PlatformTransactionManager transactionManager()
// {
//     return  new DataSourceTransactionManager(dataSource);
// }
//
//
//
//}
