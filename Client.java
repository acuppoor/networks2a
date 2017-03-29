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

        Socket socket = null;
        try{
            socket = new Socket("localhost", Integer.parseInt(portNumber)); // creates a socket with the given port number
        } catch (Exception e){
            e.printStackTrace();
            System.exit(11); // random exit code, no special reason for the exit code 11
        }
        System.out.println("----- Chat Start -----");

        while (!sentence.equalsIgnoreCase("exit")) {
            DataOutputStream toServer = null;
            try{
                toServer= new DataOutputStream(socket.getOutputStream()); // to send messages to server
            } catch (Exception e){

            }

            DataInputStream fromServer = null;
            try {
                fromServer = new DataInputStream(socket.getInputStream()); // to get messages from server
            } catch (Exception e){

            }
            System.out.print("Me: ");
            sentence = scanner.nextLine(); // read the message typed in by the user
            
            try {
                toServer.writeBytes(sentence + '\n'); // write the message to the server
            } catch (Exception e){
                System.out.println("Message not sent, error: \n" + e.getStackTrace());
            }
            try {
                System.out.println("From server: " + fromServer.readLine()); // read reply from server
            } catch (Exception e){
                System.out.println("Error from server: \n" + e.getStackTrace());
            }
        }

        try {
            socket.close();
        } catch (Exception e){
            System.out.println("Error in closing socket: " + e.getStackTrace());
        }

        System.out.println("----- Chat Start -----");
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
