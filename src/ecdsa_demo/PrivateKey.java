/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ecdsa_demo;

/**
 *
 * @author admin
 */
import java.io.*;
import java.math.*;
import java.security.*;

public class PrivateKey {

    public static void privatekey_generation() {

        BigInteger n = new BigInteger("6277101735386680763835789423176059013767194773182842284081");
        SecureRandom rnd = new SecureRandom();
        BigInteger r = new BigInteger(n.bitLength(), rnd);

        System.out.println("private key: " + r);
        String s = new String("" + r);
        try {
            FileWriter prk = new FileWriter("E:\\PrivateKey.txt");
            prk.write(s);
            prk.close();
        } catch (FileNotFoundException e) {
            System.out.print("File not found1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
