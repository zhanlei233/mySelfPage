package com.esgov.myself.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TestGetSum {
	public static void main(String[] args) {
		getSum(15);
	}
	/**
	 * 将整数分解成若干连续整数和的形式
	 * @param num
	 */
	public static void getSum(int num) {
		if(num <= 10000) {
			int count = 0;
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for(int i=(num/2 + 1); i>=1; i--) {
				//1.从num/2向上取整开始遍历
				Map<String, Object> map = new HashMap<String, Object>();
				int j = i;
				map.put("start", j);
				while(i > (num/4 + 1)) {
					//2.累积count的值
					count += j;
					j--;
					//3.当count与num相等时，跳出循环
					if(count == num) {
						map.put("end", j+1);
						resultList.add(map);
						count = 0;
						break;
					}
					if(count > num || j == 0) {
						count = 0;
						break;
					}
					
				}
			}
			if(resultList.size() == 0) {
				System.out.println("结果为： NONE");
			}else {
				//4.遍历结果集，得出连续整数和的形式
				for(int i=0; i<resultList.size(); i++) {
					String sums = "";
					int end = (int)resultList.get(i).get("end");
					int start = (int)resultList.get(i).get("start");
					for(int j=end; j<=start; j++) {
						sums += j + " ";
					}
					System.out.println(sums);
				}
			}
		}else {
			System.out.println("输入整数必须小于等于10000！");
		}
	}
}
