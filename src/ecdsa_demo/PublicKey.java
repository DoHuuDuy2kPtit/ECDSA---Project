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

public class PublicKey {

    public Elliptic_Curve E;
    public ECPoint P, Q;
    public BigInteger n;

    private BigInteger d; //this the private key
    private String privatekey;
    public PublicKey(ECPoint Q1){
        
        Q = Q1;
    }
    
    public PublicKey(Elliptic_Curve E1, ECPoint P1, BigInteger n1, String privatekey) {
        E = E1;
        P = P1;
        n = n1;
        this.privatekey = privatekey;
//        try {
//            FileReader fpr = new FileReader("E:\\PrivateKey.txt");
//            BufferedReader bpr = new BufferedReader(fpr);
//            String spr;
//            spr = bpr.readLine();
//            fpr.close();
//            d = new BigInteger("" + spr);
//        } catch (FileNotFoundException e) {
//            System.out.print("File not found2");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        d = new BigInteger("" + privatekey);
        Q = E1.point_multiplication(d, P1, E1);
        System.out.println("Calculate Q = d.G; Q(x,y) = (" + Q.get_x() + ", " + Q.get_y() + ")");
        try {
            File pkfile = new File("F:\\PublicKey.txt");
            if(pkfile.length() == 0){
                FileWriter prk = new FileWriter("F:\\PublicKey.txt", true);
                BufferedWriter bw = new BufferedWriter(prk);
                String publicX = new String("" + Q.get_x());
                String publicY = new String("" + Q.get_y());
                
//                prk.write(baseX);
//                prk.write("\n" + baseY);
                prk.write(publicX);
                prk.write("\n" + publicY);
                
                prk.close();
            }
            
        } catch (FileNotFoundException e) {
            System.out.print("File not found1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
