import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;


public class Server1 {
    
    static int portNo = 1234; //For testing purposes
    static ServerSocket serverSock;
    static Socket clientSock;
    static DataInputStream input;
    static DataOutputStream output;
   
    
    public static void main(String[] args) throws IOException{ //Exceptions thrown for method
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
		ServerSocket welcomeSocket2 = new ServerSocket(6788);

		while(true)
		{
			
			Socket connectionSocket = welcomeSocket.accept();
			
			DataInputStream fromClient = new DataInputStream(connectionSocket.getInputStream());
			DataOutputStream toClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = fromClient.readLine();
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			toClient.writeBytes(capitalizedSentence);
			
			Socket connectionSocket2 = welcomeSocket2.accept();
			DataInputStream fromClient2 = new DataInputStream(connectionSocket2.getInputStream());
			DataOutputStream toClient2 = new DataOutputStream(connectionSocket2.getOutputStream());
			clientSentence = fromClient2.readLine();
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			toClient2.writeBytes(capitalizedSentence);
		}
    }  
    
}
