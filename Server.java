import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    
    static int portNo = 1234; //For testing purposes
    static ServerSocket serverSock;
    static Socket clientSock;
    static DataInputStream input;
    static PrintStream output;
    
    public static void main(String[] args) throws IOException{ //Exceptions thrown for method
        //Get how many people in chat
        Scanner s = new Scanner(System.in);
        System.out.print("How many people are joining the chat? ");
        int people = Integer.parseInt(s.next());
        
        //Creates a server socket which listens for connections from clients.
        serverSock = new ServerSocket(portNo); //DOES THIS HAVE TO BE THE SAME PORT NO AS THE CLIENTS?
        
        //Server waits for connection request from a client
        //Accepts the connection from the client
        //Assigns a unique port to the client
        clientSock = serverSock.accept(); 
        
        //Gets input from client to send through server.
        input = new DataInputStream(clientSock.getInputStream());
        
        //Sends output to a client
        output = new PrintStream(clientSock.getOutputStream());
    }  
    
}
