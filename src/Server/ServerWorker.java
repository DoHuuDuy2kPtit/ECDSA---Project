/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * class nay xu li message tu client
 */
public class ServerWorker extends Thread{
    Socket clientSocket;
    Server server;
    ObjectOutputStream oos;
    
    
    public ServerWorker(Server server, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.server = server;
    }
   
    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleClientSocket() throws IOException, InterruptedException, ClassNotFoundException {
        this.oos = new ObjectOutputStream (clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        
        //lay username, format "name":"tin"
        String usernameRev = ois.readObject().toString();
        
        while(true){
            String messageRev = ois.readObject().toString();
//            System.out.println(messageRev);
            List<ServerWorker> WorkerList = server.getListWorker();;
            for(ServerWorker i: WorkerList){
                i.send(usernameRev + ": " + messageRev);
            }
        }
    }
    
    public void send(String msg) throws IOException{
        oos.writeObject(msg);
    }
    
    
}
