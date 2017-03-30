import java.net.*;
import java.io.*;

public class  ClientHandler extends Thread
{	private String message;
	private Socket[] sockets;
	private int senderIndex;
	
		ClientHandler(String message, Socket[] sockets, int senderIndex)
	{	this.message = message;
		this.sockets = sockets;
		this.senderIndex = senderIndex;
	}
	
	@Override
	public void run()
	{
		try{
			for(int i = 0; i < sockets.length; i++){
				if(i != senderIndex){
					DataOutputStream toClient = new DataOutputStream(sockets[i].getOutputStream());
					System.out.println("Received from " + sockets[senderIndex] + ": " + message);
					toClient.writeBytes(message);					
				}
			}
		} catch(Exception e){}
	
	}
}
