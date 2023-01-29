package com.johann.z_newFeature;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/** 分别使用 SimpleDateFormat 和 DateTimeFormatter 格式化时间
 * @ClassName: DateFormatTest
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class DateFormatTest {

    /**
     * 使用SimpleDateFormat格式化时间，存在多线程安全问题。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void sdfParseDate() throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> results = new ArrayList<>();
        // 子任务，用于格式化时间格式
        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                // 多线程情形下，使用 SimpleDateFormat 格式化时间，报错
                //return sdf.parse("2020-06-05");

                // 使用被ThreadLocal锁上的SimpleDateFormat来格式化时间
                return DateFormatThreadLocal.parse("2020-06-05");
            }
        };

        for (int i=0;i<10;i++){
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            Date d = future.get();
            System.out.println(d);
        }

        pool.shutdownNow();
    }

    /**
     * 使用Java8提供的新日期时间API格式化时间
     */
    public static void dtfParseDate() throws ExecutionException, InterruptedException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<LocalDate>> results = new ArrayList<>();
        // 子任务，用于格式化时间格式
        Callable<LocalDate> task = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                //使用Java 8中这套全新的日期时间API之后，就没有什么多线程安全问题了，因为你不管做什么样的改变，
                //它都会给你产生一个全新的实例，所以说它是线程安全的，这样就解决了多线程的安全问题。
                return LocalDate.parse("2022-06-05",dtf);
            }
        };

        for (int i=0;i<10;i++){
            results.add(pool.submit(task));
        }

        for (Future<LocalDate> future : results) {
            LocalDate d = future.get();
            System.out.println(d);
        }

        pool.shutdownNow();

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        sdfParseDate();
        dtfParseDate();
    }
}

/**
 * 使用ThreadLocal类对以上程序中的SimpleDateFormat变量上锁，通过上锁解决了传统日期时间格式化的多线程安全问题
 */
class DateFormatThreadLocal{
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        //ThreadLocal里面有一个供子类继承的方法，即initialValue()
        protected DateFormat initialValue(){
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static Date parse(String dateStr) throws ParseException {
        return df.get().parse(dateStr);
    }
}
