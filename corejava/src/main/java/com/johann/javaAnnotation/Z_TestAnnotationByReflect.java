package com.johann.javaAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.Arrays;

/** 反射获取，操作注解
 * @ClassName: TestAnnotationByReflect
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Z_TestAnnotationByReflect {

    public static void main(String[] args) throws ClassNotFoundException{
        Class clazz_TestAnnotationI = TestAnnotationI.class;
        /** 判断当前类是否应用了指定的注解 **/
        //true
        System.out.println("clazz_TestAnnotationI.isAnnotationPresent(AnnotationTest.class) : "+clazz_TestAnnotationI.isAnnotationPresent(AnnotationTest.class));

        Class clazz_TestAnnotation = Class.forName("com.johann.javaAnnotation.TestAnnotation");
        /** 判断当前类是否是注解类型 **/
        //false
        System.out.println("clazz_TestAnnotation.isAnnotation() : "+clazz_TestAnnotation.isAnnotation());
        /** 判断当前类是否应用了指定的注解
         *  @Inherited 标注的注解A，如果一个接口I 使用了注解A，这个接口的实现类 不会继承接口的注解
         * **/
        //false
        System.out.println("clazz_TestAnnotation.isAnnotationPresent(AnnotationTest.class) : "+clazz_TestAnnotation.isAnnotationPresent(AnnotationTest.class));

        Class clazz_TestRepeatableSon = TestRepeatableSon.class;
        /** 判断当前类是否应用了指定的注解
         *  @Inherited 标注的注解A，如果一个父类F 使用了注解A，这个父类的子类S 会继承父类的注解
         * **/
        //true
        System.out.println("clazz_TestRepeatableSon.isAnnotationPresent(AnnotationTest.class) : "+clazz_TestRepeatableSon.isAnnotationPresent(AnnotationTest.class));
        //true
        System.out.println("clazz_TestRepeatableSon.isAnnotationPresent(StateArray.class) : "+clazz_TestRepeatableSon.isAnnotationPresent(StateArray.class));
        //false
        System.out.println("clazz_TestRepeatableSon.isAnnotationPresent(State.class) : "+clazz_TestRepeatableSon.isAnnotationPresent(State.class));





        /**
         * 获取注解
         */
        System.out.println("\n&&&&&&&&& 获取注解 &&&&&&&&&&");
        AnnotatedType[] annotatedInterfaces = clazz_TestRepeatableSon.getAnnotatedInterfaces();
        System.out.println("\n-----getAnnotatedInterfaces------");
        for (AnnotatedType annotatedInterface : annotatedInterfaces) {
            System.out.println(annotatedInterface.getType().getTypeName());
        }
        System.out.println("-----getAnnotatedInterfaces------\n");

        AnnotatedType annotatedSuperclass = clazz_TestRepeatableSon.getAnnotatedSuperclass();
        System.out.println("getAnnotatedSuperclass : "+annotatedSuperclass.getType().getTypeName());

        Annotation[] annotations = clazz_TestRepeatableSon.getAnnotations();
        System.out.println("\n-----getAnnotations------");
        for (Annotation annotation : annotations) {
            System.out.println(annotation.annotationType().getName());
        }
        System.out.println("-----getAnnotations------\n");

        Annotation[] declaredAnnotations =  clazz_TestRepeatableSon.getDeclaredAnnotations();
        System.out.println("\n-----getDeclaredAnnotations------");
        for (Annotation annotation : declaredAnnotations) {
            System.out.println(annotation.annotationType().getName());
        }
        System.out.println("-----getDeclaredAnnotations------\n");

        boolean annotationTestFlag = clazz_TestRepeatableSon.isAnnotationPresent(AnnotationTest.class);
        System.out.println("AnnotationTest.class flag : "+annotationTestFlag);
        if(annotationTestFlag){
            /**
             * 根据指定的注解类型，获取当前 Type 中的该注解类型
             */
            AnnotationTest a1 = (AnnotationTest) clazz_TestRepeatableSon.getAnnotation(AnnotationTest.class);
            System.out.println("子类的注解属性值覆盖了父类的注解属性值，a1 "+a1.id()+" "+a1.name());
            AnnotationTest a2 = (AnnotationTest) clazz_TestRepeatableSon.getDeclaredAnnotation(AnnotationTest.class);

            AnnotationTest[] a3 = (AnnotationTest[]) clazz_TestRepeatableSon.getAnnotationsByType(AnnotationTest.class);
            AnnotationTest[] a4 = (AnnotationTest[]) clazz_TestRepeatableSon.getDeclaredAnnotationsByType(AnnotationTest.class);
        }


        /**
         * 获取 @Repeatable 注解
         */
        System.out.println("\n&&&&&&&& 获取 @Repeatable 注解 &&&&&&&&&&");

        /**
         * @Repeatable 元注解标注的注解，是间接存在的
         *
         * 1，我们在 某个Type（包含类，接口，注解类型）上使用 @Repeatable 元注解标注的注解 @State：
         *   @State(stateValue = "S3")
         *   @State(stateValue = "S4")
         *
         * 2，当编译完成后，在Class文件中，实际上是以这种形式存在的： @StateArray({@State(stateValue = "S3"), @State(stateValue = "S4")})
         *
         */
        boolean stateFlag = clazz_TestRepeatableSon.isAnnotationPresent(State.class);
        //false
        System.out.println("State.class flag : "+stateFlag);
        boolean stateArrayFlag = clazz_TestRepeatableSon.isAnnotationPresent(StateArray.class);
        //true
        System.out.println("StateArray.class flag : "+stateArrayFlag);

        /**
         * 通过直接存在的注解，获取间接存在的注解
         */
        StateArray stateArray = (StateArray) clazz_TestRepeatableSon.getDeclaredAnnotation(StateArray.class);
        State[] states = stateArray.value();
        for (State s : states){
            System.out.println(s.stateValue());
        }


        /**
         * 直接获取间接存在的注解
         */
        State[] s1 = (State[]) clazz_TestRepeatableSon.getAnnotationsByType(State.class);
        for (State s : s1){
            System.out.println(s.stateValue());
        }

        /**
         * 直接获取间接存在的注解
         */
        Arrays.asList(clazz_TestRepeatableSon.getDeclaredAnnotationsByType(State.class)).forEach(action ->{
            if (action instanceof State){
                System.out.println(((State) action).stateValue());
            }
        });
    }

}
