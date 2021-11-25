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
import java.util.Scanner;

public class verify {

    File file2;

    public verify() {
        
    }

    String verification(String mess, BigInteger[] rs, BigInteger n, ECPoint P1, Elliptic_Curve E1) {
        BigInteger w, u1, u2, v = null;
        ECPoint R, R1, R2;
        BigInteger r = rs[0];
        BigInteger s = rs[1];
        ECPoint Q = null;
        Scanner enter = new Scanner(System.in);
        
        try {
            FileReader fpr = new FileReader("F:\\PublicKey.txt");
            BufferedReader bpr = new BufferedReader(fpr);
            String publicX = bpr.readLine();
            String publicY = bpr.readLine();
            
            BigInteger bigPublicX = new BigInteger(publicX);
            BigInteger bigPublicY = new BigInteger(publicY);
            Q = new ECPoint(bigPublicX, bigPublicY);
            fpr.close();
        } catch (FileNotFoundException e) {
            System.out.print("File not found2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        PublicKey pub1 = new PublicKey(Q);
        enter.nextLine();
        w = s.modInverse(n);// Tinh w = s^(-1) mod n
        System.out.println("\nCalculate w = s^-1 mod n = "+w);
        
        //calculation of hash
        //Getting the message digest
        enter.nextLine();
        String messNoName = mess.split(":")[1].trim();
        
        String sfile = messNoName;
        String s2 = SHA1.hash(sfile);// e = HASH(m);z=Ln bit trai nhat cua e
        BigInteger h = new BigInteger(s2, 16);
        System.out.println("message digest (SHA1): " + s2);

        //calculation of u1 and u2
        enter.nextLine();
        u1 = (h.multiply(w)).mod(n);//u1=z.w mod n
        System.out.println("Calculate u1 = (h(x) * w) mod n = "+u1);
        enter.nextLine();
        u2 = (r.multiply(w)).mod(n);//u2=r.w mod n
        System.out.println("Calculate u2 = (r * w) mod n = "+u2);

        //calculation of u1*P+u2*Q
        enter.nextLine();
        R1 = E1.point_multiplication(u1, P1, E1);//=u1.G
        R2 = E1.point_multiplication(u2, Q, E1);//=u2.Q
        R = E1.point_add(R1, R2);//C(x1,y1)
        System.out.println("Calculate C = u1*G + u2*Q");
        System.out.println("C(x1,y1) = (" + R.get_x() + ", " + R.get_y() + ")"); // R la C
        v = (R.get_x()).mod(n);// =x1 mod n
        enter.nextLine();
        System.out.println("Calcute v = Cx1 mod n = " + v);
      
        //checking
        enter.nextLine();
        System.out.println("Compare r with v");

        if (v.compareTo(r) == 0) {
            return "\nThe Signature is Valid, Thats Alice.";
        } else {
            return "\nNo, The Signature is Invalid, Thats not Alice.";
        }
    }
}
