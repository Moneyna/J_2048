package src.com.Moneyna.J_2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Help extends JDialog
{
    private Image img;
    private int tag=0;
    private JLabel b = new JLabel();

    private String[] image = new String[3];

    public Help()
    {
        setTitle("游戏说明");
        img = Toolkit.getDefaultToolkit().getImage("title.png");
        setModal(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(img);
        setSize(400,400);
        setResizable(false);

        setLocationRelativeTo(null);

        image[0]="bug0.png";
        image[1]="bug1.png";
        image[2]="bug2.png";

        Icon bug = new ImageIcon(image[0]);
        b = new JLabel("按键盘  D  建继续",bug,SwingConstants.CENTER);
        b.setFont(new Font("楷体",Font.BOLD,15));
        b.setHorizontalTextPosition(SwingConstants.CENTER);
        b.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(b,BorderLayout.CENTER);

        this.addKeyListener(
                new KeyAdapter() {
                    public void keyPressed(KeyEvent e)
                    {
                        int key= e.getKeyCode();
                        if(key==KeyEvent.VK_D)
                        {
                            tag=(++tag)%3;
                            b.setIcon(new ImageIcon(image[tag]));
                        }
                    }
                }
        );
        setVisible(true);
    }

}