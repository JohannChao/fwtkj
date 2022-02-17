package objectOriented.java8Interface;

public interface NewInterface2 {
    String NAME2 = "NewInterface2";

    default void defaultMethod(){
        System.out.println("NewInterface2 JDK 1.8 default 方法");
    }

    static void staticMethod(){
        System.out.println("NewInterface2 JDK 1.8 static 方法");
    }
}
