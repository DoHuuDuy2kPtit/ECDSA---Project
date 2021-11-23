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

        
        //Getting the message digest
        System.out.println("The message is :");
        System.out.println(message);
//b1
        String s2 = SHA1.hash(message);
        BigInteger h = new BigInteger(s2, 16);
        System.out.println("Ket qua cua e = HASH(M):" + s2);

       // System.out.println("Ket qua cua z:" + h);//b2
//b3       
        SecureRandom rnd = new SecureRandom();
        k = new BigInteger(n.bitLength(), rnd);
        System.out.println("Ket qua cua k:" + k);
//b4            
        ECPoint kP = E1.point_multiplication(k, P, E1);
        System.out.println("Ket qua cua diem (x1,y1):");
        System.out.println("x1= " + kP.get_x());
        System.out.println("y1= " + kP.get_y());
//b5           
        r = (kP.get_x()).mod(E1.get_p());
        System.out.println("Ket qua cua r:" + r);
//b6            
        kinv = k.modInverse(n);
        System.out.println("Ket qua cua k^-1:"+k);  

        //Getting the private key

        BigInteger privateKey = new BigInteger(privatekey);
        s = (kinv.multiply(h.add(privateKey.multiply(r)))).mod(n);
        System.out.println("Ket qua cua s:"+s);
         
//b7
        BigInteger[] r_s = new BigInteger[2];
        r_s[0] = r;
        r_s[1] = s;
        System.out.println("r: " + r);
        System.out.println("s: " + s);
        return r_s;
    }
}
