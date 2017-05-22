/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *

 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
public class ClientHandler extends Thread  {
    Socket serverSocket;
     Scanner s = new Scanner(System.in);
    ClientHandler(Socket ss)
    {   serverSocket=ss;
    }
    
    @Override
    public void run() {
        
                // input stream
    DataInputStream  input ;
    try
    {
        //input stream
    //input = new DataInputStream (socket. getInputStream ( ) )  ; // dowes not convert bytes to char properly
    BufferedReader reader=new BufferedReader(new InputStreamReader(serverSocket. getInputStream ( )));
    // System.out.print(reader.readLine());
    }
    catch  ( IOException  e )
    {
        System . out . println ( e ) ;

    }
    //output stream
    PrintStream output;
    try
    {
        output = new PrintStream ( serverSocket . getOutputStream ( ) ) ;
        String S=s.nextLine();
        output.print(S);
    }
        
    
     catch  ( IOException  e )
    {
        System . out . println ( e ) ;

    }
    //
    
}}
