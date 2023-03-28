package com.johann.designPattern.dahao.composite;

/** 组合模式Demo
 * 组合模式（Composite Pattern）,将对象组合成树形结构以表示"部分-整体"的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 * 这种模式创建了一个包含自己对象组的类。该类提供了修改相同对象组的方式。
 * @ClassName: ZyhCompositeDemo
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ZyhCompositeDemo {
    public static void main(String[] args) {
        AbstractComponent root = new Composite("root",1,false);
        root.add(new Composite("root_leafA",root.getDepth()+1,true));
        root.add(new Composite("root_leafB",root.getDepth()+1,true));

        AbstractComponent rootCompC = new Composite("root_compC",root.getDepth()+1,false);
        rootCompC.add(new Composite("root_compC_leafA",rootCompC.getDepth()+1,true));
        rootCompC.add(new Composite("root_compC_leafB",rootCompC.getDepth()+1,true));
        root.add(rootCompC);

        AbstractComponent rootCompCompC = new Composite("root_compC_compC",rootCompC.getDepth()+1,false);
        rootCompCompC.add(new Composite("root_compC_compC_leafA",rootCompCompC.getDepth()+1,true));
        rootCompCompC.add(new Composite("root_compC_compC_leafB",rootCompCompC.getDepth()+1,true));
        rootCompC.add(rootCompCompC);

        AbstractComponent rootLeafD = new Composite("root_leafD",root.getDepth()+1,true);
        root.add(rootLeafD);
        rootLeafD.add(new Composite("root_leafD_leafA",rootLeafD.getDepth()+1,true));

        root.display();
    }
}
