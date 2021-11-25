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
import java.util.Scanner;

public class PrivateKey {

    String privatekey;

    public PrivateKey() {
    }
    
    
    
    public void privatekey_generation() {

        BigInteger n = new BigInteger("6277101735386680763835789423176059013767194773182842284081");
        SecureRandom rnd = new SecureRandom();
        BigInteger r = new BigInteger(n.bitLength(), rnd);
        Scanner enter = new Scanner(System.in);
        
        System.out.println("Pick secret key d where 0 < d < n, " + "d: " + r);
        String s = new String("" + r);
        privatekey = s;
//        System.out.println("Private Key: " + s);
        try {
            File prkfile = new File("F:\\PrivateKey.txt");
            if(prkfile.length() == 0){
                FileWriter prk = new FileWriter("F:\\PrivateKey.txt");
                prk.write(s);
                prk.close();
            }
            
        } catch (FileNotFoundException e) {
            System.out.print("File not found1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getPrivateKey(){
        return privatekey;
    }
    
    
    
    
}
