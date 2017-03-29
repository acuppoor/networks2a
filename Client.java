import java.net.Socket;
import java.util.Scanner;
public class Client {
    private static String portNumber = "6789"; // should be changed to an invalid one like 'abcd'

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a port number of 4 digits: ");
        try{
            while(!isDigit(portNumber)){
                portNumber = scanner.next();
                portNumber = portNumber.trim();
                System.out.println("PortNumber: " + portNumber);
            }
        } catch (Exception e){
          System.out.println("The following error has occurred: \n" + e.getStackTrace());
          portNumber = "6789"; // default portnumber
        }
        
        Socket socket = null;
        try{
            socket = new Socket("localhost", Integer.parseInt(portNumber));
        } catch (Exception e){
            e.printStackTrace();
            System.exit(11); // random exit code, no special reason for the exit code 11
        }
        
        // Need to create a loop which will ask the user to enter information and print response from server if there's any

    }
    
    private static boolean isDigit(String s){
        // need to write the logic to check whether the port number is of digits only
        return true;
    }
}

