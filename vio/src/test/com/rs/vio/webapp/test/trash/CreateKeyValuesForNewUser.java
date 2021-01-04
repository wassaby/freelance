package com.rs.vio.webapp.test.trash;

import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;

public class CreateKeyValuesForNewUser {
	public static void main(String args[]){
		long methodId = Calendar.getInstance().getTimeInMillis();
		String i = Long.toString(methodId);
		System.out.println(i);
		
		/*String str = new String(DatatypeConverter.parseBase64Binary("user:123"));
        String res = DatatypeConverter.printBase64Binary(str.getBytes());
        System.out.println(str);*/
		
		byte[] encodedBytes = Base64.encodeBase64("BBBB".getBytes());
		System.out.println("encodedBytes " + new String(encodedBytes));
		byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
		System.out.println("decodedBytes " + new String(decodedBytes));
        
	}
}
