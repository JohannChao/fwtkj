package objectOriented.javaConstructor;

/**
 * @ClassName: GrandFatherConstractor
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class GrandFatherConstractor {
    private String grandFatherName;
    public GrandFatherConstractor(){
        System.out.println("GrandFatherConstractor");
    }
    public GrandFatherConstractor(String grandFatherName){
        this.grandFatherName = grandFatherName;
        System.out.println("GrandFatherConstractor 带参数 ："+grandFatherName);
    }

    public String getGrandFatherName() {
        return grandFatherName;
    }

    public void setGrandFatherName(String grandFatherName) {
        this.grandFatherName = grandFatherName;
    }
}
