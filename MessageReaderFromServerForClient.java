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
    private DataInputStream d;
    private BufferedReader b;
    private Socket socket;
    private volatile boolean interrupted = false;

    public MessageReaderFromServerForClient(Socket socket){
        this.socket = socket;
        try {
            this.b = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.d = new DataInputStream(socket.getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(!interrupted) {
            try {
                System.out.println("(Waiting for messages or Type in a message to send)");
                this.b = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //System.out.println("(InputStream obtained)");
                String message = b.readLine();
                System.out.println("(Message Received)");
                System.out.println(message);
            } catch (java.net.SocketException e1){
                e1.printStackTrace();
                this.interrupt();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void interrupt(){
        this.interrupted = true;
    }
}
