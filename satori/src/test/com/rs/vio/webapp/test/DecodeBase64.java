package com.rs.vio.webapp.test;

import org.apache.commons.codec.binary.Base64;

public class DecodeBase64 {
	public static void main(String args[]){
		/*byte[] encodedBytes = Base64.encodeBase64("almas".getBytes());
		System.out.println("encodedBytes " + new String(encodedBytes));
		
		byte[] decodedBytes = Base64.decodeBase64("0ODx+Oj08PPpIP3y7iDoIP8g8uXh5SDx6uDm8yDr/uHz/iDu5O3zIO/w4OLk8w==".getBytes());
		System.out.println("decodedBytes " + new String(decodedBytes));*/
		
		byte[] decodedBytesC1 = Base64.decodeBase64("YWxtYXM=".getBytes());
		byte[] decodedBytesC2 = Base64.decodeBase64("YWxtYXM=".getBytes());
		String s1 = new String(decodedBytesC1);
		String s2 = new String(decodedBytesC2);
		
		if (s1.equals(s2)){
			System.out.println("!!!");
		} else {
			System.out.println("???");
		}
		
		/*if (decodedBytesC1 == decodedBytesC2){
			System.out.println("!!");
		} else {
			System.out.println("???");
		}
		System.out.println("decodedBytes " + new String(decodedBytesC1));
		System.out.println("decodedBytes " + new String(decodedBytesC2));*/
	}
}
