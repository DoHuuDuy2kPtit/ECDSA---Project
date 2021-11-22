/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * class nay mo ket noi socket server
 */
public class Server extends Thread{

    private final int serverPort;
    private ArrayList<ServerWorker> WorkerList = new ArrayList<>();
    
    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public List<ServerWorker> getListWorker(){
        return WorkerList;
    }
    
    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(8888);
            while(true){
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accept connect from " + clientSocket);
                ServerWorker worker = new ServerWorker(this, clientSocket);
                WorkerList.add(worker);
                worker.start();
                
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
}
