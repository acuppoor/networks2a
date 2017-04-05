package threads;

import jdk.internal.util.xml.impl.Input;
import main.Client;
import main.Server;
import overriden.ServerSocketM;

import java.io.*;
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
    private BufferedReader fromClient;
    private static volatile boolean fileWaitingToBeSent = false;
    ///private static volatile Socket senderSocket = null;
    private File file;

    public MessageReaderForServer(ServerSocketM[] serverSockets, int senderIndex){
        this.senderIndex = senderIndex;
        this.serverSockets = serverSockets;
        try{
            socket = serverSockets[senderIndex].accept(); // accepts the connection from a client(socket)
            System.out.println("Port " + socket.getLocalPort() + " has joined the chat.");
            new MessageForwarder(serverSockets, socket, senderIndex, "From Server: " + socket.getLocalPort() + " has joined the chat.").start();
        } catch (Exception e){
            e.printStackTrace(); 
            System.exit(11);
        }
    }

    public void run(){
        String message = ""; // initialising the variable
        while(!interrupted && !serverSockets[senderIndex].getAcceptedSocket().isClosed()){
            try{
                // Reading message from client
                InputStream inputStream = socket.getInputStream();
                fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                message = fromClient.readLine(); // read the message of the client

                // Check whether the message is encoded or null or a proper one.
                if(message != null) {

                    // Encoded message: Telling the server that it is receiving a file
                    if(message.toLowerCase().contains("@receivefilerequest:")){

                        System.out.println("Client " + socket.getLocalPort() + " has made a request to send a file");

                        String path = "C:\\Users\\Student.SCI000A083\\Documents\\NetBeansProjects\\networks\\src\\main\\dog.png";

                        // Creating the file, a filereader and a filewriter


                        file = new File(path);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] bytes = new byte[16*1024];
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                        int read = bufferedInputStream.read(bytes);

                        System.out.println("(Receiving File)");
                        while(read>0){
                            // writing what has been read to the file variable
                            bufferedOutputStream.write(bytes, 0, bytes.length);

                            // reading further bytes
                            if(bufferedInputStream.available() > 0) { // checks if there's anything to copy more
                                read = bufferedInputStream.read(bytes);
                            } else {
                                read = 0; // if nothing, read is set to zero and hence breaking the loop
                            }
                        }

                        bufferedOutputStream.flush();

                        System.out.println("(File Received)");
                        System.out.println("(Other Clients Requested To Accept File)");
                        //fileWaitingToBeSent = true;
                      Server.senderSocket = socket;
                        new MessageForwarder(serverSockets, socket, senderIndex, ("Message From " + socket.getLocalPort() + " : Please accept a file from me. Type '@AcceptFile' or '@RejectFile'")).start();
                        //new FileForwarder(serverSockets, socket, new File("")).start();
                    } else if (message.trim().toLowerCase().contains("@acceptfile")){
                       // if(fileWaitingToBeSent) {
                            System.out.println("(File Being Sent To Client)"+socket.getLocalPort());
                           // fileWaitingToBeSent = false;

                            // Sends files to other clients

                            FileForwarder f = new FileForwarder(serverSockets, file,socket,senderIndex);
                            f.start();
                            f.join();
                            System.out.println("(File Sent To Client)"+socket.getLocalPort());
                       // }
                    } else {
                        new MessageForwarder(serverSockets, socket, senderIndex, ("Message From " + socket.getLocalPort() + " : " + message)).start();
                    }
                } else {
                    this.interrupt();
                    new MessageForwarder(serverSockets, socket, senderIndex, ("Server: Port " + socket.getLocalPort() + " has left the chat.")).start();
                }


            } catch (Exception e){
                e.printStackTrace();
                this.interrupt();
            }
        }
    }

    public void interrupt(){
        this.interrupted = true;
        try{
            fromClient.close();
            //socket.close();
            serverSockets[senderIndex].getAcceptedSocket().close();
            serverSockets[senderIndex].close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
