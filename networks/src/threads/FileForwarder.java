package threads;

import overriden.ServerSocketM;

import java.io.*;
import java.net.Socket;
import main.Server;

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

    public FileForwarder(ServerSocketM[] serverSockets, File file,Socket socket, int senderIndex) {
        this.serverSockets = serverSockets;
        //this.socket = socket;
        this.file = file;//new File("/home/c/cppkus001/Desktop/Networks2a/src/received_file/server/receivedFrom6788.png");
        this.bytes = new byte[16*1024];
        this.senderIndex = senderIndex;
        this.socket=socket;
    }

    public void run(){
        try{
//            for (int i = 0; i < serverSockets.length; i++) {
//                System.out.println("FileForwarder: Sending to clients, serverSockets length: " + serverSockets.length
//                + " isSocket accepted: " + i + "  -- " + serverSockets[i].isAccepted());
//
//                if (serverSockets[i].isAccepted() && serverSockets[i].getAcceptedSocket()!=socket) {
//                    Socket s = serverSockets[i].getAcceptedSocket();
//                    System.out.println("FileForwarder: getting outputstream for socket " + serverSockets[i].getLocalPort());
//                    //synchronized (serverSockets[i].getLock()) {
//                        OutputStream out = s.getOutputStream();
//                          System.out.println("outputstream ok");
//                        p = new PrintWriter(out); // get the output stream to the server
//                        p.println("@ReceiveFileRequest:"); // sends the message to the server
//                        p.flush(); // so that the bytes get to the server
//
//                        byte[] bytes = new byte[16 * 1024];
//                        FileInputStream fileReader = new FileInputStream(file);
//                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileReader);
//
//                        System.out.println("(Sending File...)");
//
//                        // File sending started
//                        int count = bufferedInputStream.read(bytes);
//                        while (count > 0) {
//                            out.write(bytes, 0, bytes.length);
//                            count = fileReader.read(bytes);
//                        }
//                        bufferedInputStream.close();
//                        System.out.println("(File Sent)");
//                    //}
//                }
//            }
    // sending the file to only one client dont need a loop
            if (Server.senderSocket!=null)
            {     
            System.out.println("FileForwarder: Sending to client: " + socket.getLocalPort());
            OutputStream out =  serverSockets[senderIndex].getAcceptedSocket().getOutputStream();  // output stream to the receiver socket
            System.out.println("outputstream ok");
            p = new PrintWriter(out); // get the output stream to the server
            p.println("@ReceiveFileRequest:"); // sends the message to the server
            p.flush(); // so that the bytes get to the receiver

                        byte[] bytes = new byte[16 * 1024];
                       FileInputStream fileReader = new FileInputStream(file);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileReader);
                        
                        // File sending started
                      int count = bufferedInputStream.read(bytes);
                      while (count > 0) {
                        out.write(bytes, 0, bytes.length); // if error, remove bytes.length and put count
                        count = fileReader.read(bytes);
                      }
                      bufferedInputStream.close();
                      System.out.println("(File Sent)");
                      
                      // give notice to server that the file has been received
                     // OutputStream out2server = socket.getOutputStream();
                     // PrintWriter q = new PrintWriter(out2server); // get the output stream to the server
                     // q.println("@FileReceived: File Received from port " +  Server.senderSocket.getLocalPort()); // sends the message to the server
                      //q.flush(); // so that the bytes get to the server
                  

                      System.out.println("(Server has been notified of file received)");
          
    
            }      
        } catch (Exception e){
            //e.printStackTrace();
        }
    }
}
