package com.example.demo1.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.Collections;

/**
 * @ClassName AutoGenerator
 * @Date 2022/9/6 16:13
 * @Author chengshoufei
 * @Description TODO
 */
public class AutoGenerator {
    private static final String url = "jdbc:mysql://localhost:3306/mybatisplus?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private static final String username = "root";
    private static final String password = "123456";

    public static void main(String[] args) {
        //设置数据源
        new DataSourceConfig.Builder(url, username, password).build();

        new GlobalConfig.Builder()
                //覆盖已生成文件
                .fileOverride()
                //指定输出目录
                .outputDir("/opt/baomidou")
                //作者名
                .author("baomidou")
                //开启 kotlin 模式
                .enableKotlin()
                //	开启 swagger 模式
                .enableSwagger()
                //时间策略
                .dateType(DateType.TIME_PACK)
                //注释日期
                .commentDate("yyyy-MM-dd")
                .build();

        new PackageConfig.Builder()
                //父包名
                .parent("com.baomidou.mybatisplus.samples.generator")
                //父包模块名
                .moduleName("sys")
                //Entity 包名
                .entity("po")
                ///Service 包名
                .service("service")
                //Service Impl 包名
                .serviceImpl("service.impl")
                //Mapper 包名
                .mapper("mapper")
                //Mapper XML 包名
                .xml("mapper.xml")
                //Controller 包名
                .controller("controller")
                //自定义文件包名
                .other("other")
                //路径配置信息
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://"))
                .build();

        new StrategyConfig.Builder()
                //开启大写命名
                .enableCapitalMode()
                //开启跳过视图
                .enableSkipView()
                //禁用 sql 过滤
                .disableSqlFilter()
                //模糊表匹配(sql 过滤)
                .likeTable(new LikeTable("USER"))
                //模糊表排除(sql 过滤)
                .addInclude("t_simple")
                //增加过滤表前缀
                .addTablePrefix("t_", "c_")
                //增加过滤表后缀
                .addFieldSuffix("_flag")
                .build();
        new StrategyConfig.Builder()
                //名称转换实现
                .entityBuilder()
                //设置父类
                //.superClass(BaseEntity.class)
                //禁用生成 serialVersionUID
                .disableSerialVersionUID()
                //开启链式模型
                .enableChainModel()
                //开启 lombok 模型
                .enableLombok()
                //开启 Boolean 类型字段移除 is 前缀
                .enableRemoveIsPrefix()
                //开启生成实体时生成字段注解
                .enableTableFieldAnnotation()
                //开启 ActiveRecord 模型
                .enableActiveRecord()
                //	乐观锁字段名(数据库)
                .versionColumnName("version")
                //	乐观锁属性名(实体)
                .versionPropertyName("version")
                //逻辑删除字段名(数据库)
                .logicDeleteColumnName("deleted")
                //逻辑删除属性名(实体)
                .logicDeletePropertyName("deleteFlag")
                //数据库表映射到实体的命名策略
                .naming(NamingStrategy.no_change)
                //数据库表字段映射到实体的命名策略
                .columnNaming(NamingStrategy.underline_to_camel)
                //添加父类公共字段
                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                //添加忽略字段
                .addIgnoreColumns("age")
                //添加表字段填充
                .addTableFills(new Column("create_time", FieldFill.INSERT))
                //添加表字段填充
                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                //全局主键类型
                .idType(IdType.AUTO)
                //	格式化文件名称
                .formatFileName("%sEntity")
                .build();
        new StrategyConfig.Builder()
                .controllerBuilder()
                //设置父类
                //.superClass(BaseController.class)
                //开启驼峰转连字符
                .enableHyphenStyle()
                //开启生成@RestController 控制器
                .enableRestStyle()
                //格式化文件名称
                .formatFileName("%sAction")
                .build();

        new StrategyConfig.Builder()
                .serviceBuilder()
                //设置 service 接口父类
                //.superServiceClass(BaseService.class)
                //设置 service 实现类父类
                // .superServiceImplClass(BaseServiceImpl.class)
                //格式化 service 接口文件名称
                .formatServiceFileName("%sService")
                //格式化 service 实现类文件名称
                .formatServiceImplFileName("%sServiceImp")
                .build();

        new StrategyConfig.Builder()
                .mapperBuilder()
                //	设置父类
                .superClass(BaseMapper.class)
                //开启 @Mapper 注解
                .enableMapperAnnotation()
                //启用 BaseResultMap 生成
                .enableBaseResultMap()
                //启用 BaseColumnList
                .enableBaseColumnList()
                //设置缓存实现类
                //  .cache(MyMapperCache.class)
                //格式化 mapper 文件名称
                .formatMapperFileName("%sDao")
                //格式化 xml 实现类文件名称
                .formatXmlFileName("%sXml")
                .build();

    }


}
