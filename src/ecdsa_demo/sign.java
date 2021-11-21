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

    public sign(File file1) {
        this.file1 = file1;
    }

    BigInteger[] signing(Elliptic_Curve E1, ECPoint P, BigInteger n) {
        BigInteger k, kinv, r, s = null;

        SecureRandom rnd = new SecureRandom();
        k = new BigInteger(n.bitLength(), rnd);

        ECPoint kP = E1.point_multiplication(k, P, E1);
        r = (kP.get_x()).mod(E1.get_p());

        //System.out.println(kP.get_x());
        kinv = k.modInverse(n);

        try {

            //Getting the message digest
            FileRead fd = new FileRead();
            String sfile = fd.FiletoString(file1);
            System.out.println("The message is :");
            System.out.println(sfile);
            String s2 = SHA1.hash(sfile);
            BigInteger h = new BigInteger(s2, 16);
            System.out.println("\nmessage digest: " + s2);

            //Getting the private key
            FileReader fin2 = new FileReader("E:\\PrivateKey.txt");
            BufferedReader br1 = new BufferedReader(fin2);
            String s3;
            s3 = br1.readLine();
            fin2.close();
            BigInteger privateKey = new BigInteger(s3);
            s = (kinv.multiply(h.add(privateKey.multiply(r)))).mod(n);
        } catch (FileNotFoundException e) {
            System.out.print("File not found 3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BigInteger[] r_s = new BigInteger[2];
        r_s[0] = r;
        r_s[1] = s;
        System.out.println("r: " + r);
        System.out.println("s: " + s);
        return r_s;
    }
}
