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

/**
 *
 * Class nay don gian de chay serve thoi
 */


public class ServerMain {
    ArrayList<ServerWorker> WorkerList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        int port = 8888;
        Server server = new Server(port);
        server.start();
        
        
    }
}
