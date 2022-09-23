package com.example.demo1.Readwrite;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName Readwrite
 * @Date 2022/6/21 17:01
 * @Author chengshoufei
 * @Description TODO
 */
public class Readwrite {
    private volatile int count = 0;
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write() {
        readWriteLock.writeLock().lock();
        System.out.println("获取写锁");
        count++;
        System.out.println("执行了写操作count = " + count);
        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解除写锁");
        readWriteLock.writeLock().unlock();
    }

    public void read() {
        readWriteLock.readLock().lock();
        System.out.println("获取读锁");
        System.out.println("执行了读操作count = " + count);
        try {
            Thread.sleep(6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        readWriteLock.readLock().unlock();
        System.out.println("解除读锁");
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(1, 10, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


        Readwrite readwrite = new Readwrite();

            executorService.execute(() -> {

                readwrite.read();
            });


            executorService.execute(() -> {
                readwrite.write();
            });


    }
}
