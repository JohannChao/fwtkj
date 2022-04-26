package com.johann.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** DOM解析 xml
 * @ClassName: DomParse
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class DomParse {

    //解析器
    private DocumentBuilder documentBuilder;
    //文档对象
    private Document document;

    /**
     * 构造器初始化时，初始化解析器
     */
    public DomParse(){
        //创建解析工厂
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        //得到解析器
        try{
            documentBuilder = dbFactory.newDocumentBuilder();
        }catch (ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    /**
     * 加载xml文件，获取文档对象
     * @param filePath
     */
    public void loadFile(String filePath){
        try{
            //document = documentBuilder.parse(filePath);
            document = documentBuilder.parse(new File(filePath));
        }catch (IOException e){
            e.printStackTrace();
        }catch (SAXException e2){
            e2.printStackTrace();
        }
    }

    public void read(){
        Element rootElemenet =  document.getDocumentElement();


        NodeList nodeList = document.getElementsByTagName("name");
        for(int i = 0 ; i < nodeList.getLength() ;i++){
            NamedNodeMap np = nodeList.item(i).getAttributes();
            Node attribute = np.item(0);
            Node node = nodeList.item(i);
            System.out.println("【"+attribute.getNodeType() + "---"+ attribute.getNodeName()+ "=" + attribute.getTextContent() + "】  " +node.getNodeName() +"="+node.getTextContent());

        }

        list(rootElemenet);
    }

    /**
     * 打印所有标签
     * @param root
     */
    private void list(Node root) {
        if(root instanceof Element){
            System.out.println(root.getNodeName()+"---"+root.getNodeType()+"---"+root.getTextContent());
        }
        NodeList list = root.getChildNodes();
        System.out.println(root.getNodeName()+"---"+root.getTextContent()+"--list.getLength = "+list.getLength());
        for(int i = 0 ; i < list.getLength() ; i++){
            Node child = list.item(i);
            System.out.println(i+"--------------------");
            System.out.println("child.getNodeName() "+child.getNodeName());
            System.out.println("child.getTextContent() "+child.getTextContent());
            System.out.println(i+"--------------------");
            list(child);
        }
    }

    /**
     * 添加新的元素，属性
     * @param filePath
     * @throws Exception
     */
    public void add(String filePath) throws Exception{
        //创建节点
        Element sex = document.createElement("sex");
        sex.setTextContent("男");

        //把创建的节点添加到第一个<student></student>标签上
        Element student = (Element) document.getElementsByTagName("student").item(0);
        student.appendChild(sex);

        //在<name></name>中增加属性 <name address="xxx"></name>
        Element name = (Element) document.getElementsByTagName("name").item(0);
        name.setAttribute("address", "xxx");

        //把更新后的内存写入xml文档中
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tFormer = tfFactory.newTransformer();
        tFormer.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream(filePath)));
    }

    /**
     * 更新元素，属性
     * @param filePath
     * @throws Exception
     */
    public void update(String filePath) throws Exception{
        //得到要删除的第一个<name></name>节点
        Element name = (Element) document.getElementsByTagName("name").item(0);
        //在<name></name>中更新属性 <name address="xxx"></name>为<name address="yyy"></name>
        name.setAttribute("address", "yyy");
        name.setAttribute("alias", "新约翰");
        //更新name节点的文字为VAE,即<name>vae</name>
        name.setTextContent("new_johann");

        //把更新后的内存写入xml文档中
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tFormer = tfFactory.newTransformer();
        tFormer.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream(filePath)));
    }

    /**
     * 删除节点元素，属性
     * @param filePath
     * @throws Exception
     */
    public void delete(String filePath) throws Exception{
        //得到要删除的第一个<name></name>节点
        Element name = (Element) document.getElementsByTagName("name").item(0);
        //得到要删除的第一个<name></name>节点的父节点
        //Element student = (Element) document.getElementsByTagName("student").item(0);
        //student.removeChild(name);
        //上面两步可以简写为
        name.getParentNode().removeChild(name);

        //在<name></name>中删除属性 <name address="xxx"></name>
        name.removeAttribute("alias");

        //把更新后的内存写入xml文档中
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tFormer = tfFactory.newTransformer();
        tFormer.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream(filePath)));
    }


    public static void main(String[] args) throws Exception{
        DomParse domParse = new DomParse();
        String filePath = "javaTool/src/main/java/com/johann/xml/johannTest.xml";
        domParse.loadFile(filePath);
        //domParse.read();
        //domParse.add(filePath);
        //domParse.update(filePath);
        //domParse.delete(filePath);
    }
}
