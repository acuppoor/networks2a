/**
 * Created by Arjun on 3/30/2017.
 */
package main;

import threads.MessageReaderAndSenderForClient;
import threads.MessageReaderFromServerForClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String portNumber = "abcd";

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); // to get port number and messages from user
        String sentence = "InitialSentence"; // Initial vaue for sentence
        try{
            while(!isDigit(portNumber)){
                System.out.print("Enter a port number of 4 digits:\n");
                portNumber = scanner.nextLine();
                portNumber = portNumber.trim();
            }
            System.out.println("PortNumber: " + portNumber);
        } catch (Exception e){
            System.out.println("The following error has occurred: \n" + e.getStackTrace());
            portNumber = "6789"; // default portnumber in case of any error
        }

        System.out.println("----- Chat Start -----");

        /*
        while (!sentence.equalsIgnoreCase("exit")) {
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1", Integer.parseInt(portNumber)); // creates a socket with the given port number

                DataOutputStream toServer = null;
                toServer = new DataOutputStream(socket.getOutputStream()); // to send messages to server
                new MessageReaderAndSenderForClient(toServer, scanner).start();

                DataInputStream fromServer = null;
                fromServer = new DataInputStream(socket.getInputStream()); // to get messages from server
                new MessageReaderFromServerForClient(fromServer).start();

            } catch (Exception e){
                //e.printStackTrace();
            }

        }*/
        try {
            Socket socket = new Socket("localhost", Integer.parseInt(portNumber));
            MessageReaderFromServerForClient r = new MessageReaderFromServerForClient(socket);
            MessageReaderAndSenderForClient s = new MessageReaderAndSenderForClient(socket);
            r.start();
            s.start();
            r.join();
            s.join();
            System.out.println("----- Chat End -----");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isDigit(String s){
        char c;
        for(int i = 0; i < s.length(); i++){
            c = s.charAt(i);
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
