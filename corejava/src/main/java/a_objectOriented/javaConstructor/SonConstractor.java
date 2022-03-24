package a_objectOriented.javaConstructor;

/**
 * @ClassName: SonConstractor
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class SonConstractor extends FatherConstractor{
    private String sonName;
    public SonConstractor(){
        System.out.println("SonConstractor");
    }

    public SonConstractor(String sonName) {
        this.sonName = sonName;
        System.out.println("SonConstractor 带参数 ："+sonName);
    }

    public SonConstractor(String fatherName, String sonName) {
        super(fatherName);
        this.sonName = sonName;
        System.out.println("SonConstractor 带参数 ："+fatherName+","+sonName);
    }

    public SonConstractor(String grandFatherName, String fatherName, String sonName) {
        super(grandFatherName, fatherName);
        this.sonName = sonName;
        System.out.println("SonConstractor 带参数 ："+grandFatherName+","+fatherName+","+sonName);
    }

    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }

    public static void main(String[] args) {
        //SonConstractor sonConstractor = new SonConstractor("grand","father","son");
        SonConstractor sonConstractor = new SonConstractor("son");
    }
}
