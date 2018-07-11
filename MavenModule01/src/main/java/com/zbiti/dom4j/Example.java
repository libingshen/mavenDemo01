package com.zbiti.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.net.URL;

public class Example {
    public static void main(String[] args) throws DocumentException {


        //获得类路径
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);

        //获得文件url
        URL url = Example.class.getResource("/contact.xml");
        System.out.println(url);

        //读取下xml文档,获得document对象
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        System.out.println(document);


    }
}
