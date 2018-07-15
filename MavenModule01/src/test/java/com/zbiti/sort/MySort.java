package com.zbiti.sort;

import org.junit.Test;


public class MySort {


    @Test
    public void test01(){
        int[] myList = {3,6,8,1,2,8,7};
        sort01(myList);
    }

    public void sort01(int[] arr){
        int[] result;
        for(int i = 0;i<arr.length-1;i++){
            for(int j = 0;j<arr.length-1-i;j++){
                System.out.println(arr[i]);
                System.out.println(arr[j]);
                System.out.println("==========");
                if(arr[i]>arr[j]){
                    int temp;
                    
                }
            }
        }

    }
}
