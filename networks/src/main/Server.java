package main;

import overriden.ServerSocketM;
import threads.*;

import java.io.*;
import java.util.Scanner;
import java.net.Socket;

public class Server {
public static volatile Socket senderSocket;
    public static void main(String[] args) throws IOException{ //Exceptions thrown for method
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many people will chat?");
        int numberOfPeople = 2;
       senderSocket = null;
     

        try{
            numberOfPeople = scanner.nextInt();
        } catch (Exception e){
            numberOfPeople = 2;
            System.out.println("Number of people is assumed to be 2, error with the number of people entered:" + e.getStackTrace());
        }

        ServerSocketM[] serverSockets = new ServerSocketM[numberOfPeople];
        String[] portNumbers = new String[numberOfPeople];

        System.out.println("Enter the " + numberOfPeople + " port numbers (separated by space):" );

        for (int i = 0; i < numberOfPeople; i++){
            portNumbers[i] = scanner.next(); // reads the port number from the user
            serverSockets[i] = new ServerSocketM(Integer.parseInt(portNumbers[i])); // creates a serversocket for the port number
        }

        System.out.println("----- Server Started -----");
        MessageReaderForServer[] m = new MessageReaderForServer[numberOfPeople];
        for (int i = 0; i < numberOfPeople; i++) {
            m[i] = new MessageReaderForServer(serverSockets, i); // a message reader is created for every client - reads message from client and forward it to others
            m[i].start(); // starts the thread
        }

        for (int i = 0; i < numberOfPeople; i++) {
            try {
                m[i].join(); // just to prevent the server from going offline
            } catch (Exception e){
                //e.printStackTrace();
            }
        }
        System.out.println("----- Server Ended -----");

    }

}
