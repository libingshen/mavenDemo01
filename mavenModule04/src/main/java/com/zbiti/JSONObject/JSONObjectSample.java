package com.zbiti.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * api:
 * http://json-lib.sourceforge.net/apidocs/jdk15/index.html
 *
 * @author slb
 * @create 2018-08-02 11:18
 */

public class JSONObjectSample {


    //创建jsonObject第一种方法
    private static JSONObject createJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "huangwuyi");
        jsonObject.put("sex", "男");
        jsonObject.put("QQ", "999999999");
        jsonObject.put("Min.score", new Integer(99));
        jsonObject.put("nickname", "梦中心境");
        return jsonObject;
    }

    //创建jsonObject第二种方法
    private static JSONObject createJSONObject2() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("UserName", "ZHULI");
        hashMap.put("age", "30");
        hashMap.put("workIn", "ALI");
        JSONObject jsonObject = JSONObject.fromObject(hashMap);
//        System.out.println("jsonObject2：" + JSONObject.fromObject(hashMap));
        return jsonObject;
    }

    //创建一个JsonArray方法1
    private static JSONArray createJSONArray() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(0, "kobi");
        jsonArray.add(1, "34");
        jsonArray.add(2, "ALI");
//        System.out.println("jsonArray1：" + jsonArray);
        return jsonArray;
    }

    //创建JsonArray方法2
    private static JSONArray createJSONArray2() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("kobi");
        arrayList.add("34");
        arrayList.add("ALI");
        JSONArray jsonArray = JSONArray.fromObject(arrayList);
//        System.out.println("jsonArray2：" + JSONArray.fromObject(arrayList));
        return jsonArray;
    }

    //获得jsonObject
    @Test
    public void test01() {
        JSONObject jsonObject = createJSONObject2();
        System.out.println("jsonObject==>" + jsonObject);
        //获取JSONObject中值
        String workIn = jsonObject.getString("workIn");
        System.out.println("workIn=============>" + workIn);
    }

    //获得jsonArray
    @Test
    public void test02() {
        JSONArray jsonArray = createJSONArray();
        System.out.println("jsonArray==>" + jsonArray);
    }

    //获得jsonArray
    @Test
    public void test03() {
        JSONArray jsonArray = createJSONArray2();
        System.out.println("jsonArray==>" + jsonArray);
        //获取JSONArray中的值
        String value = jsonArray.getString(1);
        System.out.println("value=============>" + value);
    }


    public static void main(String[] args) {
        JSONObject jsonObject = JSONObjectSample.createJSONObject();
        //输出jsonobject对象
        System.out.println("jsonObject==>" + jsonObject);

        //判读输出对象的类型
        boolean isArray = jsonObject.isArray();
        boolean isEmpty = jsonObject.isEmpty();
        boolean isNullObject = jsonObject.isNullObject();
        System.out.println("isArray===========>" + isArray);
        System.out.println("isEmpty===========>" + isEmpty);
        System.out.println("isNullObject==========>" + isNullObject);

        //添加属性
        jsonObject.element("address", "福建省厦门市");
        System.out.println("添加属性后的对象==>" + jsonObject);

        //返回一个JSONArray对象
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(0, "this is a jsonArray value");
        //jsonObject添加一个数组
        jsonObject.element("jsonArray", jsonArray);
//        jsonObject.put("jsonArray",jsonArray);
        JSONArray array = jsonObject.getJSONArray("jsonArray");
        System.out.println("返回一个JSONArray对象：" + array);
        System.out.println("添加JSONArray后的值==========>" + jsonObject);

        //根据key返回一个字符串
        String username = jsonObject.getString("username");
        System.out.println("username==>" + username);


        //把字符转换为 JSONObject
        String temp = jsonObject.toString();
        System.out.println("temp=========>" + temp);
        System.out.println("temp的类型===========>" + temp instanceof String);
        //将Json字符串转为java对象
        JSONObject object = JSONObject.fromObject(temp);
        //转换后根据Key返回值
        System.out.println("qq=" + object.get("QQ"));
    }
}
