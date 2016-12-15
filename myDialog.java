package Tic_Tac_Toe;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by effie on 16/12/14.
 */
public class myDialog extends JDialog
{
    public JTextField mAddr, mPort;
    public JButton Ok,Cancel;
    protected JLabel label1, label2;

    myDialog()
    {
        super();
        setSize(400, 180);
        setResizable(false);

        mAddr = new JTextField();
        mPort = new JTextField();
        Ok = new JButton("确定");
        Cancel = new JButton("取消");
        label1 = new JLabel("IP: ");
        label2 = new JLabel("Port: ");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));


        panel.add(label1);
        panel.add(mAddr);
        panel.add(label2);
        panel.add(mPort);
        panel.add(Ok);
        panel.add(Cancel);

        this.add(panel);
        this.setVisible(true);
        initEvent();
    }

    void initEvent()
    {

        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
