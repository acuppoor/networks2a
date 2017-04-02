package threads;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Arjun on 3/31/2017.
 */
public class MessageReaderFromServerForClient extends Thread {
    private BufferedReader b;
    private Socket socket;
    private volatile boolean interrupted = false;

    public MessageReaderFromServerForClient(Socket socket){
        this.socket = socket; // client socket
        try {
            // this.b = new BufferedReader(new InputStreamReader(socket.getInputStream())); // gets messages from server
            // this.d = new DataInputStream(socket.getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(!interrupted && !socket.isClosed()) {
            try {
                // todo needs to add code to download file if one can be received from server
                System.out.println("(Waiting for messages or Type in a message to send)");
                this.b = new BufferedReader(new InputStreamReader(socket.getInputStream())); // gets messages from server
                String message = b.readLine(); // reads the messages
                System.out.println("(Message Received)");
                if (message == null){
                    System.out.println("Server Went Offline, Please Connect Later!");
                    this.interrupt();
                } else {
                    System.out.println(message); // print messages from other clients
                }
            } catch (java.net.SocketException e1) {
                //e1.printStackTrace();
                this.interrupt(); // socket must have been closed and hence an interruption is needed
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //System.exit(0);
    }

    public void interrupt(){
        this.interrupted = true;
        try {
            b.close();
        } catch (Exception e){

        }
    }
}
