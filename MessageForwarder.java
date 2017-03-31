package threads;

import overriden.ServerSocketM;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Arjun on 3/31/2017.
 */
public class MessageForwarder extends Thread {
    private ServerSocketM[] serverSockets;
    private Socket socket;
    private int senderIndex;
    private String message;

    public MessageForwarder(ServerSocketM[] serverSockets, Socket socket, int senderIndex, String message){
        this.serverSockets = serverSockets;
        this.socket = socket;
        this.senderIndex = senderIndex;
        this.message = message;
    }

    public void run(){
        try{
            for (int i = 0; i < serverSockets.length; i++) {
                if(i != senderIndex && serverSockets[i].isAccepted()){
                    PrintWriter p = new PrintWriter(serverSockets[i].getAcceptedSocket().getOutputStream());
                    System.out.println("Message sent to " + serverSockets[i].getAcceptedSocket().getLocalPort());
                    p.write("Message From " + serverSockets[senderIndex].getLocalPort() + " : " + message+"\n");
                    p.flush();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
