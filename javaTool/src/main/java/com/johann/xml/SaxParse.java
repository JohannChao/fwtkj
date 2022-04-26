package com.johann.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/** SAX解析 xml
 * @ClassName: SaxParse
 * @Description: TODO
 * @Author: Johann
 * @Version: 1.0
 **/
public class SaxParse {

    private XMLReader xmlReader;

    public SaxParse(){
        //创建解析工厂
        SAXParserFactory spFactory = SAXParserFactory.newInstance();
        try{
            //获得解析器
            SAXParser saxParser = spFactory.newSAXParser();
            //获得读取器
            xmlReader = saxParser.getXMLReader();
        }catch (SAXException e){
            e.printStackTrace();
        }catch (ParserConfigurationException e2){
            e2.printStackTrace();
        }
    }

    public static void main(String[] args) throws SAXException, IOException {
        SaxParse saxParse = new SaxParse();
        //设置内容处理器
        saxParse.xmlReader.setContentHandler(new TagDefaultHandler());
        String uri = "javaTool/src/main/java/com/johann/xml/johannTest.xml";
        saxParse.xmlReader.parse(uri);
    }
}


/**
 * 第一种方法：继承接口ContentHandler 得到 XML 文档所有内容
 */
class ListHandler implements ContentHandler {

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {
        System.out.println("<"+qName+">");
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        System.out.println(new String(ch,start,length));
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("</"+qName+">");
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

}

/**
 * 内容处理器
 */
class TagDefaultHandler extends DefaultHandler{
    //当前解析的是什么标签
    private String currentTag;
    //想获得第几个标签的值
    private int tagNumber = 0;
    //当前解析的是第几个标签
    private int currentTagNumber = 0;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        System.out.println("<"+qName+">");
        //System.out.println(uri+"---"+localName+"---"+attributes);
        System.out.println("attributes: "+attributes.getQName(0)+"--"+attributes.getValue(0));
//        currentTag = qName;
        //当前解析的name 标签是第几个
//        if("name".equals(currentTag)){
//            currentTagNumber++;
//            System.out.println(currentTagNumber);
//        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //打印所有name标签的值
//        if("name".equals(currentTag)){
//            System.out.println(new String(ch,start,length));
//        }
        //想获得 第二个name标签的值
//        tagNumber = 2;
//        if("name".equals(currentTag)&&currentTagNumber==tagNumber){
//            System.out.println(new String(ch,start,length));
//        }
        System.out.println(new String(ch,start,length));
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("</"+qName+">");
    }

}

