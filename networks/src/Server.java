
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *

 */
public class Server {
    
  private static ServerSocket serverS; // declares the server socket
  Socket clientPort;
  int server_port= 1025; // predefined port
    
    public static void main(String[] args){
        //Get how many people in chat
        Scanner s = new Scanner(System.in);
//        System.out.print("How many people are joining the chat? ");
//        int people = Integer.parseInt(s.next());
//            
    }
    
    public void createServerSockets(){
        try {
           
            serverS = new ServerSocket(server_port);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void checkForConnection() throws IOException{
        while (true) {            
            clientPort = serverS.accept(); // blocks until a connection is established
            //spawn thread to handle communication passing clientPort
            
            
          ClientHandler t=new  ClientHandler(clientPort);
          t.start();
            
        }
    }
    
}
