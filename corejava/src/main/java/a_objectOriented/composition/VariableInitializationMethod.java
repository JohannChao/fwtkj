package a_objectOriented.composition;

/**
 * @ClassName: VariableInitializationMethod
 * @Description: 实例（成员）变量初始化的四种方式
 * @Author: Johann
 * @Version: 1.0
 **/
public class VariableInitializationMethod {

    // 1. 变量被定义时，就已经初始化。
    private InstanceA instanceA = new InstanceA();
    private String s1 = "definition Initializing s1",
    s2,s3,s4;

    public VariableInitializationMethod(){
        System.out.println("VariableInitializationMethod()");
        // 2. 在该类的构造函数中初始化。
        s2 = "Constructor Initializing s2";
        System.out.println("s1 = "+s1+"\n"+"s1变量被定义时，就已经初始化。这意味着它总是在调用构造函数之前初始化。");
    }

    {
        // 4. 使用实例初始化。
        s3 = "Instance Initializing s3";
    }

    @Override
    public String toString() {
        if(s4 == null){
            // 3. 在实际使用的时候才初始化，即延时初始化（Delayed initialization）。
            s4 = "Instance Initializing s4";
        }
        return "VariableInitializationMethod{" +
                "instanceA=" + instanceA +
                ", s1='" + s1 + '\'' +
                ", s2='" + s2 + '\'' +
                ", s3='" + s3 + '\'' +
                ", s4='" + s4 + '\'' +
                '}';
    }

    public static void main(String[] args) {
        VariableInitializationMethod va = new VariableInitializationMethod();
        System.out.println(va);
    }
}

class InstanceA{
    private String s;
    InstanceA(){
        System.out.println("InstanceA()");
        // 2. 在该类的构造函数中初始化。
        s = "InstanceA";
    }

    @Override
    public String toString() {
        return "InstanceA{" +
                "s='" + s + '\'' +
                '}';
    }
}