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
        BigInteger k, kinv, r = null, s = null;

        try {
            //Getting the message digest
            FileRead fd = new FileRead();
            String sfile = fd.FiletoString(file1);
            System.out.println("The message is :");
            System.out.println(sfile);
//b1
            String s2 = SHA1.hash(sfile);
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
            FileReader fin2 = new FileReader("E:\\PrivateKey.txt");
            BufferedReader br1 = new BufferedReader(fin2);
            String s3;
            s3 = br1.readLine();
            fin2.close();
            BigInteger privateKey = new BigInteger(s3);
            s = (kinv.multiply(h.add(privateKey.multiply(r)))).mod(n);
            System.out.println("Ket qua cua s:"+s);
        } catch (FileNotFoundException e) {
            System.out.print("File not found 3");
        } catch (IOException e) {
            e.printStackTrace();
        }
//b7
        BigInteger[] r_s = new BigInteger[2];
        r_s[0] = r;
        r_s[1] = s;
        System.out.println("r: " + r);
        System.out.println("s: " + s);
        return r_s;
    }
}
