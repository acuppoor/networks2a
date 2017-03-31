package main;

import overriden.ServerSocketM;
import threads.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException{ //Exceptions thrown for method
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many people will chat?");
        int numberOfPeople = 2;

        try{
            numberOfPeople = scanner.nextInt();
        } catch (Exception e){
            numberOfPeople = 2;
            System.out.println("Number of people is assumed to be 2, error with the number of people entered:" + e.getStackTrace());
        }

        ServerSocketM[] serverSockets = new ServerSocketM[numberOfPeople];
        String[] portNumbers = new String[numberOfPeople];
        Socket[] sockets = new Socket[numberOfPeople];

        System.out.println("Enter the " + numberOfPeople + " port numbers (separated by space):" );

        for (int i = 0; i < numberOfPeople; i++){
            portNumbers[i] = scanner.next();
            serverSockets[i] = new ServerSocketM(Integer.parseInt(portNumbers[i]));
        }

        System.out.println("----- Server Started -----");
        MessageReaderForServer[] m = new MessageReaderForServer[numberOfPeople];
        for (int i = 0; i < numberOfPeople; i++) {
            m[i] = new MessageReaderForServer(serverSockets, i, sockets);
            m[i].start();
        }

        for (int i = 0; i < numberOfPeople; i++) {
            try {
                m[i].join();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        /*
        sockets[0] = serverSockets[0].accept();
        while(true){
                try {
                    System.out.println(new DataInputStream(sockets[0].getInputStream()).readLine());
                } catch (Exception e){
                    e.printStackTrace();
                }

        }
        */

        //System.out.println("----- Server Ended -----");

    }

}
