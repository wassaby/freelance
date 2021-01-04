package com.rs.vio.webapp.test.trash;

import org.apache.commons.codec.binary.Base64;

public class DecodeBase64 {
	public static void main(String args[]){
		byte[] encodedBytes = Base64.encodeBase64("123".getBytes());
		System.out.println("encodedBytes " + new String(encodedBytes));
		
		byte[] decodedBytes = Base64.decodeBase64("c21hbmFnZXI=".getBytes());
		System.out.println("decodedBytes " + new String(decodedBytes));

	}
}
