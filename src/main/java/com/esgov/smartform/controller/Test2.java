package com.esgov.smartform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test2 {
	public static void main(String[] args) {
		getSum(17);
	}
	
	public static void getSum(int num) {
		int count = 0;
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for(int i=(num/2 + 1); i>=1; i--) {
			Map<String, Object> map = new HashMap<String, Object>();
			int j = i;
			map.put("start", j);
			while(i > (num/4 + 1)) {
				count += j;
				j--;
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
			for(int i=0; i<resultList.size(); i++) {
				String sums = "";
				int end = (int)resultList.get(i).get("end");
				int start = (int)resultList.get(i).get("start");
				for(int j=end; j<=start; j++) {
					sums += j + "+";
				}
				System.out.println(num + "=" + sums.substring(0, sums.length()-1));
			}
		}
	}
}
