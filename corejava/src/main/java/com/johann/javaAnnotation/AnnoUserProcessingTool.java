package com.johann.javaAnnotation;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** 注解处理类
 * @ClassName: AnnoUserProcessingTool
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class AnnoUserProcessingTool {

    /**
     * 处理字段中文名称标签，时间标签
     * @param obj
     * @return
     */
    public String processing(Object obj) {

        StringBuilder result = new StringBuilder();

        //获取Class
        Class clazz = obj.getClass();
        //获取所有定义的字段
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // @LabelZh 注解标注的字段
            if (field.isAnnotationPresent(LabelZh.class)){
                //放弃java安全检测，设置可以访问私有字段
                field.setAccessible(true);

                LabelZh labelZh = field.getAnnotation(LabelZh.class);

                String name = !"".equals(labelZh.value())?labelZh.value():field.getName();
                Object value = "";

                //时间类型字段的处理
                if (field.getType() == Date.class && field.isAnnotationPresent(DateFormatter.class)){
                    DateFormatter dateFormatter = field.getAnnotation(DateFormatter.class);
                    String pattern = dateFormatter.pattern();
                    String timeZone = dateFormatter.timeZone();
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
                    try {
                        value = sdf.format(field.get(obj));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        value = field.get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                result.append(name+":"+value+"\r\n");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        AnnoUserProcessingTool tool = new AnnoUserProcessingTool();
        AnnoUser user = new AnnoUser(11,"Johann",new Date());
        System.out.println(tool.processing(user));
    }
}
