package Tic_Tac_Toe;
import java.net.*;
import java.io.*;
import java.util.Map;

/**
 * Created by effie on 16/12/14.
 */
public class ServerThread extends Thread
{
    Tic_Tac_Toe game;
    gamer gamer1;
    gamer gamer2;

    public ServerThread(gamer g1, gamer g2, Tic_Tac_Toe g)
    {
        gamer1 = g1;
        gamer2 = g2;
        game = g;
    }


    public void run()
    {
        try
        {
            String str;
            String data;

            do
            {
                str = gamer1.dataFromClient.readLine();

                if(str.equals("pos"))
                {
                    Integer i, j;
                    str = gamer1.dataFromClient.readLine();
                    i = Integer.valueOf(str);
                    str = gamer1.dataFromClient.readLine();
                    j = Integer.valueOf(str);

                    if(gamer1.valid)
                    {
                        if (!game.put(i, j, gamer1))
                        {
                            //the process of putting the piece was not successful
                        }
                        else
                        {
                            if (game.Judge())
                            {
                                gamer1.dataToClient.writeBytes("win" + '\n');
                                gamer2.dataToClient.writeBytes("loose" + '\n');
                                game.end = true;
                            }
                            else if(game.noWinning)
                            {
                                gamer1.dataToClient.writeBytes("loose" + '\n');
                                gamer2.dataToClient.writeBytes("loose" + '\n');
                                game.end = true;
                            }
                        }
                    }
                }

                data = gamer2.dataFromClient.readLine();
                if(data.equals("pos"))
                {
                    Integer i, j;
                    data = gamer2.dataFromClient.readLine();
                    i = Integer.valueOf(data);
                    data = gamer2.dataFromClient.readLine();
                    j = Integer.valueOf(data);

                    if (gamer2.valid)
                    {
                        if (!game.put(i, j, gamer2))
                        {
                            //the process of putting the piece was not successful
                        }
                        else
                        {
                            if(game.Judge())
                            {
                                gamer1.dataToClient.writeBytes("loose" + '\n');
                                gamer2.dataToClient.writeBytes("win" + '\n');
                                game.end = true;
                            }
                            else if(game.noWinning)
                            {
                                gamer1.dataToClient.writeBytes("loose" + '\n');
                                gamer2.dataToClient.writeBytes("loose" + '\n');
                                game.end = true;
                            }
                        }
                    }
                }
            }while(!str.equals("end") && !game.end && !data.equals("end"));

        }
        catch(Exception e)
        {
            System.err.println("Exception at ServerThread");
        }
    }

}
