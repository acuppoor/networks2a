package threads;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
                InputStream inputStream = socket.getInputStream();
                this.b = new BufferedReader(new InputStreamReader(inputStream)); // gets messages from server
                String message = b.readLine(); // reads the messages
                System.out.println("(Message Received)");
                if (message == null){
                    System.out.println("Server Went Offline, Please Connect Later!");
                    this.interrupt();
                    System.exit(0);
                } else {
                    if (message.equalsIgnoreCase("@receivefilerequest:")) {
                        // read file from output stream and save
                        File file = new File("/home/c/cppkus001/Desktop/Networks2a/src/received_file/client/received_" + socket.getLocalPort() + ".png");
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] bytes = new byte[16 * 1024];
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                        int read = bufferedInputStream.read(bytes);

                        System.out.println("(Receiving File)");
                        while (read > 0) {
                            // writing what has been read to the file variable
                            bufferedOutputStream.write(bytes, 0, bytes.length);

                            // reading further bytes
                            if (bufferedInputStream.available() > 0) { // checks if there's anything to copy more
                                read = bufferedInputStream.read(bytes);
                            } else {
                                read = 0; // if nothing, read is set to zero and hence breaking the loop
                            }
                        }
                        // since File.close() does not exist, below is a way of closing the file
                        //file = null;
                        //System.gc();

                        bufferedOutputStream.flush();
                        System.out.println("(File Received)");

                    } else {
                        System.out.println(message); // print messages from other clients
                    }
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
