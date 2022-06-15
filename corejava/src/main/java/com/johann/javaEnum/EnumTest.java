package com.johann.javaEnum;

import java.util.Arrays;

/**
 * @ClassName: EnumTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class EnumTest {
    /**
     * 包含抽象方法的枚举类
     */
    enum Color
    {
        RED{
            @Override
            String getConstantValue() {
                return "红色";
            }
        },
        GREEN{
            @Override
            String getConstantValue() {
                return "绿色";
            }
        },
        BLUE{
            @Override
            String getConstantValue() {
                return "蓝色";
            }
        };

        abstract String getConstantValue();
    }

    public static void main(String[] args) {

        Arrays.asList(Color.values()).forEach(action->{
            System.out.println("color: "+action.getConstantValue()+" at index of "+action.ordinal());
        });
        System.out.println(Color.RED);
        System.out.println(Color.valueOf("GREEN"));
        System.out.println(Enum.valueOf(Color.class,"BLUE"));


        for (SeasonEnum season : SeasonEnum.values()) {
            System.out.print("index : "+season.ordinal());
            System.out.println(" name : "+season.getName()+" solarTerm : "+season.getSolarTerm());
            System.out.println(season+" : "+season.getMonth());
            System.out.println("枚举类中单独实现： "+season.getMonth2());
        }
        System.out.println(SeasonEnum.SPRING);
        System.out.println(SeasonEnum.valueOf("SUMMER"));
        System.out.println(Enum.valueOf(SeasonEnum.class,"SUMMER"));


        /**
         * 枚举使用Switch
         */
        SeasonEnum seasonEnum = SeasonEnum.valueOf("SUMMER");
        switch (seasonEnum){
            case SPRING:
                System.out.println("这是春天，节气有: "+seasonEnum.getSolarTerm());
                break;
            case SUMMER:
                System.out.println("这是夏天，节气有: "+seasonEnum.getSolarTerm());
                break;
            case FALL:
                System.out.println("这是秋天，节气有: "+seasonEnum.getSolarTerm());
                break;
            case WINTER:
                System.out.println("这是冬天，节气有: "+seasonEnum.getSolarTerm());
                break;
            default:
                System.out.println("枚举不存在");
                break;
        }
    }
}
