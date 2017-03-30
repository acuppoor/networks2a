import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;


public class Server {
    
    static int portNo = 1234; //For testing purposes
    static ServerSocket serverSock;
    static Socket clientSock;
    static DataInputStream input;
    static DataOutputStream output;
   
    
    public static void main(String[] args) throws IOException{ //Exceptions thrown for method
		String messageFromClient;
		String messageToClient;
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many people will chat? \n");
		int numberOfPeople = 2;
		
		try{
			numberOfPeople = scanner.nextInt();
		} catch (Exception e){
			numberOfPeople = 2;
			System.out.println("Number of people is assumed to be 2, error with the number of people entered:" + e.getStackTrace());
		}
		
		ServerSocket[] serverSockets = new ServerSocket[numberOfPeople];
		String[] portNumbers = new String[numberOfPeople];
		Socket[] sockets = new Socket[numberOfPeople];
		
		System.out.println("Enter the " + numberOfPeople + " port numbers (separated by space):" );
		
		for (int i = 0; i < numberOfPeople; i++){
			portNumbers[i] = scanner.next();
			serverSockets[i] = new ServerSocket(Integer.parseInt(portNumbers[i]));
		}
		
		ClientHandler[] clientHandlers = new ClientHandler[numberOfPeople];
		
		/*ServerSocket welcomeSocket = new ServerSocket(2222);	
			
		while(true)
		{	
			Socket connectionSocket = welcomeSocket.accept();
			ClientHandler clientHandler = new ClientHandler(connectionSocket);
			clientHandler.start();
		}*/
		
		while(true){
			for(int i = 0; i < numberOfPeople; i++){
				sockets[i] = serverSockets[i].accept();
				DataInputStream fromClient = new DataInputStream(sockets[i].getInputStream());
				String clientMessage = fromClient.readLine();
				
				clientHandlers[i] = new ClientHandler(clientMessage, sockets, i);
				clientHandlers[i].start();				
			}
		}
    }  
    
}
