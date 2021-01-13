package test;

import java.util.Base64;

public class DecodeTest {

    public static void main (String[] args){
        String str = "c21hbmFnZXI=";
        String str2 = "123";
        String encodedStr = new String(Base64.getDecoder().decode(str));
        String decodedStr = new String(Base64.getEncoder().encode(str2.getBytes()));
        System.out.print(encodedStr + "\n");
        System.out.print(decodedStr);
    }
}
