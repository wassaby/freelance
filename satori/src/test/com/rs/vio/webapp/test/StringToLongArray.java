package com.rs.vio.webapp.test;

import java.util.StringTokenizer;

public class StringToLongArray {
	
	public static void main(String args[]){
		String items = "1,2,3,4,5,6";
		
		StringTokenizer st = new StringTokenizer(items, ",");
		while (st.hasMoreTokens()) {
		     System.out.println(st.nextToken());
		}
		
		String[] ss = items.split(",");
		for(int i=0;i<ss.length;i++)
		{
		    System.out.println(ss[i]);
		}
	}
	
}
