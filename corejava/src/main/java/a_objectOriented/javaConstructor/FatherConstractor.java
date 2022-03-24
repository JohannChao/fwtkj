package a_objectOriented.javaConstructor;

/**
 * @ClassName: FatherConstractor
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class FatherConstractor extends GrandFatherConstractor{
    private String fatherName;
    public FatherConstractor(){
        System.out.println("FatherConstractor");
    }
    public FatherConstractor(String fatherName){
        super();
        this.fatherName = fatherName;
        System.out.println("FatherConstractor 带参数 ："+fatherName);
    }

    public FatherConstractor(String grandFatherName, String fatherName) {
        super(grandFatherName);
        this.fatherName = fatherName;
        System.out.println("FatherConstractor 带参数 ："+grandFatherName+","+fatherName);
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
}
