package ecdsa_demo;
import ecdsa_demo.ECPoint;
import ecdsa_demo.Elliptic_Curve;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class ClientMain {
    
    
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("localhost",8888);
        Scanner in = new Scanner(System.in);
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        
        BigInteger a = new BigInteger("-3");
        BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
        BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
        BigInteger n = new BigInteger("6277101735386680763835789423176059013767194773182842284081");

        BigInteger Px = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
        BigInteger Py = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);

        Elliptic_Curve E1 = new Elliptic_Curve(a, b, p);

        ECPoint P = new ECPoint(Px, Py);
        
        
        System.out.print("Enter name: ");
        String name = in.nextLine();
        oos.writeObject(name); 
        new ReceiveMessage(ois,clientSocket).start();
        
        while(true){
            System.out.print(name + ": ");
            String message = in.nextLine();
            // create private key
            System.out.println("Press ENTER to create the private key");
            Scanner keyboard1 = new Scanner(System.in);
            keyboard1.nextLine();
            PrivateKey prvk = new PrivateKey();
            prvk.privatekey_generation();
            
            // create public key
            System.out.println("\nPress ENTER to create the public key");
            Scanner keyboard2 = new Scanner(System.in);
            keyboard2.nextLine();
            PublicKey pub = new PublicKey(E1, P, n, prvk.getPrivateKey());
            System.out.println("Public key has been created." + "\n");
//            oos.writeObject(message);
            // sign message
            System.out.println("\nPress ENTER to sign the message");
            Scanner keyboard4 = new Scanner(System.in);
            keyboard4.nextLine();
            sign signobj = new sign(message, prvk.getPrivateKey());
            BigInteger Signature[] = signobj.signing(E1, P, n);
            String r = new String("" + Signature[0]);
            String s = new String("" + Signature[1]);
            /*
            "tam gio toi nay nhe", 
            
            
            
            */
            String messageSend = message + "," + r + "," + s;
            
            oos.writeObject(messageSend);
        }
    }
    
}
class ReceiveMessage extends Thread{
    ObjectInputStream ois;
    Socket clientSocket;

    public ReceiveMessage(ObjectInputStream ois, Socket clientSocket){
        this.ois = ois;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        try {
            while(true){
                String msgRev = ois.readObject().toString();
                
                System.out.print("\n" + msgRev.split(",")[0]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}