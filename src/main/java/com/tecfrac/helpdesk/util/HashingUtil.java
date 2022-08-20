/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CLICK ONCE
 */
public class HashingUtil {

    public static String hashString(String originalString) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    originalString.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashingUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return originalString;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String args[]) {
        String test = hashString("test");
        System.out.println("test:" + test);
    }
}
