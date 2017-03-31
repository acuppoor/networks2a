package threads;

import overriden.ServerSocketM;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Arjun on 3/31/2017.
 */
public class MessageReaderForServer extends Thread {
    private int senderIndex;
    private ServerSocketM[] serverSockets;
    private Socket socket;
    private volatile boolean interrupted = false;

    public MessageReaderForServer(ServerSocketM[] serverSockets, int senderIndex, Socket[] sockets){
        this.senderIndex = senderIndex;
        this.serverSockets = serverSockets;
        try{
            socket = serverSockets[senderIndex].accept();
            System.out.println("Port " + socket.getLocalPort() + " has joined the chat.");
        } catch (Exception e){
            e.printStackTrace(); System.exit(11);
        }
    }

    public void run(){
        while(!interrupted){
            try{
                //System.out.println("(Reading messages from client " + socket.getLocalPort() + ")");
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                String message = fromClient.readLine();
                System.out.println("Message Received from " + socket.getLocalPort() + " : " + message);
                new MessageForwarder(serverSockets, socket, senderIndex, message).start();
            } catch (Exception e){
                e.printStackTrace();
                this.interrupt();
            }
        }
    }

    public void interrupt(){
        this.interrupted = true;
    }
}
