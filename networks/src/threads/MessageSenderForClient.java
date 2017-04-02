package threads;

import main.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Arjun on 3/31/2017.
 */
public class MessageSenderForClient extends Thread {
    private PrintWriter p;
    private BufferedReader bufferedReader;
    private Socket socket;
    private volatile boolean interrupted = false;

    public MessageSenderForClient(Socket socket){ // Reads message from client and sends it to server
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            // p = new PrintWriter(socket.getOutputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(!interrupted && !socket.isClosed() && socket.isConnected()) {
            try {
                String message = bufferedReader.readLine(); // standard input


                if(message.trim().equalsIgnoreCase("@exit")){ // Exiting the chat
                    this.interrupt();
                } else if (message.toLowerCase().contains("@sendfile:")){ // Sending file to server

                    // Extracting the path of the file
                    String path;
                    try {
                        path = message.substring(message.indexOf(':'), message.length()).trim();
                    } catch (Exception e){
                        System.out.println("(Path Extraction Error)");
                        path = "C:\\Users\\Arjun\\Desktop\\Networks2a\\src\\main\\dog.png";
                    }

                    path = "C:\\Users\\Arjun\\Desktop\\Networks2a\\src\\main\\dog.png"; // Need to be removed later

                    OutputStream out = socket.getOutputStream();


                    // Requestiong the server to receive a file from this client
                    p = new PrintWriter(out); // get the output stream to the server
                    p.println("@ReceiveFileRequest: File Received from port " + Client.m1.get(socket.getLocalPort())); // sends the message to the server
                    p.flush(); // so that the bytes get to the server
                    System.out.println("(Server has been requested to receive the file)");

                    // Loading the file
                    File file = new File(path);
                    byte[] bytes = new byte[16*1024];
                    FileInputStream fileReader = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileReader);

                    System.out.println("(Sending File...)");

                    // File sending started
                    int count = bufferedInputStream.read(bytes);
                    while (count > 0) {
                        out.write(bytes, 0, count);
                        count = fileReader.read(bytes);
                    }
                    bufferedInputStream.close();
                    System.out.println("(File Sent)");

                    // Notifying the server that file has been sent, hence it can start reading the file
                    p = new PrintWriter(out); // get the output stream to the server
                    p.println("@FileReceived: File Received from port " + Client.m1.get(socket.getLocalPort())); // sends the message to the server
                    p.flush(); // so that the bytes get to the server

                    System.out.println("(Server has been notified of file received)");

                } else {
                    // Sending messages that the client has typed into the shell
                    p = new PrintWriter(socket.getOutputStream()); // get the output stream to the server
                    p.println(message); // sends the message to the server
                    p.flush(); // so that the bytes get to the server
                    System.out.println("(Message Sent)");

                }
            } catch (java.net.SocketException e1){
                e1.printStackTrace();
                this.interrupt();
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //System.exit(0);
    }

    public void interrupt(){
        this.interrupted = true;
        try{
            if(bufferedReader != null) {
                bufferedReader.close();
            }

            if(p!= null){
                p.close();
            }

            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
