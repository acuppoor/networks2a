package threads;

import com.sun.org.apache.xpath.internal.SourceTree;
import overriden.ServerSocketM;

import java.io.*;
import java.net.Socket;

/**
 * Created by cppkus001 on 2017/04/01.
 */
public class FileForwarder extends  Thread{
    private ServerSocketM[] serverSockets;
    private Socket socket;
    private int senderIndex;
    private String message;
    private byte[] bytes;
    private FileOutputStream fileOutputStream;
    private PrintWriter p;
    private File file;

    public FileForwarder(ServerSocketM[] serverSockets, Socket socket, int senderIndex, String message) {
        System.out.println("Fileforwarder started");
        this.serverSockets = serverSockets;
        this.socket = socket;
        this.senderIndex = senderIndex;
        this.message = message;
        this.bytes = new byte[16*1024];
        file = new File("/home/c/cppkus001/Desktop/Networks2a/src/main/received.png");
        try {
            this.fileOutputStream = new FileOutputStream(file);
        } catch (Exception e){

        }
        System.out.println("Fileforwarder finished");
    }

    public void run(){
        try{
            System.out.println("getting inputstream");
            InputStream in = socket.getInputStream();
            System.out.println("inputStreamObtained");
            int count = in.read(bytes);
            while(count > 0){
                fileOutputStream.write(bytes, 0, count);
            }
            System.out.println("File copied to server!");

            for (int i = 0; i < serverSockets.length; i++) {
                if(i != senderIndex && serverSockets[i].isAccepted()) {
                    FileInputStream fileReader = new FileInputStream(file);
                    count = fileReader.read(bytes);
                    OutputStream out = serverSockets[i].getAcceptedSocket().getOutputStream();
                    while(count > 0){
                        out.write(bytes, 0, count);
                        count = fileReader.read(bytes);
                    }
                    out.flush();

                    p = new PrintWriter(socket.getOutputStream()); // get the output stream to the server
                    p.println("@FileReceived: File Received from port " + socket.getLocalPort()); // sends the message to the server
                    p.flush(); // so that the bytes get to the server
                }
            }
            //"Message From " + serverSockets[senderIndex].getLocalPort() + " : " +
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
