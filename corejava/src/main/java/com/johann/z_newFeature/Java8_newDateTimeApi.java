package com.johann.z_newFeature;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Set;

/** Java8的日期时间API
 * @ClassName: Java8_newDateTimeApi
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class Java8_newDateTimeApi {

    public static void main(String[] args) {
        //localDateDemo();
        //localTimeDemo();
        //dateTimeDemo();
        //instantDemo();
        //durationDemo();
        //periodDemo();
        //temporalAdjusterDemo();
        zoneIdDemo();
        //clockDemo();
    }
    /**
     * LocalDate：ISO-8601日历系统中没有时区的日期，如2007-12-03。
     */
    public static void localDateDemo(){
        // 1，获取当前日期
        // 从指定时区的系统时钟中获取当前日期
        LocalDate localDate = LocalDate.now();
        LocalDate localDateZone = LocalDate.now(ZoneId.of("UTC-12"));
        System.out.println("\n本地日期："+localDate+"; 指定时区日期："+localDateZone);
        // 之前的时间类，包含日期和时间
        Date oldDate = new Date();
        System.out.println("旧时间类日期："+oldDate);
        // 使用特定的格式化程序从文本字符串中获取LocalDate的实例
        System.out.println("从字符串中解析出日期："+LocalDate.parse("2022-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // 从文本字符串（如2007-12-03）获取LocalDate的实例。
        // parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("从字符串中解析出日期："+LocalDate.parse("2022-01-01"));

        // 2，获取年、月、日等信息
        System.out.println("\n年："+localDate.getYear());
        System.out.println("月："+localDate.getMonthValue());
        System.out.println("日："+localDate.getDayOfMonth());
        System.out.println("月份字段："+localDate.getMonth());
        System.out.println("一年中的第几天："+localDate.getDayOfYear());
        System.out.println("一周中的第几天："+localDate.getDayOfWeek());
        System.out.println("ISO-8601日历系统："+localDate.getChronology().dateNow());
        System.out.println("是否闰年："+localDate.isLeapYear());

        // 3，获取指定的日期时间，所见即所得
        System.out.println("\n指定年月日的日期："+LocalDate.of(2022,2,2));
        System.out.println("指定距1970-01-01特定天数的日期："+LocalDate.ofEpochDay(10000));
        System.out.println("指定年份中特定天数的日期："+LocalDate.ofYearDay(2022,15));
        System.out.println("当前日期距1970-01-01多少天："+localDate.toEpochDay());

        // 4，判断两个日期是否相同
        System.out.println("\n两个日期是否相同："+LocalDate.ofYearDay(2022,31).equals(localDate));

        // 5，判断法定假日，生日等周期性日期
        // YearMonth的时间格式是：2022-02; MonthDay的时间格式是：--02-01
         LocalDate birthDate = LocalDate.of(1949,10,1);
         MonthDay birthDay = MonthDay.from(birthDate);
         MonthDay labourDay = MonthDay.of(5,1);
         // 判断当前时间，是否是国庆节或劳动节
        MonthDay localMonthday = MonthDay.from(localDate);
        System.out.print("\n"+localDate);
        if (birthDay.equals(localMonthday)) {
            System.out.println(" 今天是国庆节");
        } else if (labourDay.equals(localMonthday)) {
            System.out.println(" 今天是劳动节");
        } else {
            System.out.println(" 今天既不是国庆节,也不是劳动节");
        }

        // 6，对日期进行加减操作
        System.out.println("\n对当前日期+2天："+localDate.plusDays(2));
        System.out.println("对当前日期+1星期："+localDate.plus(1,ChronoUnit.WEEKS));
        System.out.println("对当前日期-1月："+localDate.minusMonths(1));

        // 7，返回此日期的副本，其中指定字段[年/月/日]设置为新值。
        System.out.println("\n设定当前日期的月："+localDate.with(ChronoField.MONTH_OF_YEAR,3));
        System.out.println("设定当前日期的天："+localDate.withDayOfYear(32));

        // 8，将此日期与偏移时间组合以创建OffsetDateTime
        System.out.println("\n日期与偏移时间组成OffsetDateTime："+localDate.atTime(OffsetTime.of(LocalTime.now(),ZoneOffset.of("+09:30"))));
        // 将此日期与时间组合以创建LocalDateTime
        System.out.println("\n日期与时间组成LocalDateTime："+localDate.atTime(LocalTime.now()));
    }

    /**
     * LocalTime：ISO-8601日历系统中没有时区的时间，如10:15:30
     */
    public static void localTimeDemo(){
        // 1，获取时间
        // 获取指定时区的当前时间
        LocalTime localTime = LocalTime.now();
        LocalTime localTimeZone = LocalTime.now(ZoneId.of("UTC+3"));
        System.out.println("\n本地时间: "+localTime+"; 指定时区的时间: "+localTimeZone);
        // 使用特定的格式化程序从文本字符串中获取LocalTime的实例
        System.out.println("从字符串中解析出时间："+LocalTime.parse("12:12:12",DateTimeFormatter.ofPattern("HH:mm:ss")));

        System.out.println("\n给时间添加上日期："+localTime.atDate(LocalDate.now()));
        System.out.println("指定字段有效值范围："+localTime.range(ChronoField.MINUTE_OF_DAY));

        // 2，对时间进行加减,并返回当前时间的副本
        System.out.println("\n对当前时间+2小时："+localTime.plusHours(2));
        System.out.println("对当前时间+40分钟："+localTime.plus(40, ChronoUnit.MINUTES));
        System.out.println("对当前时间-4小时："+localTime.minusHours(4));

        // 3，获取指定的日期时间，所见即所得
        System.out.println("\n指定时分秒的时间："+LocalTime.of(15,15,15));

        // 4，判断两个时间是否相同
        System.out.println("\n两个时间是否相同："+localTime.equals(LocalTime.of(15,15,15)));

        // 5，返回更改了一天中的[时/分/秒]的此LocalTime的副本。
        System.out.println("\n设定当前时间的小时："+localTime.withHour(12));
        System.out.println("设定当前时间的分钟："+localTime.with(ChronoField.MINUTE_OF_HOUR,55));

        // 6，将此时间与偏移组合以创建偏移时间。
        // 这将返回从此时起以指定偏移量形成的OffsetTime。时间和偏移的所有可能组合都有效。
        // OffsetTime：在ISO-8601日历系统中与UTC/格林威治有偏移的时间，例如10:15:30+01:00。
        OffsetTime offsetTime = localTime.atOffset(ZoneOffset.of("+09:30"));
        System.out.println("\n当前时间与时区偏移量的组合："+offsetTime+" --- "+offsetTime.getOffset());
    }

    /**
     * LocalDateTime：ISO-8601日历系统中没有时区的日期时间，如2007-12-03T10:15:30。
     * ZonedDateTime：ISO-8601日历系统中带有时区的日期时间，例如2007-12-03T10:15:30+01:00欧洲/巴黎。
     * OffsetDateTime：ISO-8601日历系统中与UTC/Greenwich有偏移的日期时间，例如2007-12-03T10:15:30+01:00。
     */
    public static void dateTimeDemo(){
        // 1，获取当前日期时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("\n无时区的日期时间："+localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("包含时区的日期时间："+zonedDateTime);
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        System.out.println("包含UTC偏移量的日期时间："+offsetDateTime);

        // 2，日期时间格式化
        System.out.println("\n日期时间格式化："+localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 3，日期时间截断
        System.out.println("\n日期时间截断："+localDateTime.truncatedTo(ChronoUnit.DAYS));

        // 4，返回对日期时间的指定单位进行加减操作后的副本
        System.out.println("\n当前日期时间30天以后："+localDateTime.plus(30,ChronoUnit.DAYS));
        System.out.println("当前日期时间1星期后："+localDateTime.plusWeeks(1));

        // 5，返回此日期时间的副本，其中指定字段设置为新值。
        System.out.println("\n对日期时间的指定单位设置新值后的副本："+localDateTime.with(ChronoField.DAY_OF_MONTH,22));

        // 6，对localDateTime 添加偏移量和时区标识
        // 将此日期时间与偏移量组合以创建OffsetDateTime。
        offsetDateTime = localDateTime.atOffset(ZoneOffset.of("+09:30"));
        System.out.println("\n对日期时间添加偏移量标识："+offsetDateTime);
        // 将此日期时间与时区组合以创建ZonedDateTime。
        zonedDateTime = localDateTime.atZone(ZoneId.of("UTC+08:30"));
        System.out.println("对日期时间添加时区标识："+zonedDateTime);
    }

    /**
     * ZoneId：时区ID，如欧洲/巴黎。 ZoneId用于标识用于在Instant和LocalDateTime之间转换的规则。
     * 有两种不同类型的ID：
     *   a.固定偏移量-从UTC/Greenwich完全解析的偏移量，对所有本地日期时间使用相同的偏移量
     *   b.地理区域-适用于查找UTC/Greenwich偏移量的特定规则集的区域
     * 大多数固定偏移由ZoneOffset表示。对任何ZoneId调用normalized() 将确保固定偏移ID将表示为ZoneOffset。
     *
     * ZoneOffset：与格林威治/UTC的时区偏移，例如+02:00。
     * 时区偏移量是指时区与格林威治/UTC不同的时间量。这通常是固定的小时和分钟数。
     * 世界上不同的地区有不同的时区偏移。在ZoneId类中捕获了偏移量随时间和地点变化的规则。
     */
    public static void zoneIdDemo(){
        Set<String> zoneSet = ZoneId.getAvailableZoneIds();
        zoneSet.forEach(System.out::println);

        LocalDateTime localDateTime = LocalDateTime.now();
        // ZoneId.of("") 的参数格式可以是 【Z、UTC±hh:mm、UT±hh:mm、GMT±hh:mm、地理区域(Asia/Shanghai)】
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC+10:30"));
        System.out.println(zonedDateTime);
        // ZoneOffset.of("") 的参数格式只能是 【Z、±hh:mm:ss】
        OffsetDateTime offsetDateTime = localDateTime.atOffset(ZoneOffset.of("+09:30"));
        System.out.println(offsetDateTime);

        LocalDate localDate = LocalDate.now();
        ZonedDateTime zonedDateTime2 = localDate.atStartOfDay(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime2);
    }

    /**
     * Instant：时间线上的瞬时点
     */
    public static void instantDemo(){
        // 1，获取Instant实例
        // 查询系统UTC时钟以获取当前时刻
        Instant instantUtc = Instant.now();
        System.out.println("\n根据系统时钟获取时刻："+instantUtc);
        // 查询指定的时钟以获取当前时刻
        System.out.println("根据指定时钟获取时刻："+Instant.now(Clock.systemDefaultZone()));
        //LocalDateTime.now().isSupported(ChronoField.INSTANT_SECONDS)
        System.out.println("Instant.from()，不支持从LocalDate，LocalTime，LocalDateTime中获取Instant的实例");
        // 从文本字符串（如2007-12-03T10:15:30.00Z）获取Instant的实例
        System.out.println("根据文本字符串获取时刻："+Instant.parse("2022-02-03T15:11:39.025Z"));
        // 使用1970-01-01T00:00:00Z时期的毫秒获取Instant实例。
        System.out.println("根据时间戳(毫秒数)获取时刻："+Instant.ofEpochMilli(instantUtc.toEpochMilli()));
        // 使用1970-01-01T00:00:00Z时期的秒获取Instant实例。
        System.out.println("根据时间戳(秒数)获取时刻："+Instant.ofEpochSecond(instantUtc.getEpochSecond()));
        // 使用1970-01-01T00:00:00Z纪元的秒数和纳秒级的秒数获取Instant实例。
        System.out.println("根据时间戳(秒数)和指定的纳秒数获取时刻："+Instant.ofEpochSecond(instantUtc.getEpochSecond(),999999999));

        // 2，检查是否支持指定的字段。这将检查是否可以针对指定字段查询此即时
        // 支持以下字段：true ChronoField.{NANO_OF_SECOND,MICRO_OF_SECOND,MILLI_OF_SECOND,INSTANT_SECONDS}
        System.out.println("\n检查是否支持指定的字段："+instantUtc.isSupported(ChronoField.INSTANT_SECONDS));

        // 3，检查是否支持指定的单位。这将检查指定的单位是否可以添加到此日期时间或从中减去
        // 支持以下字段：ChronoUnit.{NANOS,MICROS,MILLIS,SECONDS,MINUTESHOURS,HALF_DAYS,DAYS}
        System.out.println("\n检查是否支持指定的单位："+instantUtc.isSupported(ChronoUnit.MICROS));

        // 4，瞬时与时区的组合
        // 将此瞬间与偏移量组合以创建OffsetDateTime。
        System.out.println("\n瞬时与时区偏移量组合："+instantUtc.atOffset(ZoneOffset.of("+08:30")));
        // 将此即时与时区组合以创建ZonedDateTime。
        System.out.println("瞬时与时区组合："+instantUtc.atZone(ZoneId.of("UTC+8")));
        System.out.println("瞬时与时区组合："+instantUtc.atZone(ZoneId.of("Asia/Tokyo")));

        // 5，返回截断为指定单位的此Instant的副本。
        System.out.println("\n瞬时被截断为指定单位的副本："+instantUtc.truncatedTo(ChronoUnit.DAYS));

        // 6，对瞬时进行加减
        System.out.println("\n对瞬时进行加减操作："+instantUtc.plus(20,ChronoUnit.MINUTES));
        System.out.println("对瞬时进行加减操作："+instantUtc.minus(20,ChronoUnit.MINUTES));

        // 7，返回指定字段设置为新值的此瞬时的副本。
        // 支持以下字段：true ChronoField.{NANO_OF_SECOND,MICRO_OF_SECOND,MILLI_OF_SECOND,INSTANT_SECONDS}
        System.out.println("\n指定字段设置为新值的瞬时副本："+instantUtc.with(ChronoField.INSTANT_SECONDS,15));
    }

    /**
     * Duration：持续时间，该类以秒和纳秒为单位对时间量进行建模。可以使用其他基于持续时间的单位（如分钟和小时）访问它。
     */
    public static void durationDemo(){
        // 1，获取持续时间
        // 获取以指定单位表示时间量的持续时间。
        Duration duration = Duration.of(65,ChronoUnit.MINUTES);
        System.out.println("\n获取时间时间："+duration);
        // 获取表示秒数的持续时间和以纳秒为单位的调整。
        System.out.println("获取持续时间："+Duration.ofSeconds(3700,888888888));
        // 从时间量中获取持续时间的实例。
        // 这将根据指定的时间量获得持续时间。TemporalAmount表示一个时间量，它可以是基于日期的，也可以是基于时间的，该工厂将其提取为一个持续时间
        System.out.println("获取持续时间："+Duration.from(ChronoUnit.DAYS.getDuration()));
        // 从文本字符串（如PnDTnHnMn.nS）中获取持续时间
        System.out.println("文本解析，获取持续时间："+Duration.parse("PT59H3M9.777777777S"));
        // 获取表示两个时间对象之间的持续时间的持续时间。
        // 如果结束在开始之前，则此方法的结果可能是负周期。要保证获得正持续时间，请对结果调用abs（）
        Duration durationBetween = Duration.between(LocalTime.of(2,12,12),LocalTime.of(3,14,14));
        System.out.println("获取两个时间对象之间的持续时间："+durationBetween);

        // 2，获取此持续时间支持的单位集。
        System.out.println("\n此持续时间支持的单位集："+durationBetween.getUnits());

        // 3，返回加上、减去指定持续时间的此持续时间的副本
        System.out.println("\n持续时间加减："+duration.minus(durationBetween));
        System.out.println("持续时间加减："+duration.minus(15,ChronoUnit.MINUTES));

        // 4，负的持续时间
        Duration negatedDuration = Duration.ofSeconds(-650);
        System.out.println("\n负的持续时间："+negatedDuration);
        System.out.println("是否是负持续时间："+negatedDuration.isNegative());
        System.out.println("负持续时间转正持续时间（绝对值）："+negatedDuration.abs());

        // 5，零长度持续时间
        System.out.println("\n零长度持续时间："+Duration.ZERO);
        System.out.println("是否是零长度持续时间："+Duration.ZERO.isZero());

        // 6，返回具有指定秒数的此持续时间的副本
        System.out.println("\n指定秒数的持续时间副本："+duration.withSeconds(650));
    }

    /**
     * Period：持续时间，该类以年、月和日为单位模拟时间量。
     */
    public static void periodDemo(){
        // 1，获取表示年、月和日数的周期
        Period period = Period.of(1,1,1);
        System.out.println("\n获取周期："+period);
        // 生成的期间将具有指定的天数。年和月单位将为零。
        System.out.println("获取周期："+Period.ofDays(366));
        // 从文本字符串（如PnYnMnD）中获取Period
        Period parsePeriod = Period.parse("P1M4W5D");
        System.out.println("文本解析，获取周期："+parsePeriod);
        // 获取由两个日期之间的年数、月数和日数组成的周期
        Period periodBetween = Period.between(LocalDate.of(2022,1,12),LocalDate.of(2022,3,3));
        System.out.println("两个日期之间的周期："+periodBetween);

        System.out.println("\n周期的年数、月数、日数："+parsePeriod.getYears()+"、"+parsePeriod.getMonths()+"、"+parsePeriod.getDays());

        // 2，获取此周期支持的单位集。
        System.out.println("\n周期支持的单位集："+parsePeriod.getUnits());

        // 3，返回添加了指定期间的此周期的副本
        System.out.println("\n周期加减："+period.plus(parsePeriod));
        System.out.println("周期加减："+period.minusMonths(2));

        // 4，负周期，零周期
        // 检查此周期的三个单位中是否有任何一个为负。
        System.out.println("\n负周期："+period.minusMonths(2).isNegative());
        // 返回一个新实例，此周期的每个单位都被求反。
        System.out.println("负周期求反："+period.minusMonths(2).negated());
        System.out.println("零周期："+Period.ZERO.isZero());

        // 返回具有指定单位长度的此周期的副本。
        System.out.println("\n指定月数的周期副本："+period.withMonths(2));
    }

    /**
     * TemporalAdjuster：函数式接口，调整时间对象的策略。
     * TemporalAdjusters：常见且有用的临时调节器。调整器是修改时间对象的关键工具。
     * 它们的存在是为了将调整过程具体化，根据战略设计模式允许不同的方法。例如，可以设置避开周末的日期的调整器，或者将日期设置为当月最后一天的调整器。
     */
    public static void temporalAdjusterDemo(){
        LocalDate localDate = LocalDate.of(2023,2,13);

        // 返回当前年份的第一天的新日期
        LocalDate firstDayOfYear = localDate.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("\n当前年份的第一天："+firstDayOfYear);
        // 返回当前月份第一天的新日期
        LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("\n当前月份第一天："+firstDayOfMonth);
        // 返回下个月第一天的新日期
        LocalDate firstDayOfNextMonth = localDate.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("\n下个月第一天："+firstDayOfNextMonth);
        // 返回下一年第一天的新日期
        LocalDate firstDayOfNextYear = localDate.with(TemporalAdjusters.firstDayOfNextYear());
        System.out.println("\n下一年第一天："+firstDayOfNextYear);
        // 返回当前月份最后一天的新日期
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("\n当前月份最后一天："+lastDayOfMonth);
        // 返回当前年份最后一天的新日期
        LocalDate lastDayOfYear = localDate.with(TemporalAdjusters.lastDayOfYear());
        System.out.println("\n当前年份最后一天："+lastDayOfYear);
        // 返回当前月的第一个星期一
        LocalDate firstInMonth = localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println("\n当前月的第一个星期一："+firstInMonth);
        // 返回当前月的最后一个星期一
        LocalDate lastInMonth = localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
        System.out.println("\n当前月的最后一个星期一："+lastInMonth);
        // 返回当前日期之后的下一个星期一
        LocalDate next = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("\n当前日期之后的下一个星期一："+next);
        // 返回当前日期之后的下一个星期一（如果当前日期是星期一，则返回当前日期）
        LocalDate nextOrSame = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        System.out.println("\n当前日期之后的下一个星期一（如果当前日期是星期一，则返回当前日期）："+nextOrSame);
        // 返回当前日期之前的上一个星期一
        LocalDate previous = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println("\n当前日期之前的上一个星期一："+previous);
        // 返回当前日期之前的上一个星期一（如果当前日期是星期一，则返回当前日期）
        LocalDate previousOrSame = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println("\n当前日期之前的上一个星期一（如果当前日期是星期一，则返回当前日期）："+previousOrSame);
        // 返回当前月的第二个星期一
        LocalDate dayOfWeekInMonth = localDate.with(TemporalAdjusters.dayOfWeekInMonth(2,DayOfWeek.MONDAY));
        System.out.println("\n当前月的第二个星期一："+dayOfWeekInMonth);
        // 返回当前月的倒数第二个星期一
        LocalDate dayOfWeekInMonth1 = localDate.with(TemporalAdjusters.dayOfWeekInMonth(-2,DayOfWeek.MONDAY));
        System.out.println("\n当前月的倒数第二个星期一："+dayOfWeekInMonth1);

        // 自定义日期调节器，发薪日
        LocalDate d = LocalDate.of(2022,5,12);
        LocalDate salaryDate = d.with(SALARY_DATE);
        System.out.println("\n当前日期为"+d+"，其下一个发薪日是："+salaryDate+"["+salaryDate.getDayOfWeek()+"]");
    }
    /**
     * 自定义日期调节器
     * 当前日期的下一个发薪日：
     *   a.如果当前日期超过15号，则计算下个月的发薪日，否则计算本月发薪日；
     *   b.如果发薪日(15号)处于周末，则以15号的前一个周五为发薪日。
     */
    static TemporalAdjuster SALARY_DATE = TemporalAdjusters.ofDateAdjuster(localDate1 -> {
        if (localDate1.getDayOfMonth() > 15){
            localDate1 = localDate1.plusMonths(1);
        }
        LocalDate salaryDate = localDate1.withDayOfMonth(15);
        if (DayOfWeek.SATURDAY.equals(salaryDate.getDayOfWeek())||DayOfWeek.SUNDAY.equals(salaryDate.getDayOfWeek())){
            salaryDate = salaryDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
        }
        return salaryDate;
    });

    /**
     * Clock：使用时区访问当前时刻、日期和时间的时钟
     * 可以使用时钟代替 System.currentTimeMillis() 和 TimeZone.getDefault()
     */
    public static void clockDemo(){

        // 1，获取时钟
        // 1.1，systemDefaultZone：获取一个<使用最佳可用系统时钟返回当前时刻>的时钟，并使用默认时区转换为日期和时间。
        Clock clockDefaultZone = Clock.systemDefaultZone();
        System.out.println("\nclockDefaultZone："+clockDefaultZone);
        //SystemClock[Asia/Shanghai]
        System.out.println("【默认时钟】"+clockDefaultZone.instant().atZone(clockDefaultZone.getZone()));
        // 1.2，system：获取一个<使用最佳可用系统时钟返回当前时刻>的时钟，并使用指定时区转换为日期和时间。
        Clock clockSetZone = Clock.system(ZoneId.of("UTC+9"));
        System.out.println("clockSetZone："+clockSetZone);
        //SystemClock[UTC+09:00]
        System.out.println("【默认时钟】"+clockSetZone.instant().atZone(clockSetZone.getZone()));
        // 1.3，systemUTC：获取一个<使用最佳可用系统时钟返回当前时刻>的时钟，并使用UTC时区转换为日期和时间。（UTC不属于任何时区）
        Clock clockSystemUtc = Clock.systemUTC();
        System.out.println("clockSystemUtc："+clockSystemUtc);
        //SystemClock[Z]
        System.out.println("【默认时钟】"+clockSystemUtc.instant().atZone(clockSystemUtc.getZone()));

        // 2，millis：返回从1970-01-01T00:00Z（UTC）测量的基于毫秒的瞬间。这相当于System.currentTimeMillis()
        System.out.println("\nmillis："+clockDefaultZone.millis());

        // 3，instant：返回时钟的当前时刻
        System.out.println("\n时钟当前时刻："+clockDefaultZone.instant());
        System.out.println("时钟当前时刻（瞬时与时区偏移量组合）："+clockDefaultZone.instant().atOffset(ZoneOffset.of("+08:30")));
        System.out.println("时钟当前时刻（瞬时与时区组合）："+clockDefaultZone.instant().atZone(ZoneId.of("UTC+8")));
        System.out.println("时钟当前时刻（瞬时与时区组合）："+clockDefaultZone.instant().atZone(ZoneId.of("Asia/Tokyo")));

        // 4，offset：偏移时钟。获取一个时钟，该时钟从添加了指定持续时间的指定时钟返回瞬间。
        // 该时钟包装另一个时钟，返回延迟指定持续时间的瞬间。如果持续时间为负，则瞬间将早于当前日期和时间。
        System.out.println("\n【偏移时钟】返回添加指定持续时间的时钟(-2小时)："+Clock.offset(clockDefaultZone,Duration.ofHours(-2)).instant());;
        System.out.println("【偏移时钟】返回添加指定持续时间的时钟(+2小时)："+Clock.offset(clockDefaultZone,Duration.ofHours(2)).instant());;
        System.out.println("【偏移时钟】返回添加指定持续时间的时钟(零持续时间)："+Clock.offset(clockDefaultZone,Duration.ZERO).instant());;

        // 5，tick：滴答时钟。该方法返回一个时钟，该时钟返回从指定时钟截断到指定持续时间最近出现的时刻。
        // 创建一个指定间隔持续时间的滴答时钟，只有在 {当前时刻 > (上一滴答时钟时刻+持续时间)} 时，滴答时钟的时刻才会变化
        Clock tickClock = Clock.tick(clockDefaultZone,Duration.ofSeconds(3));
        // 间隔持续时间为 1分钟，1秒的滴答时钟
        Clock.tickMinutes(ZoneId.systemDefault());
        Clock.tickSeconds(ZoneId.systemDefault());
        System.out.println("\n创建滴答时钟");
        for (int i = 0;i < 4; i++){
            System.out.println("=========="+i+"============");
            System.out.println("clockDefaultZone: "+clockDefaultZone.instant());
            System.out.println("【滴答时钟】tickClock: "+tickClock.instant());
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 6，fixed：固定时钟。获取始终返回同一时刻的时钟。
        // 这个时钟只返回指定的时刻。因此，它不是传统意义上的时钟。这方面的主要用例是在测试中，固定时钟确保测试不依赖于当前时钟。
        Clock fixedClock = Clock.fixed(clockDefaultZone.instant(),ZoneId.systemDefault());
        System.out.println("\n【固定时钟】："+fixedClock.instant());
        System.out.println("【固定时钟】："+fixedClock.instant());
        System.out.println("【固定时钟】："+fixedClock.instant());

        // 7，withZone：返回具有不同时区的时钟副本
        Clock clockWithZone = clockDefaultZone.withZone(ZoneId.of("Asia/Tokyo"));
        System.out.println("\n不同时区的时钟副本："+clockWithZone);
        System.out.println("时钟副本获取的UTC瞬时："+clockWithZone.instant());
    }
}
