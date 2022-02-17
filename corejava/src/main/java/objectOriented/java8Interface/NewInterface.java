package objectOriented.java8Interface;

public interface NewInterface {

    String NAME1 = "NewInterface";

    //public abstract
    void abstractMethod();

    default void defaultMethod(){
        System.out.println("NewInterface JDK 1.8 default 方法");
    }

    static void staticMethod(){
        System.out.println("NewInterface JDK 1.8 static 方法");
    }

}
