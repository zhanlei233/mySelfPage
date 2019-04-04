package com.esgov.myself.controller;

import java.util.ArrayList;
import java.util.List;

public class TestGetCommonWord{

	public static void main(String[] args) {
		String s = "This is C programming text ";
		String t = "This is a text for C programming";
		getCommonWord(s,t);
	}
	
	/**
	 * 返回两个字符串的第二长公共单词
	 * @param s
	 * @param t
	 */
	public static void getCommonWord(String s, String t) {
		//1.对字符串按照空格截串成数组
		String [] strs = s.split(" ");
		String [] ttrs = t.split(" ");
		//2.遍历两个数组得到数组中所有相同的字符串集合
		List<String> strList = new ArrayList<String>();
		for(int i=0; i<strs.length; i++) {
			for(int j=0; j<ttrs.length; j++) {
				if(ttrs[j].equals(strs[i])) {
					strList.add(ttrs[j]);
				}
			}
		}
		//3.对字符串集合中的数据进行排序，取出长度最长的两个字符串
		if(strList.size() > 0) {
			System.out.println("第一次打印数据结果: ");
			for(String ss : strList) {
				System.out.print(ss + " ");
			}
			
			System.out.println();
			String[] ss=strList.toArray(new String[strList.size()]);
			//数组排序
			bubbleSort(ss);
			System.out.println("第二次打印数据结果: ");
			for(String str : ss) {
				System.out.print(str + " ");
			}
			
			System.out.println();
			System.out.println("公共字符串中第二长的字符串是： ");
			System.out.println(ss[1]);
		}
	}
	
	//排序算法，倒序，将最小的放在最后面
	public static void bubbleSort(String []ss){
	    int len=ss.length;
	     for(int i=0;i<len;i++){
	       for(int j=0;j<len-i-1;j++){
	            if(ss[j].length()<ss[j+1].length()){
	               String temp=ss[j];
	               ss[j]=ss[j+1];
	               ss[j+1]=temp;
		    }
		  }
		}
	}


}
