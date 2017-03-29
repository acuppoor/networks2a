/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 *
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
public class Client {
    private static String ServerportNumber =  "1025"; // predefined
    private static String ServerHostName="137.158.58.117"; // localhost
    

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
   
        
        
        Socket socket = null;
        try{
            socket = new Socket(ServerHostName, Integer.parseInt(ServerportNumber));
        } catch (Exception e){
            e.printStackTrace();
            System.exit(11); // random exit code, no special reason for the exit code 11
        }
        
        // Need to create a loop which will ask the user to enter information and print response from server if there's any
        
        
           DataOutputStream  output ;
        try
        {
         String S=scanner.nextLine();
         output = new DataOutputStream ( socket . getOutputStream ( ) ) ;
         output.writeUTF(S);
        }
    catch  ( IOException  e )
{
System . out . println ( e ) ;
}
        // input stream
    DataInputStream  input ;
    try
    {
    //input = new DataInputStream (socket. getInputStream ( ) )  ; // dowes not convert bytes to char properly
    BufferedReader reader=new BufferedReader(new InputStreamReader(socket. getInputStream ( )));
     //System.out.print(reader.readLine());
    }
    catch  ( IOException  e )
    {
        System . out . println ( e ) ;

    }
 

}
}