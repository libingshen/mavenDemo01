package com.zbiti.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.List;

public class Example {
    public static void main(String[] args) throws DocumentException {


        //获得类的绝对路径
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("path----->" + path);

        //获得文件url
        URL url = Example.class.getResource("/contact.xml");
        System.out.println("url----->" + url);

        //获得资源文件路径
        String path1 = Example.class.getResource("/contact.xml").getPath();
        System.out.println("path1----->" + path1);
        File file = new File(path1);
        System.out.println("file----->" + file);

        //读取下xml文档,获得document对象
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        System.out.println("document----->" + document);

        Document document1 = reader.read(file);
        System.out.println("document1----->" + document1);

        //false
        System.out.println(document == document1);


        /*
        * 节点对象操作方法
        *
        * */
        //获取文档根节点
        Element rootElement = document.getRootElement();

        //获得根标签的名字
        System.out.println(rootElement.getName());

        //获取根节点下面的所有子节点（不包括子节点的子节点）
       /* List<Element> elements = rootElement.elements();
//        for (int i = 0; i < elements.size(); i++) System.out.println(elements.get(i));
        for (Element element : elements){
            System.out.println(element);
        }*/

        //获得指定节点下面的子节点
        List<Element> test = rootElement.elements("test");
        for (Element element : test){
            System.out.println("test节点名称--->"+element.getName());
            System.out.println("test节点文本值--->"+element.getText());

        }

        String test1 = "/contactList/contactTwo/age";
        Node node = document.selectSingleNode(test1);
        String value = node.getStringValue().trim();
        System.out.println(value);


    }
}
