package Tic_Tac_Toe;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by effie on 16/12/14.
 */
public class Server
{
    static int ClientNum = 0;
    static Map<gamer, Socket> socketMap;

    public static void main(String args[]) throws Exception
    {
        ServerSocket welcome = new ServerSocket(6789);
        socketMap = new HashMap<>();

        while(true)
        {
            Socket socketServer = welcome.accept();
            gamer g = new gamer(socketServer);
            socketMap.put(g, socketServer);
            ClientNum++;
            //ServerThread thread;
            //thread = new ServerThread(socketServer, g, socketMap);
            //thread.start();

            g.dataToClient.writeBytes(g.addr.getHostAddress() + '\n');
            g.dataToClient.writeBytes(new Integer(g.port).toString() + '\n');
            CheckThread check = new CheckThread(socketMap, g);
            check.start();
        }

    }

}
