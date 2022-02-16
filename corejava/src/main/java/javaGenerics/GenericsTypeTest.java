package javaGenerics;

/**
 * @ClassName: GenericsTypeTest
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class GenericsTypeTest<T> {

    private T t;

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public static void main(String[] args){
        GenericsTypeTest type = new GenericsTypeTest();
        type.set("Johann");
        type.set(10);
        System.out.println(type.get());
    }
}
