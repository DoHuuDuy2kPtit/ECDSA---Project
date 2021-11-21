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

        w = s.modInverse(n);

        //calculation of hash
        //Getting the message digest
        FileRead fd = new FileRead();
        String sfile = fd.FiletoString(file2);
        System.out.println(sfile);
        String s2 = SHA1.hash(sfile);
        BigInteger h = new BigInteger(s2, 16);
        System.out.println("\nmessage digest: " + s2);

        //calculation of u1 and u2
        u1 = (h.multiply(w)).mod(n);
        u2 = (r.multiply(w)).mod(n);

        //calculation of u1*P+u2*Q
        R1 = pub1.E.point_multiplication(u1, pub1.P, pub1.E);
        R2 = pub1.E.point_multiplication(u2, pub1.Q, pub1.E);
        R = pub1.E.point_add(R1, R2);

        v = (R.get_x()).mod(n);

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
