package Tic_Tac_Toe;
import java.net.*;
import java.util.*;

/**
 * Created by effie on 16/12/15.
 */
public class CheckThread extends Thread
{
    Map<gamer, Socket> socketMap;
    gamer g;

    public CheckThread(Map<gamer, Socket> m, gamer myGamer)
    {
        socketMap = m;
        g = myGamer;
    }

    public void run()
    {
        try
        {
            String str;

            do
            {
                if(g.isPlaying)
                    break;

                str = g.dataFromClient.readLine();
                if (str.equals("change"))
                {
                    String addr = g.dataFromClient.readLine();
                    str = g.dataFromClient.readLine();
                    int port = Integer.valueOf(str);
                    System.out.println("connecting");
                    for (Map.Entry<gamer, Socket> entry : socketMap.entrySet()) {
                        if ((entry.getKey().addr.getHostName().equals(addr) || entry.getKey().addr.getHostAddress().equals(addr))
                                && entry.getKey().port == port && !entry.getKey().isPlaying) {
                            g.valid = true;
                            Tic_Tac_Toe game = new Tic_Tac_Toe(g, entry.getKey());
                            g.isPlaying = true;
                            entry.getKey().isPlaying = true;
                            g.side = 1;
                            entry.getKey().side = 2;
                            g.dataToClient.writeBytes("ok" + '\n');
                            entry.getKey().dataToClient.writeBytes("requesting" + '\n');

                            ServerThread thread = new ServerThread(g, entry.getKey(), game);
                            thread.start();
                            break;
                        }
                    }
                }

                if(g.isPlaying)
                {
                    this.sleep(1000);
                }
            }while(!g.valid);
        }
        catch(Exception e)
        {
            System.err.println("Exception at connecting");
        }
    }

}
