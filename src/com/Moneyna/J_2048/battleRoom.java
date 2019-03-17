package src.com.Moneyna.J_2048;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;

public class battleRoom extends JFrame {

    private JPanel jp;
    private static Sign_in sign_in;
    private static sign_up sign_up;
    private static JTextArea[] room = new JTextArea[6];
    private int roomIndex=-1;
    private readyOrNot ready=null;
    private static Client client;
    //private Server server;

    /**
     * Launch the application.
     */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					battleRoom frame = new battleRoom();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

    /**
     * Create the frame.
     */
    public battleRoom() {
        //this.client=new Client();
        //setVisible(true);
        setTitle("\u5BF9\u6218\u623F\u95F4");  //对战房间
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //TODO:更改逻辑
        setBounds(100, 100, 450, 300);
        BeginGame.setBattleroom(this);

        jp = new JPanel();
        jp.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(jp);
        jp.setOpaque(true);
        jp.setBackground(new Color(0, 128, 128));
        jp.setLayout(null);

        JButton quickBegin = new JButton("\u5FEB\u901F\u5F00\u59CB");
        //quickBegin.setOpaque(true);
        quickBegin.setHorizontalAlignment(SwingConstants.CENTER);
        quickBegin.setForeground(new Color(0, 0, 0));
        quickBegin.setFont(new Font("开始游戏", Font.PLAIN, 12));
        quickBegin.setBackground(new Color(173, 255, 47));
        quickBegin.setBounds(100, 228, 93, 23);
        quickBegin.setBorderPainted(false); // �����߿�
        //quickBegin.setContentAreaFilled(false);
        quickBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(roomIndex==(-1))
                {
                    Random random=new Random();
                    roomIndex=random.nextInt(5);

                    room[roomIndex].setBackground(new Color(255, 250, 205));
                    String s = room[roomIndex].getText();
                    if (s.length() > 0 || !s.equals("")) {
                        room[roomIndex].setText("当前玩家人数\n" + "1/2");
                        //room[0].setH
                    } else {
                        room[roomIndex].setText("当前玩家人数\n" + "2/2");
                    }
                    quickBegin.setText("退出房间");
                    if(ready==null)
                    {
                       ready = new readyOrNot();
                       ready.setTitle("房间"+roomIndex);
                    }else
                    {
                        ready.setTitle("房间"+roomIndex);
                        ready.setVisible(true);
                    }

                }else
                {
                    quickBegin.setText("快速开始");
                    room[roomIndex].setText("");
                    room[roomIndex].setBackground(new Color(47, 79, 79));
                    roomIndex=-1;
                }
            }
        });
        jp.add(quickBegin);

        JButton back = new JButton("\u8FD4\u56DE");
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.setForeground(Color.BLACK);
        back.setFont(new Font("返回", Font.PLAIN, 12));
        back.setBackground(new Color(173, 255, 47));
        back.setBorderPainted(false);
        back.setBounds(237, 228, 93, 23);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                room[roomIndex].setText("");
                room[roomIndex].setBackground(new Color(47, 79, 79));
                roomIndex=-1;

                BeginGame.getBattleRoom().setVisible(false);
                BeginGame.getBeginGame().setVisible(true);
            }
        });
        jp.add(back);

        room[0] = new JTextArea();
        room[0].setBackground(new Color(47, 79, 79));
        room[0].setBounds(26, 24, 82, 70);
        room[0].setEditable(false);
        jp.add(room[0]);

        room[1] = new JTextArea();
        room[1].setBackground(new Color(47, 79, 79));
        room[1].setBounds(172, 24, 82, 70);
        room[1].setEditable(false);
        jp.add(room[1]);

        room[2] = new JTextArea();
        room[2].setBackground(new Color(47, 79, 79));
        room[2].setBounds(305, 24, 82, 70);
        room[2].setEditable(false);
        jp.add(room[2]);

        room[3] = new JTextArea();
        room[3].setBackground(new Color(47, 79, 79));
        room[3].setBounds(26, 125, 82, 70);
        room[3].setEditable(false);
        jp.add(room[3]);

        room[4] = new JTextArea();
        room[4].setBackground(new Color(47, 79, 79));
        room[4].setBounds(172, 125, 82, 70);
        room[4].setEditable(false);
        jp.add(room[4]);

        room[5] = new JTextArea();
        room[5].setBackground(new Color(47, 79, 79));
        room[5].setBounds(305, 125, 82, 70);
        room[5].setEditable(false);
        jp.add(room[5]);

        for(int i=0;i<6;i++)
        {
            room[i].addMouseListener(new roomListener(i,quickBegin));
        }

        setLocation(500, 252);

        sign_in = new Sign_in();

        setVisible(true);

    }

    class roomListener extends MouseAdapter
    {
        private int index;
        private JButton btn;
        public roomListener(int i,JButton btn)
        {
            index=i;
            this.btn=btn;
            //mouseClicked(new MouseEvent(e));
        }
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if(roomIndex==(-1)) {
                roomIndex=index;
                client.setRoomIndex(index);
                room[index].setBackground(new Color(255, 250, 205));
                String s = room[index].getText();
                if (s.length() > 0 || !s.equals("")) {
                    room[index].setText("当前玩家人数\n" + "1/2");
                    //room[0].setH
                } else {
                    room[index].setText("当前玩家人数\n" + "2/2");
                }
                BeginGame.getServer().setBattlePair(roomIndex,client);
                btn.setText("退出房间");
            }
        }

    }

    public static void setSign_in(Sign_in s) {
        sign_in = s;
    }

    public static Sign_in getSign_in() {
        return sign_in;
    }

    public static void setSign_up(src.com.Moneyna.J_2048.sign_up s) {
        sign_up = s;
    }

    public static sign_up getSign_up() {
        return sign_up;
    }

    public static void setClient(Client c) {
        client=c;
    }

    public static Client getClient() {
        return client;
    }


//    public void setServer(Server server)
//    {
//        this.server=server;
//    }

}

