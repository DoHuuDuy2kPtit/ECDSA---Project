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
import java.security.SecureRandom;
import java.util.Scanner;

public class sign {

    File file1;
    String message;
    String privatekey;
    
    public sign(String message, String privatekey) {
        this.message = message;
        this.privatekey = privatekey;
    }

    BigInteger[] signing(Elliptic_Curve E1, ECPoint P, BigInteger n) {
        BigInteger k, kinv, r = null, s = null;
        Scanner enter = new Scanner(System.in);
        
        //Getting the message digest
        System.out.print("The message is: ");
        System.out.println(message);
//b1
        enter.nextLine();
        String s2 = SHA1.hash(message);
        BigInteger h = new BigInteger(s2, 16);
        System.out.println("Calculate e = HASH(M):" + s2);

       // System.out.println("Ket qua cua z:" + h);//b2
//b3       
        enter.nextLine();
        SecureRandom rnd = new SecureRandom();
        k = new BigInteger(n.bitLength(), rnd);
        System.out.println("Pick random number k in [1,n-1]:" + k);
//b4            
        enter.nextLine();
        System.out.println("Calculate R(x1,y1) = k x G;");
        ECPoint kP = E1.point_multiplication(k, P, E1);
        System.out.println("x1= " + kP.get_x());
        System.out.println("y1= " + kP.get_y());
//b5           
        enter.nextLine();
        
        r = (kP.get_x()).mod(E1.get_p());
        System.out.print("Calculate r = x1 mod n; r = " + r);
//b6            
        enter.nextLine();
        kinv = k.modInverse(n);
//        System.out.println("Ket qua cua k^-1:"+k);  

        //Getting the private key
        
        BigInteger privateKey = new BigInteger(privatekey);
        s = (kinv.multiply(h.add(privateKey.multiply(r)))).mod(n);
        System.out.println("Calculate s = k^-1 * (e + d*r) = " + s);
         
//b7
        enter.nextLine();
        BigInteger[] r_s = new BigInteger[2];
        r_s[0] = r;
        r_s[1] = s;
        System.out.println("r: " + r);
        System.out.println("s: " + s);
        return r_s;
    }
}
