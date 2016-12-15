package Tic_Tac_Toe;
import javax.swing.*;
import java.awt.*;

/**
 * Created by effie on 16/12/15.
 */
public class looseDialog extends JDialog
{
    protected JButton buttonOK, buttonCancel;
    public looseDialog()
    {
        super();
        setSize(400, 180);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel panel1 = new JPanel();
        JLabel label = new JLabel("you loose");
        label.setVisible(true);
        panel1.add(label);
        panel1.setVisible(true);
        panel.add(panel1);

        buttonOK = new JButton("Ok");
        buttonOK.setVisible(true);
        buttonCancel = new JButton("Cancel");
        buttonCancel.setVisible(true);
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setVisible(true);
        panel2.add(buttonOK);
        panel2.add(buttonCancel);
        panel.add(panel2);
        panel.setVisible(true);

        this.add(panel);
        this.setVisible(true);
    }
}
