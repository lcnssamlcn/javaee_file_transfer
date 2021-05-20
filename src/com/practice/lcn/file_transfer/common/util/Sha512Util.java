package com.practice.lcn.file_transfer.common.util;

import java.math.BigInteger; 
import java.security.MessageDigest; 

public class Sha512Util {
    public static String hash(String input) throws Exception { 
        MessageDigest md = MessageDigest.getInstance("SHA-512"); 

        // digest() method is called 
        // to calculate message digest of the input string 
        // returned as array of byte 
        byte[] messageDigest = md.digest(input.getBytes()); 

        // Convert byte array into signum representation 
        BigInteger no = new BigInteger(1, messageDigest); 

        // Convert message digest into hex value 
        String hashtext = no.toString(16); 

        // Add preceding 0s to make it 32 bit 
        while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; 
        } 

        return hashtext; 
    } 
}
