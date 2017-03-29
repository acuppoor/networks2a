//aaa
public class Server {
   ServerSocket serverPort;
    Socket clientPort;
    
    public static void main(String[] args){
        //Get how many people in chat
        Scanner s = new Scanner(System.in);
        System.out.print("How many people are joining the chat? ");
        int people = Integer.parseInt(s.next());
            
    }
    
    public void createServerSockets(){
        try {
            ServerSocket ss = new ServerSocket();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void checkForConnection(){
        while (true) {            
            clientPort = ss.accept();
        }
    }
    
}
