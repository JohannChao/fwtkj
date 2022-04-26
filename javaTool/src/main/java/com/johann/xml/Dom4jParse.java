package com.johann.xml;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**Dom4j解析 xml
 * @ClassName: Dom4jParse
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class Dom4jParse {
    //文档对象
    private Document document;
    //读取器
    private SAXReader saxReader;

    public Dom4jParse(){
        saxReader = new SAXReader();
    }

    /**
     * 加载xml文件
     * @param filePath
     * @throws DocumentException
     */
    public void loadFile(String filePath) throws DocumentException {
        document = saxReader.read(new File(filePath));
    }

    /**
     * 读取 xml 文件
     */
    public void read(){
        //得到根节点
        Element root = document.getRootElement();
        //得到第二个<student><student>节点
        Element student = root.elements("student").get(1);
        //获取<name><name>中间的值
        String value = student.element("name").getText();
        System.out.println(value);
        //获取<name alias="xxx"><name>中间的alias值
        String alias = student.element("name").attributeValue("alias");
        System.out.println(alias);

    }

    /**
     * 新增节点元素，属性值
     * @param filePath
     * @throws Exception
     */
    public void add(String filePath) throws Exception{
        //Element student = document.getRootElement().element("student");
        Element student = document.getRootElement().elements("student").get(1);
        //添加新元素
        student.addElement("schoolName").setText("新曙光");
        //添加新属性
        student.addAttribute("grade","9");

        sameBlock(filePath);
    }

    /**
     * 更新节点元素，属性值
     * @param filePath
     * @throws Exception
     */
    public void update(String filePath) throws Exception{
        Element student = document.getRootElement().elements("student").get(1);
        Element school = student.element("schoolName");
        //修改节点元素名称
        school.setName("schoolNameNew");
        //修改节点元素值
        school.setText("新曙光");
        //获取属性名称
        System.out.println(student.element("name").attribute("alias").getName());
        //修改元素属性值
        //student.element("name").attribute("alias").setValue("新杰西");
        student.element("name").attribute("alias").setText("新杰西");


        sameBlock(filePath);

    }

    /**
     * 移除节点元素
     * @param filePath
     * @throws Exception
     */
    public void remove(String filePath) throws Exception{

        Element student = document.getRootElement().elements("student").get(1);
        //移除属性
        Attribute grade = student.attribute("grade");
        student.remove(grade);

        Element schoolName = student.element("schoolNameNew");
        //移除元素
        schoolName.getParent().remove(schoolName);
        sameBlock(filePath);

    }

    public void sameBlock(String filePath) throws Exception{
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");

        XMLWriter writer = new XMLWriter(new FileOutputStream(filePath),format);
        writer.write(document);
        writer.close();
    }


    public static void main(String[] args) throws Exception{
        Dom4jParse dom4jParse = new Dom4jParse();
        String filePath = "javaTool/src/main/java/com/johann/xml/johannTest.xml";
        dom4jParse.loadFile(filePath);
        dom4jParse.read();
        //dom4jParse.add(filePath);
        //dom4jParse.update(filePath);
        //dom4jParse.remove(filePath);
    }
}
