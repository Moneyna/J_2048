package src.com.Moneyna.J_2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class ShadePanel extends JPanel
{
    public ShadePanel()
    {
        super();
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g1)
    {
        Graphics2D g = (Graphics2D)g1;

        super.paintComponent(g);

        int width=getWidth();
        int height=getHeight();

        GradientPaint paint = new GradientPaint(0,0,new Color( 0, 191, 255),0,height,Color.MAGENTA);
        g.setPaint(paint);
        g.fillRect(0,0,width,height);
    }
}

class About extends JDialog
{
    private JPanel contentPane;
    private Font f1 = new Font("楷体",Font.PLAIN,15);
    private Font f2 = new Font("楷体",Font.PLAIN,20);

    private ImageIcon icon;
    private JLabel label;

    public About()
    {
        setTitle("关于我");
        Image img=Toolkit.getDefaultToolkit().getImage("title.png");
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);
        setSize(new Dimension(410,300));
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(contentPane);
        ShadePanel shadePanel = new ShadePanel();
        contentPane.add(shadePanel,BorderLayout.CENTER);
        shadePanel.setLayout(null);

        JTextArea jta1= new JTextArea("版本:Version_4\n开发者:Moneyna\n开发语言:Java\n");
        jta1.setForeground(new Color(145,135 ,145));
        jta1.setFocusable(false);
        jta1.setFont(f2);
        jta1.setEnabled(false);
        jta1.setOpaque(false);
        shadePanel.add(jta1);
        jta1.setBounds(10,30,400,100);
        icon = new ImageIcon("title.png");
        icon.setImage(icon.getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));
        label = new JLabel(icon);
        shadePanel.add(label);
        label.setBounds(270,0,130,130);

        JPanel p = new JPanel();
        p.setBounds(5,130,395,1);
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        shadePanel.add(p);

        JLabel j2 = new JLabel("" + "欢迎与我取得联系!");
        j2.setBounds(10,145,200,30);
        j2.setFont(f2);
        shadePanel.add(j2);

        JLabel MyGithub = new JLabel("Github:");
        MyGithub.setFont(f1);
        final JLabel J_Github =  new JLabel("    https://github.com/Moneyna");
        J_Github.setFont(f1);
        J_Github.setBackground(Color.WHITE);
        J_Github.addMouseListener(new InternetMonitor());
        JTextArea Copyright = new JTextArea("       Copyright ©2018-2019 JY.H.All rights reserved. ");
        Copyright.setFocusable(false);
        Copyright.setOpaque(false);
        Copyright.setFont(f1);
        Copyright.setEnabled(false);

        shadePanel.add(MyGithub);
        MyGithub.setBounds(10,180,400,20);
        shadePanel.add(J_Github);
        J_Github.setBounds(10,200,400,30);
        shadePanel.add(Copyright);
        Copyright.setBounds(10,240,400,25);

        setVisible(true);
    }

}


class InternetMonitor extends MouseAdapter
{
    public void mouseClicked(MouseEvent e)
    {
        JLabel JLabel_temp = (JLabel)e.getSource();
        String J_temp = (JLabel_temp.getText()).trim();
        System.out.println(J_temp);
        URI uri;

        try{
            uri = new URI(J_temp);
            Desktop desk = Desktop.getDesktop();
            if(Desktop.isDesktopSupported()&&desk.isSupported(Desktop.Action.BROWSE))
            {
                try{
                    desk.browse(uri);
                }catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }catch (URISyntaxException e1)
        {
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        JLabel JLabel_temp = (JLabel) e.getSource();
        JLabel_temp.setForeground(Color.BLUE);
    }

    public void mouseExited(MouseEvent e )
    {
        JLabel JLabel_temp = (JLabel)e.getSource();
        JLabel_temp.setForeground(Color.DARK_GRAY);
    }
}