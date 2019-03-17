package src.com.Moneyna.J_2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;

class BeginGame extends JFrame {
    private static Server server=null;
    private static BeginGame beginGame;

    private final Color BG_Color = new Color(0xede0c8);
    private final Color Lower_Color = new Color(0xeee4da);
    private final Color Upper_Color = new Color(0xbbada0);

    private static final String FONT_NAME = "楷体";
    private static final int FONT_SIZE_1 = 100;
    private static final int FONT_SIZE_2 = 10;
    private static final Color FONT_COLOR = new Color(0xF5F1F6);

    private static battleRoom battleroom;

    public BeginGame() {
        super("欢迎来到2048～");
        beginGame = this;
        BeginPanel bp = new BeginPanel();
        add(bp);


        setSize(400, 300);
        setLocation(450, 200);


        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        bp.setOpaque(true);
    }

    private void invisblePanel() {
        setVisible(false);
    }

    public static void visiblePanel() {
        beginGame.setVisible(true);
    }
//    private void closeBeginGamePanel()
//    {
//        System.exit(1);
//    }


    class BeginPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g1) {
            Graphics2D g = (Graphics2D) g1;

            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();

            GradientPaint paint = new GradientPaint(0, 0, Upper_Color, 0, height, Lower_Color);
            g.setPaint(paint);
            g.fillRect(0, 0, width, height);

        }

        public BeginPanel() {

            setOpaque(true);

            setLayout(new BorderLayout());
            //add(new PartOne(),BorderLayout.NORTH);
            PartTwo partTwo = new PartTwo();
            add(partTwo, BorderLayout.SOUTH);
        }

        public void paint(Graphics g) {

            super.paint(g);
            final Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE_1);

            g.setFont(font);
            String s = String.valueOf(2048);

            final FontMetrics fm = g.getFontMetrics(font);
            final int w = fm.stringWidth(s);
            final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

            g.setColor(FONT_COLOR);
            g.drawString(s, (getWidth() - w) / 2, 180 - (180 - h) / 2 - 2);
        }

        class PartTwo extends JPanel {
            Font font = new Font(FONT_NAME, Font.PLAIN, 10);
            Color color = new Color(174, 255, 8);

            public PartTwo() {
                setPreferredSize(new Dimension(400, 80));

                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                setBackground(Lower_Color);

                ButtonPart();
            }

            private void ButtonPart() {
                JButton classical_btn = new JButton("经典模式");
                JButton countdown_btn = new JButton("倒计时模式");
                JButton battle_btn = new JButton("对战模式");

                Font font = new Font(FONT_NAME, Font.PLAIN, 10);

                add(javax.swing.Box.createHorizontalGlue());
                add(classical_btn);
                add(javax.swing.Box.createHorizontalStrut(30));
                add(countdown_btn);
                add(javax.swing.Box.createHorizontalStrut(30));
                add(battle_btn);
                add(javax.swing.Box.createHorizontalGlue());

                setButtonFont(classical_btn);
                classical_btn.setText("经典模式");
                classical_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        invisblePanel();

                        new mainWindows(4, 4,1);
                        //new play(1, 1);

//                        closeBeginGamePanel();
                    }
                });

                setButtonFont(countdown_btn);
                countdown_btn.setText("倒计时模式");
                countdown_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        invisblePanel();

                        new mainWindows(4, 4,2);
                        //new play(2, 1);

                    }
                });

                setButtonFont(battle_btn);
                battle_btn.setText("对战模式");
                battle_btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        invisblePanel();
                        if(server==null) {
                            server = new Server();
                            //TODO: Server与client设计
                            battleroom=new battleRoom();
                            //Server.setBattleRoom(battleroom);
                            // battleroom.setServer(server);
                        }else
                        {
                            System.out.println("Server 非空！");
                          //  Server.getBattleRoom().setVisible(true);
                            //TODO：Server与client设计
                        }


                    }
                });
            }

            private void setButtonFont(JButton btn) {
                btn.setFont(font);
                btn.setBackground(color);
            }
        }
    }

    public static void setBattleroom(battleRoom b)
    {
        battleroom=b;
    }

    public static Server getServer()
    {
        return server;
    }

    public static battleRoom getBattleRoom()
    {
        return battleroom;
    }

    public static BeginGame getBeginGame()
    {
        return beginGame;
    }
}



