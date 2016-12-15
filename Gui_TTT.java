package Tic_Tac_Toe;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by effie on 16/12/12.
 */
public class Gui_TTT{
    protected JFrame mFrame;
    protected JMenuBar mBar;
    protected JMenu myMenu;
    protected JMenuItem mIP;

    protected JPanel mPanel;
    protected myDialog mDialog;

    protected JLabel bg;
    protected JLabel[] x, o;
    protected ImageIcon bg_img, x_img, o_img;

    protected String addr;
    protected Integer port;

    protected Socket socketClient;
    protected DataOutputStream dataToServer;
    protected BufferedReader dataFromServer;

    private final static int startPosx = 46;
    private final static int endPosx = 346;
    private final static int startPosy = 280;
    private final static int endPosy = 580;
    private final static int length = 100;

    public Integer i, j;

    private JLabel ipShow, portShow;
    public boolean gameStart;

    Gui_TTT()
    {
        mFrame = new JFrame();
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setSize(400, 800);

        mBar = new JMenuBar();
        myMenu = new JMenu("选项");
        mIP = new JMenuItem("选择玩家");
        myMenu.add(mIP);
        mBar.add(myMenu);
        mFrame.setJMenuBar(mBar);

        //load the images
        bg_img = new ImageIcon("src/Tic_Tac_Toe/bg.jpg");
        bg_img.setImage(bg_img.getImage().getScaledInstance(400, 700, Image.SCALE_DEFAULT));
        x_img = new ImageIcon("src/Tic_Tac_Toe/x.png");
        x_img.setImage(x_img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        o_img = new ImageIcon("src/Tic_Tac_Toe/o.png");
        o_img.setImage(o_img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        mPanel = new JPanel();
        bg = new JLabel(bg_img);
        bg.setBounds(0, 0, bg.getWidth(), bg.getHeight());
        mPanel.add(bg);

        mFrame.add(mPanel);
        x = new JLabel[9];
        o = new JLabel[9];
        addr = new String("localhost");
        port = 6789;

        mFrame.setVisible(true);

        initEvent();

        i = -1;
        j = -1;
        gameStart = false;
        getConnected();
    }

    void getConnected() {
        try {
            socketClient = new Socket(addr, port);
            dataToServer = new DataOutputStream(socketClient.getOutputStream());
            dataFromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));

            String str1;
            Integer myPort;
            str1 = dataFromServer.readLine();
            myPort = Integer.valueOf(dataFromServer.readLine());
            ipShow = new JLabel();
            ipShow.setText("my address number is: " + str1);
            ipShow.setBounds(10, 20, 300, 20);
            portShow = new JLabel();
            portShow.setText("my port number is: " + myPort.toString());
            portShow.setBounds(10, 40, 300, 20);
            bg.add(ipShow);
            bg.add(portShow);
            ipShow.setVisible(true);
            portShow.setVisible(true);
            bg.setVisible(true);
            bg.repaint();

            run();
        } catch (IOException e) {
        }
    }

    void initEvent() {

        mIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mDialog = new myDialog();
                mDialog.setVisible(true);
                mDialog.Ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addr = mDialog.mAddr.getText();
                        try
                        {
                            port = Integer.parseInt(mDialog.mPort.getText());
                        }
                        catch(NumberFormatException ex2)
                        {
                            System.err.println("NumberFormatException");
                        }

                        try {
                            dataToServer.writeBytes("change" + '\n');
                            dataToServer.writeBytes(addr + '\n');
                            dataToServer.writeBytes(port.toString() + '\n');
                        } catch (IOException ex2) {
                            System.err.println("IOException at IP config");
                        }
                        mDialog.dispose();
                    }
                });
            }
        });

        bg.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                if (x >= startPosx && x < endPosx && y >= startPosy && y < endPosy)
                {
                    i = (x - startPosx) / length;
                    j = (y - startPosy) / length;
                    System.out.println("i = " + i);
                    System.out.println("j = " + j);

                    try
                    {
                        dataToServer.writeBytes("pos" + '\n');
                        dataToServer.writeBytes(i.toString() + '\n');
                        dataToServer.writeBytes(j.toString() + '\n');
                    }
                    catch(IOException ex3)
                    {

                    }
                }
            }
        });
    }

    void run()
    {
        String str = new String();
        do
        {
            try
            {
                str = dataFromServer.readLine();
                if(str.equals("ok"))
                {
                    gameStart = true;
                    System.out.println("connected to the other user");
                }
                else if(str.equals("put"))
                {
                    String tag = dataFromServer.readLine();
                    str = dataFromServer.readLine();
                    i = Integer.valueOf(str);
                    str = dataFromServer.readLine();
                    j = Integer.valueOf(str);
                    JLabel label;
                    if(tag.equals("x"))
                    {
                        label = new JLabel(x_img);
                        label.setBounds(startPosx + i * length, startPosy + j * length, length, length);
                        label.setVisible(true);
                        bg.add(label);
                        bg.setVisible(true);
                        bg.repaint();
                    }
                    else if(tag.equals("o"))
                    {
                        label = new JLabel(o_img);
                        label.setBounds(startPosx + i * length, startPosy + j * length, length, length);
                        label.setVisible(true);
                        bg.add(label);
                        bg.setVisible(true);
                        bg.repaint();
                    }

                }
                else if(str.equals("requesting"))
                {
                    System.out.println("agree to connect");
                    gameStart = true;
                }
                else if(str.equals("win"))
                {
                    winDialog winning = new winDialog();
                    winning.setVisible(true);
                    winning.buttonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            winning.dispose();
                            gameStart = false;
                        }
                    });

                    winning.buttonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            winning.dispose();
                            gameStart = false;
                        }
                    });
                }
                else if(str.equals("loose"))
                {
                    looseDialog loosing = new looseDialog();
                    loosing.setVisible(true);
                    loosing.buttonOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            loosing.dispose();
                            gameStart = false;
                        }
                    });

                    loosing.buttonCancel.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            loosing.dispose();
                            gameStart = false;
                        }
                    });
                }
                else if(str.equals("change"))
                {
                    mDialog = new myDialog();
                    mDialog.setVisible(true);
                    mDialog.Ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            addr = mDialog.mAddr.getText();
                            try {
                                port = Integer.parseInt(mDialog.mPort.getText());
                            } catch (NumberFormatException ex2) {
                                System.err.println("NumberFormatException");
                            }

                            try {
                                dataToServer.writeBytes("change" + '\n');
                                dataToServer.writeBytes(addr + '\n');
                                dataToServer.writeBytes(port.toString() + '\n');
                            } catch (IOException ex2) {
                                System.err.println("IOException at IP config");
                            }
                            mDialog.dispose();
                        }
                    });
                }
            }
            catch(IOException e)
            {

            }
        }while(!str.equals("win") && !str.equals("loose"));

        if((str.equals("win") || str.equals("loose")) && !gameStart)
        {
            try
            {
                dataToServer.writeBytes("end" + '\n');
            }
            catch(IOException e)
            {

            }
            mFrame.dispose();
        }
    }


    public static void main(String args[])
    {
        Gui_TTT t = new Gui_TTT();
    }
}