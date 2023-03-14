package com.johann.designPattern.dahao.builderDemo;

public class ZyhBuilderDemo {

    public static void main(String[] args) {
        DataPlansDirector director = new DataPlansDirector();
        DataPlansBuilder builderA = new DataPlansBuilderA();
        DataPlansBuilder builderB = new DataPlansBuilderB();

        // 指挥者使用建造者A的方法来建造具体产品
        director.construct(builderA);
        DataPlans dataPlansA = builderA.build();
        dataPlansA.show();

        // 指挥者使用建造者B的方法来建造具体产品
        director.construct(builderB);
        DataPlans dataPlansB = builderB.build();
        dataPlansB.show();
    }

}
