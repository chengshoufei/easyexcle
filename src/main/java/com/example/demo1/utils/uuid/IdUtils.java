package com.example.demo1.utils.uuid;

/**
 * @ClassName IdUtils
 * @Date 2022/9/16 9:40
 * @Author chengshoufei
 * @Description ID生成器工具类
 */
public class IdUtils
{
    public static void main ( String[] args ) {
        System.out.println (IdUtils.fastUUID () );
        System.out.println (IdUtils.fastSimpleUUID () );
        System.out.println (IdUtils.randomUUID () );
        System.out.println (IdUtils.simpleUUID () );

    }
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
