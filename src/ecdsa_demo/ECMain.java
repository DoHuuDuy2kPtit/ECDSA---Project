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
import java.util.*;
import javax.swing.*;

public class ECMain {

    public static void main(String[] args) {
        try {

            /*
			 * Curve P-192
			 * 
			 * a = -3 p = 6277101735386680763835789423207666416083908700390324961279 b =
			 * 64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1 n =
			 * 6277101735386680763835789423176059013767194773182842284081 Px =
			 * 188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012 Py =
			 * 07192b95ffc8da78631011ed6b24cdd573f977a11e794811
             */
            BigInteger a = new BigInteger("-3");
            BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
            BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
            BigInteger n = new BigInteger("6277101735386680763835789423176059013767194773182842284081");

            BigInteger Px = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
            BigInteger Py = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);

            Elliptic_Curve E1 = new Elliptic_Curve(a, b, p);

            ECPoint P = new ECPoint(Px, Py);

            /*
			 * ECPoint R = E1.point_add(P, P); R = E1.point_add(P, R); R = E1.point_add(P,
			 * R); R = E1.point_add(P, R); E1.point_check(R);
             */
            // private key generation
            System.out.println("Press ENTER to create the private key");
            Scanner keyboard1 = new Scanner(System.in);
            keyboard1.nextLine();
            PrivateKey.privatekey_generation();

            // public key generation
            System.out.println("\nPress ENTER to create the public key");
            Scanner keyboard2 = new Scanner(System.in);
            keyboard2.nextLine();
            PublicKey pub = new PublicKey(E1, P, n);
            System.out.println("Public key has been created." + "\n");

            // reading the text file where the message is already saved
            System.out.println("Now press ENTER to browse the file containing the message");
            Scanner keyboard3 = new Scanner(System.in);
            keyboard3.nextLine();
            //JFileChooser chooser = new JFileChooser();
            //int returnVal = chooser.showOpenDialog(null);
            //if (returnVal == JFileChooser.APPROVE_OPTION) {
//				File f = chooser.getSelectedFile();
            File f = new File("E:\\message.txt");
            String filepath = f.getAbsolutePath();
            System.out.println("The filepath is : " + filepath);

            // signing
            System.out.println("\nPress ENTER to sign the message");
            Scanner keyboard4 = new Scanner(System.in);
            keyboard4.nextLine();
            sign signobj = new sign(f);
            BigInteger Signature[] = signobj.signing(E1, P, n);

            // saving the signature in the file
            FileWriter fsign1 = new FileWriter("E:\\signature1.txt");
            FileWriter fsign2 = new FileWriter("E:\\signature2.txt");
            String r = new String("" + Signature[0]);
            String s = new String("" + Signature[1]);
            fsign1.write(r);
            fsign2.write(s);
            fsign1.close();
            fsign2.close();

            // verification
            System.out.println("\nPress ENTER to verify the signature");
            Scanner keyboard5 = new Scanner(System.in);
            keyboard5.nextLine();
            verify verifyobj = new verify(f);
            String vers = verifyobj.verification(pub, Signature, n);
            System.out.println(vers);
            //}
        } catch (FileNotFoundException e) {
            System.out.print("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
