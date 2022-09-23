package com.example.demo1.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName Entity
 * @Date 2022/4/7 14:55
 * @Author chengshoufei
 * @Description TODO
 */
public class BeanToGenerateTable {
    public static void main(String[] args) {
        BeanToGenerateTable bean = new BeanToGenerateTable();
        String sql = bean.generateTableOracle("com.example.demo1.entity.DemoData", true);
        System.err.println(sql);

    }

    /***
     * 根据实体类自动生成Oracle建表sql
     * @param beanName  实体类路径
     * @param isConvert  是否需要转换驼峰格式
     * @return
     */
    public String generateTableOracle(String beanName, boolean isConvert) {
        StringBuilder sqlSb = new StringBuilder();

        if (null != beanName && !"".equals(beanName)) {
            Object obj = null;
            try {
                obj = Class.forName(beanName).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 拿到该类
            Class<?> clz = obj.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();
            if (fields != null) {

                StringBuilder commentSb = new StringBuilder();

                sqlSb.append(" DECLARE \n");
                sqlSb.append("     VC_STR VARCHAR2 ( 2000 );\n");
                sqlSb.append(" ERP_COUNT NUMBER;\n");
                sqlSb.append(" BEGIN \n");
                sqlSb.append("   SELECT \n");
                sqlSb.append("       COUNT( * ) INTO ERP_COUNT \n");
                sqlSb.append("   FROM \n");
                sqlSb.append("       USER_TABLES \n");
                sqlSb.append("   WHERE \n");

                //获取实体类的Table注解表名（导入persistence包）
                Table tableClass = clz.getAnnotation(Table.class);
                String tableName = "";
                if (tableClass != null) {
                    System.out.println("表名：" + tableClass.name());
                    tableName = tableClass.name();
                }
                sqlSb.append("       TABLE_NAME = '" + tableName + "'; \n");
                sqlSb.append("   IF \n");
                sqlSb.append("       ERP_COUNT < 1 THEN \n");
                sqlSb.append("       VC_STR := 'CREATE TABLE \"" + tableName + "\" ( \n");

                String idKey = "";

                for (Field field : fields) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    String type = field.getGenericType().toString();
                    System.out.println("类的属性类型全称:" + type);
                    if (type.indexOf(".") == -1 || type.indexOf("java.") != -1) {//过滤实体对象
                        //截取最后一个.后面的字符
                        type = type.substring(type.lastIndexOf(".") + 1);
                        System.out.println("对象属性类型:" + type);
                        if (!field.getType().equals(List.class)) {// 不匹配list类型
                            //字段名称
                            String name = field.getName();

                            Method doSomeMethod = null;
                            try {
                                //获取get方法
                                doSomeMethod = clz.getDeclaredMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            boolean isExist = true;
                            //判断该方法上是否存在这个注解
                            /*if (doSomeMethod.isAnnotationPresent(Transient.class)) {
                                Transient aTransient = doSomeMethod.getAnnotation(Transient.class);
                                System.out.println(name+"==>对象不属于表字段："+aTransient);
                                isExist =false;
                            }
                            //判断该字段上是否存在这个注解
                            if (field.isAnnotationPresent(Transient.class)) {
                                Transient aTransient = field.getAnnotation(Transient.class);
                                System.out.println(name+"==>对象不属于表字段："+aTransient);
                                isExist =false;
                            }*/
                            //（Transient注解是导入persistence包）
                            if (field.isAnnotationPresent(Transient.class) || (null != doSomeMethod && doSomeMethod.isAnnotationPresent(Transient.class))) {
                                System.out.println(name + "==>对象不属于表字段");
                                isExist = false;
                            }
                            //属于表字段
                            if (isExist) {
                                StringBuilder convertName = new StringBuilder();
                                convertName.append(name);
                                //如果需要转换驼峰格式
                                if (isConvert) {
                                    convertName = new StringBuilder();
                                    for (int i = 0; i < name.length(); i++) {
                                        //如果是大写前面先加一个_
                                        if (isUpperCase(name.charAt(i))) {
                                            convertName.append("_");
                                        }
                                        convertName.append(name.charAt(i));
                                    }
                                }
                                name = convertName.toString();
                                sqlSb.append("       \"" + name.toUpperCase() + "\" ");
                                //java 数据类型转换成 Oracle 字段数据类型
                                sqlSb.append(" " + this.societyOracle(type));
                                //判断该字段是否是Id主键（导入persistence包）
                                if (field.isAnnotationPresent(Id.class)) {
                                    Id id = field.getAnnotation(Id.class);
                                    idKey = name.toUpperCase();//id主键字段
                                    System.out.println("id主键字段：" + idKey);
                                    sqlSb.append("  NOT NULL, \n");
                                } else {
                                    sqlSb.append(" , \n");
                                }

                                //字段属性说明（没有ApiModelProperty包的把这段代码注释掉）
                                if (field.isAnnotationPresent(ApiModelProperty.class)) {
                                    ApiModelProperty explain = field.getAnnotation(ApiModelProperty.class);
                                    System.out.println("字段说明：" + explain.value());
                                    commentSb.append("COMMENT ON COLUMN  \"" + tableName + "\"");
                                    commentSb.append(".\"" + name.toUpperCase() + "\" IS '" + explain.value() + "'; \n");
                                } else {
                                    commentSb.append("COMMENT ON COLUMN  \"" + tableName + "\"");
                                    commentSb.append(".\"" + name.toUpperCase() + "\" IS ''; \n");
                                }

                                /**
                                 * 字段属性说明
                                 * 没有ApiModelProperty包的把上面那段代码注释掉，用这个
                                 */
                                /*
                                commentSb.append("COMMENT ON COLUMN  \""+tableName+"\"");
                                commentSb.append(".\""+name.toUpperCase()+"\" IS ''; \n");
                                */

                            }

                        }
                    }

                }
                if (null != idKey && !"".equals(idKey)) {
                    sqlSb.append(" constraint " + tableName + " primary key(" + idKey + ") \n");
                } else {
                    String lastStr = sqlSb.substring(0, sqlSb.length() - 1);
                    if (lastStr.equals(",")) {
                        //删除最后一个字符
                        sqlSb = sqlSb.deleteCharAt(sqlSb.length() - 1);
                    }
                }
                sqlSb.append(" )'; \n");
                sqlSb.append("      EXECUTE IMMEDIATE VC_STR; \n");
                sqlSb.append("      COMMIT; \n");
                sqlSb.append("   END IF; \n");
                sqlSb.append(" END; \n");
                sqlSb.append("/\n");

                sqlSb.append(commentSb);
            }
        }

        return sqlSb.toString();
    }

    //字母是否是大写
    public boolean isUpperCase(char c) {
        return c >= 65 && c <= 90;
    }

    public String societyOracle(String javaType) {
        String oracleType = "";
        if (javaType.equals("String")) {
            oracleType = "VARCHAR2(255)";
        }
        //不区分大小写
        else if (javaType.equalsIgnoreCase("BigDecimal") || javaType.equalsIgnoreCase("int") || javaType.equalsIgnoreCase("Integer") || javaType.equalsIgnoreCase("boolean") || javaType.equalsIgnoreCase("byte") || javaType.equalsIgnoreCase("short") || javaType.equalsIgnoreCase("long") || javaType.equalsIgnoreCase("float") || javaType.equalsIgnoreCase("double")) {
            oracleType = "NUMBER";
        } else if (javaType.equals("Date")) {
            oracleType = "DATE";
        } else if (javaType.equals("Timestamp")) {
            oracleType = "TIMESTAMP";
        } else if (javaType.equals("Blob")) {
            oracleType = "BLOB";
        } else if (javaType.equals("Clob")) {
            oracleType = "CLOB";
        }
        return oracleType;
    }


}
