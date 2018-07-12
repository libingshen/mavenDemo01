package com.zbiti.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @Description: 【读取book.xml文档中的数据】
 * @Version: 1.0
 * @CreateDate: 2018/7/12
 * @Author: slb
 */
public class Demo1 {

    //文件路径
    String str = Thread.currentThread().getContextClassLoader().getResource("book.xml").getPath();


    @Test
    public void readText() throws Exception {
        //获得解析器对象
        SAXReader reader = new SAXReader();
        //通过解析器对象获得文档对象
        Document document = reader.read(new File(str));
        //得到根节点（此处的根节点是“书架”）
        Element root = document.getRootElement();
        //得到子节点“书”的子节点“书名”
        Element bookname = root.element("书").element("书名");
        //得到节点的值或文本
        System.out.println(bookname.getText());

    }

    //读取xml文档中的属性
    @Test
    public void readAttr() throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(str));
        Element root = document.getRootElement();//得到了“书架”节点
        //得到标签中属性的值
        String value = root.element("书").attributeValue("name");
        System.out.println(value);
    }

    //使用xpath解析
    //注意需要jaxen-1.1-beta-6.jar和jaxme-api-0.3.jar包
    @Test
    public void findWithXpath() throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(str));
//        Element element = (Element) document.selectNodes("/书架/书/书名").get(1);
        Element element = (Element) document.selectNodes("//书名").get(0);
        System.out.println(element.getText());

    }

    //遍历文档
    @Test
    public void test01() throws DocumentException {
        //获得解析器对象
        SAXReader reader = new SAXReader();
        //通过解析器对象获得文档对象
        Document document = reader.read(new File(str));

        List<Node> nodes = document.selectNodes("/书架/书/书名" );
        /*for (Node node : nodes){
            System.out.println(node.selectSingleNode("售价").getText());
            System.out.println(node.selectSingleNode("书名").getText());
            System.out.println(node.selectSingleNode("作者").getText());
            System.out.println("=================");
        }*/
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()){
            Element element = (Element) iterator.next();
            System.out.println(element.getText());


        }
    }


}
