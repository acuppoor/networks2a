/**
 * Created by Arjun on 3/30/2017.
 */
package main;


import threads.MessageReaderFromServerForClient;
import threads.MessageSenderForClient;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static String portNumber = "abcd";
    public String INPUT_LOCK = "";
    public String OUTPUT_LOCK = "";
    public static Map m1 ;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); // to get port number and messages from user
        
        try{
            while(!isDigit(portNumber)){
                System.out.print("Enter a port number of 4 digits:\n");
               
                portNumber = scanner.nextLine();
                portNumber = portNumber.trim();
                System.out.print("Enter  your student number:\n");
                String stu_no=scanner.nextLine();
                m1= new HashMap(); 
                m1.put(portNumber, stu_no);
            }
            System.out.println("PortNumber: " + portNumber);
        } catch (Exception e){
            System.out.println("The following error has occurred: \n" + e.getStackTrace());
            portNumber = "6789"; // default portnumber in case of any error
        }

        System.out.println("----- Chat Start -----");

        try {
            Socket socket = new Socket("localhost", Integer.parseInt(portNumber)); // create a socket with the port number
            MessageReaderFromServerForClient r = new MessageReaderFromServerForClient(socket);
            MessageSenderForClient s = new MessageSenderForClient(socket);
            r.start(); // returns immediately
            s.start(); // returns immediately
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
