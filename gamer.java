package Tic_Tac_Toe;
import java.net.*;
import java.io.*;

/**
 * Created by effie on 16/12/14.
 */
public class gamer
{
    public InetAddress addr;
    public int port;
    public boolean valid;
    public int side;
    public BufferedReader dataFromClient;
    public DataOutputStream dataToClient;
    Socket socket;
    public boolean isPlaying;

    public gamer(Socket s)
    {
        socket = s;
        InetAddress a = socket.getInetAddress();
        int p = socket.getPort();
        addr = a;
        System.out.println(addr.getHostAddress());
        port = p;
        System.out.println(port);
        valid = false;

        try
        {
            dataFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataToClient = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException e)
        {

        }
        isPlaying = false;
    }
}
