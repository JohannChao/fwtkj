package com.johann.javaAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** APT工具Demo
 *  当我们生成一个自定义注解时，需要提供相应的代码来提取并处理注解信息，这些处理注解的代码统称为 APT 工具
 * @ClassName: zAnnotationProcessingTool
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Z_AnnotationProcessingTool {

    public static void jiancha(Class clazz){
        StringBuilder result = new StringBuilder();
        StringBuilder error = new StringBuilder();
        int eNum = 0;
        int jianchaNum = 0;
        /**
         * 获取类中所有的方法
         */
        Method[] ms = clazz.getDeclaredMethods();
        for (Method m : ms){
            /**
             * 如果方法带 @JianCha 注解，则APT会对这个方法进行处理
             */
            if (m.isAnnotationPresent(JianCha.class)){
                try {
                    jianchaNum++;
                    m.setAccessible(true);
                    m.invoke(clazz.newInstance(),null);
                } catch (IllegalAccessException e) {
                    //e.printStackTrace();
                    eNum++;
                    error.append(m.getName()+"方法存在异常 : "+e.getCause().getClass().getSimpleName()+" "+e.getCause().getMessage());
                    error.append("\r\n");
                } catch (InvocationTargetException e) {
                    //e.printStackTrace();
                    eNum++;
                    error.append(m.getName()+"方法存在异常 : "+e.getCause().getClass().getSimpleName()+" "+e.getCause().getMessage());
                    error.append("\r\n");
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        }
        result.append(clazz.getSimpleName()+"检查结果如下：");
        result.append("\r\n");
        result.append("共有 "+ms.length+" 个方法，其中 "+jianchaNum+" 个方法需要检查，存在异常的方法有 "+eNum+" 个。");
        result.append("\r\n");
        result.append(error);
        System.out.println(result.toString());
    }

    public static void main(String[] args) {
        jiancha(Z_JianchaClass.class);
    }

}
