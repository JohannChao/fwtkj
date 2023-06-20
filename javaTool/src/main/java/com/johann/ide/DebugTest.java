package com.johann.ide;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * @author Johann
 * @version 1.0
 * @see
 **/
public class DebugTest {

    private static String name = "Johann";
    public int age;

    public static void main(String[] args) throws InterruptedException {
        //new DebugTest().age = 18;
        System.out.println(new DebugTest().age);
        suspendTest();
        Integer return_param = extracted(10,12);
        System.out.println(return_param);
    }

    private static Integer extracted(Integer cycle,Integer param) {
        if (param == null) {
            throw new NullPointerException("param is null");
        }
        int i = 0;
        int j = 0;
        for (int k = 0; k < cycle; k++) {
            i = i++;
            j = ++j;
        }
        System.out.println("i=" + i + " j=" + j);
        return ++param;
    }

    private static void suspendTest() throws InterruptedException {
        System.out.println("=====suspend勾选选项=======");
        //模拟两个线程
        new Thread(() -> threadDemo()).start();

        TimeUnit.SECONDS.sleep(10);
        new Thread(() -> threadDemo2()).start();

        TimeUnit.SECONDS.sleep(20);
        System.out.println("=====suspend勾选选项 Stop=======");
    }

    private static void threadDemo() {
        long id = Thread.currentThread().getId();
        System.out.printf(LocalDateTime.now()+": thread id [%d] is blocked \n", id);
        System.out.printf(LocalDateTime.now()+": thread id [%d] is Non-blocking \n", id);
    }
    private static void threadDemo2() {
        long id = Thread.currentThread().getId();
        System.out.printf(LocalDateTime.now()+": thread id [%d] is blocked \n", id);
        System.out.printf(LocalDateTime.now()+": thread id [%d] is Non-blocking \n", id);
    }


}
