package com.johann.z_newFeature;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;

/** Date与LocalDateTime相互转换
 * @ClassName: Date_DateTime_Demo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Date_DateTime_Demo {

    public static void main(String[] args) {
        //dateToLocalDateTime();
        localDateTimeToDate();
    }

    /**
     * Date 转 LocalDateTime，Instant作为中间量。
     *   首先，Date转Instant，Instant.ofEpochMilli(date.getTime())，
     *   然后，Instant转LocalDateTime，LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
     */
    public static void dateToLocalDateTime(){
        Date date = new Date();
        System.out.println("Date: "+date);
        // 返回此Date对象表示的自1970年1月1日00:00:00 GMT以来的毫秒数。
        long millisecond = date.getTime();
        // 使用1970-01-01T00:00:00Z时期的毫秒获取Instant实例。
        Instant instant = Instant.ofEpochMilli(millisecond);
        // 从即时和区域ID获取LocalDateTime的实例。这将基于指定的时间创建本地日期时间。
        // 首先，使用区域ID和瞬时获得UTC/Greenwich的偏移量，这很简单，因为每个瞬时只有一个有效的偏移量。
        // 然后，使用即时和偏移量计算本地日期时间。
        //LocalDateTime localDateTime= LocalDateTime.ofInstant(instant, ZoneId.of("UTC+09:30"));
        LocalDateTime localDateTime= LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println("1，从瞬时和时区ID获取LocalDateTime: "+localDateTime);

        // 将此即时与时区组合以创建ZonedDateTime
        //ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.println("2.1，Instant转ZonedDateTime: "+zonedDateTime);
        // 获取此日期时间的LocalDateTime部分
        LocalDateTime localDateTime1 = zonedDateTime.toLocalDateTime();
        System.out.println("2.2，ZonedDateTime转LocalDateTime: "+localDateTime1);
    }

    /**
     * LocalDateTime 转 Date
     * 两种方式：
     *   a.Instant作为中间量。localDateTime.toInstant(zoneOffset)，然后 Date date = Date.from(instant)。
     *   b.Timestamp作为中间量。Timestamp.valueOf(localDateTime)，然后 Date date = new Date(timestamp.getTime())。
     */
    public static void localDateTimeToDate(){
        // 获取[+09:00]时区当前时间
        //LocalDateTime dateTimePlus9 = LocalDateTime.now(ZoneId.of("UTC+09:00"));
        //System.out.println(dateTimePlus9);

        // ZoneOffset继承了ZoneId
        ZoneId zoneId = ZoneId.of("Z");
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        // 要想保证时间的一致性，获取LocalDateTime传入的时区需要与下面LocalDateTime转Instant传入的时区相同。
        LocalDateTime localDateTime = LocalDateTime.now(zoneOffset);
        System.out.println("LocalDateTime: "+localDateTime);

        // 这将本地日期时间和指定的偏移量组合在一起，形成“瞬时”。
        // LocalDateTime转Instant，有以下两种方法：
        Instant instant = localDateTime.atOffset(zoneOffset).toInstant();
        instant = localDateTime.toInstant(zoneOffset);
        // 从Instant对象获取Date的实例
        Date date = Date.from(instant);
        System.out.println("Instant转Date: "+date);

        // 从LocalDateTime对象获取Timestamp的实例
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Date date1 = new Date(timestamp.getTime());
        System.out.println(date1);
    }

}
