package com.cn.poker.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestReadFile {
	public static void main(String[] args) {
		 File file = new File("C:/Users/Administrator/Desktop/123/1.txt");
	        StringBuilder result = new StringBuilder();
	        try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
	                if(s.contains("错误====sql===INSERT INTO t_interf_platform_order"))
	                {
	                  System.err.println(s.split("====sql===")[1]+";");	
	                }
	            }
	            br.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
