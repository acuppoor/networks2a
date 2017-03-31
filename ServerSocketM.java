package overriden;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Arjun on 3/31/2017.
 */
public class ServerSocketM  extends ServerSocket {
    private Socket socket;
    private boolean accepted = false;

    public ServerSocketM(int portNumber) throws IOException{
        super(portNumber);
    }

    @Override
    public Socket accept() throws IOException{
        socket = super.accept();
        accepted = true;
        return socket;
    }

    public boolean isAccepted(){
        return accepted;
    }

    public Socket getAcceptedSocket(){
        return (accepted? socket:null);
    }
}
