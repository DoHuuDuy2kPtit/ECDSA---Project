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

public class verify {

    File file2;

    public verify(File file2) {
        this.file2 = file2;
    }

    String verification(PublicKey pub1, BigInteger[] rs, BigInteger n) {
        BigInteger w, u1, u2, v = null;
        ECPoint R, R1, R2;
        BigInteger r = rs[0];
        BigInteger s = rs[1];

        w = s.modInverse(n);// Tinh w = s^(-1) mod n
        System.out.println("w: "+w);
        
        //calculation of hash
        //Getting the message digest
        FileRead fd = new FileRead();
        String sfile = fd.FiletoString(file2);
        System.out.println(sfile);
        String s2 = SHA1.hash(sfile);// e = HASH(m);z=Ln bit trai nhat cua e
        BigInteger h = new BigInteger(s2, 16);
        System.out.println("\nmessage digest: " + s2);

        //calculation of u1 and u2
        u1 = (h.multiply(w)).mod(n);//u1=z.w mod n
        u2 = (r.multiply(w)).mod(n);//u2=r.w mod n
        System.out.println("u1: "+u1);
        System.out.println("u2: "+u2);

        //calculation of u1*P+u2*Q
        R1 = pub1.E.point_multiplication(u1, pub1.P, pub1.E);//=u1.G
        R2 = pub1.E.point_multiplication(u2, pub1.Q, pub1.E);//=u2.Q
        R = pub1.E.point_add(R1, R2);//C(x1,y1)

        v = (R.get_x()).mod(n);// =x1 mod n
        System.out.println("R1: "+R1);
        System.out.println("R2: "+R2);
        System.out.println("R: "+R);
        
        //checking
        System.out.println("v: " + v);
        System.out.println("r: " + r);

        if (v.compareTo(r) == 0) {
            return "\nThe Signature is Valid.";
        } else {
            return "\nThe Signature is Invalid.";
        }
    }
}
