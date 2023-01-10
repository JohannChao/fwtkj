package com.johann.javaFunctionalProgramming.lambdaDemo;

@FunctionalInterface
interface Writable {
    /**
     * 函数式接口代表只包含一个抽象方法接口。函数式类型接口可以包含多个默认方法、类方法，但只能声明一个抽象方法。
     */
    void write();

    /**
     * java.lang.Object 中的 public 方法
     * @param obj
     * @return
     */
    public boolean equals(Object obj);
    public String toString();

    /**
     * 接口默认方法
     * @return string
     */
    default String defaultWrite(){
        return "defaultWrite: I'm Johann, this.getClass() is -- "+this.getClass();
    }

    /**
     * 接口类方法
     * @return string
     */
    static String staticWrite(){
        return "staticWrite: I'm Johann";
    }

    public static void main(String[] args) {
        Writable writable = () -> {
            System.out.println("函数式接口Lambda表达式");
        };
        writable.write();
        System.out.println(writable.defaultWrite());
        System.out.println(Writable.staticWrite());
        System.out.println(writable.toString());
    }
}
