package objectOriented.java8Interface;

/**
 * @ClassName: MultipleInheritance
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class MultipleInheritance implements NewInterface,NewInterface2{

    private static String CLASS_NAME = "MultipleInheritance";
    @Override
    public void abstractMethod() {
        System.out.println("MultipleInheritance implements abstractMethod");
    }

    /**
    * 如果子类实现了多个接口，且这些接口中有同名的方法，则需要子类重写这个同名方法
    */
    @Override
    public void defaultMethod(){
        System.out.println("----子类START");
        NewInterface.super.defaultMethod();
        NewInterface2.super.defaultMethod();
        System.out.println("----子类END");

    }


    public void ownMethod(){
        System.out.println("MultipleInheritance ownMethod");
    }

    public static void main(String[] args) {
        NewInterface newInterface = new MultipleInheritance();

        System.out.println("MultipleInheritance.CLASS_NAME : "+MultipleInheritance.CLASS_NAME);
        System.out.println("MultipleInheritance.NAME1 : "+MultipleInheritance.NAME1);
        System.out.println("MultipleInheritance.NAME2 ： "+MultipleInheritance.NAME2);

        newInterface.abstractMethod();
        newInterface.defaultMethod();
        NewInterface.staticMethod();
        NewInterface2.staticMethod();

        ((MultipleInheritance) newInterface).ownMethod();
    }
}
