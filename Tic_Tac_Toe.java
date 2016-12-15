package Tic_Tac_Toe;
import java.io.IOException;
import java.net.*;

/**
 * Created by effie on 16/12/12.
 */
public class Tic_Tac_Toe
{
    private int[][] map;

    public static final int x = 1;
    public static final int o = 2;

    public gamer gamer1;
    public gamer gamer2;

    public boolean end;
    public boolean noWinning;

    public Tic_Tac_Toe(gamer g1, gamer g2)
    {
        map = new int[3][3];
        int i, j;
        for(i = 0; i < 3; i++)
        {
            for(j = 0; j < 3; j++)
            {
                map[i][j] = 0;
            }
        }
        end = false;
        noWinning = false;

        gamer1 = g1;
        gamer2 = g2;
    }

    public boolean Judge()
    {
        //对当前落子顺序进行判断，是否比赛结束
        boolean result = false;
        //先进行行判断
        int i;
        for(i = 0; i < 3; i++)
        {
            if(map[i][0] != 0 && map[i][0] == map[i][1] && map[i][0] == map[i][2])
            {
                result = true;
                break;
            }
        }

        if(!result)
        {
            //行判断没有成功，再进行列判断
            for(i = 0; i < 3; i++)
            {
                if(map[0][i] != 0 && map[0][i] == map[1][i] && map[0][i] == map[2][i])
                {
                    result = true;
                    break;
                }
            }
        }

        if(!result)
        {
            //行、列判断均没有成功，再进行对角线的判断
            if (map[0][0] != 0 && map[0][0] == map[1][1] && map[0][0] == map[2][2])
            {
                result = true;
            }

            if (map[0][2] != 0 && map[0][2] == map[1][1] && map[0][2] == map[2][0])
            {
                result = true;
            }
        }

        noWinning = true;
        for(i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(map[i][j] == 0)
                {
                    noWinning = false;
                    break;
                }
            }
            if(!noWinning)
                break;
        }
        return result;
    }

    boolean put(Integer i, Integer j, gamer side)
    {
        //put down the piece
        if(i < 0 || i > 2 || j < 0 || j > 2)
            return false;

        boolean result = false;
        if(map[i][j] == 0)
        {
            if(side == gamer1)
            {
                map[i][j] = x;
                gamer1.valid = false;
                gamer2.valid = true;

                try
                {
                    gamer1.dataToClient.writeBytes("put" + '\n');
                    gamer1.dataToClient.writeBytes("x" + '\n');
                    gamer1.dataToClient.writeBytes(i.toString() + '\n');
                    gamer1.dataToClient.writeBytes(j.toString() + '\n');

                    gamer2.dataToClient.writeBytes("put" + '\n');
                    gamer2.dataToClient.writeBytes("x" + '\n');
                    gamer2.dataToClient.writeBytes(i.toString() + '\n');
                    gamer2.dataToClient.writeBytes(j.toString() + '\n');
                }
                catch(IOException e)
                {

                }
            }
            else
            {
                map[i][j] = o;
                gamer1.valid = true;
                gamer2.valid = false;

                try
                {
                    gamer1.dataToClient.writeBytes("put" + '\n');
                    gamer1.dataToClient.writeBytes("o" + '\n');
                    gamer1.dataToClient.writeBytes(i.toString() + '\n');
                    gamer1.dataToClient.writeBytes(j.toString() + '\n');

                    gamer2.dataToClient.writeBytes("put" + '\n');
                    gamer2.dataToClient.writeBytes("o" + '\n');
                    gamer2.dataToClient.writeBytes(i.toString() + '\n');
                    gamer2.dataToClient.writeBytes(j.toString() + '\n');
                }
                catch(IOException e)
                {

                }
            }
            result = true;
        }

        return result;
    }
}
