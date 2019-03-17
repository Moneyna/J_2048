/*
 * Created by JFormDesigner on Mon Mar 11 20:51:45 CST 2019
 */

package src.com.Moneyna.J_2048;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author unknown
 */
public class readyOrNot extends JFrame {

    private JTextField jtf;
    //private int roomIndex;

    public void readyOrNot()
    {
        //roomIndex=i;

        JPanel jp = new JPanel();
        jp.setForeground(new Color(0, 0, 0));
        //jp.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(jp);

        jtf=new JTextField();
        jtf.setText("请点击准备。。。");
        jtf.setHorizontalAlignment(JTextField.CENTER);
        jp.add(jtf,BorderLayout.CENTER);

        JPanel buttonPart=new JPanel();
        buttonPart.setLayout(new BoxLayout(buttonPart,BoxLayout.X_AXIS));
        jp.add(buttonPart,BorderLayout.SOUTH);

        JButton ready = new JButton("准备");  //登录
        ready.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO:给服务器送消息
                String s=ready.getText();
                if(s.equals("准备")) {
                    BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setReady(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()),true);
                    jtf.setText("等待对方准备游戏。。。");
                    ready.setText("取消准备");
                }
                else{
                    if(s.equals("取消准备"))
                    {
                        //相反操作
                        BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setReady(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()),false);
                    }
                }
            }
        });
        ready.setBounds(190, 145, 93, 23);
        buttonPart.add(javax.swing.Box.createHorizontalGlue());
        buttonPart.add(ready);

        buttonPart.add(javax.swing.Box.createHorizontalStrut(10));

        JButton back = new JButton("退出房间");  //注册
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               setVisible(false);
               //TODO:server和battelRoom及CLIENT都要有反应
            }
        });
        back.setBounds(57, 145, 93, 23);
        buttonPart.add(back);
        buttonPart.add(javax.swing.Box.createHorizontalGlue());

        //setTitle("房间"+roomIndex);

    }

    public void setJtfText1(String s)
    {
        jtf.setText(s);
    }

//    public void setRoomIndex(int i)
//    {
//        roomIndex=i;
//    }
}
