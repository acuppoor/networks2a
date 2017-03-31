package threads;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Arjun on 3/31/2017.
 */
public class MessageReaderAndSenderForClient extends Thread {
    private PrintWriter p;
    private BufferedReader bufferedReader;
    private Socket socket;
    private volatile boolean interrupted = false;

    public MessageReaderAndSenderForClient(Socket socket){
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            p = new PrintWriter(socket.getOutputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(!interrupted) {
            try {
                String message = bufferedReader.readLine();
                p = new PrintWriter(socket.getOutputStream());
                p.println(message);
                p.flush();
                System.out.println("(Message Sent)");
            } catch (java.net.SocketException e1){
                e1.printStackTrace();
                this.interrupt();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void interrupt(){
        this.interrupted = true;
    }
}
