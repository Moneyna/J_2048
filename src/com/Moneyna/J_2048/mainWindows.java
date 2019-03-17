package src.com.Moneyna.J_2048;

//import com.mysql.cj.MysqlConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class mainWindows extends JFrame {
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";

    private JPanel jp1;
    private static Box_Panel jp2;
    private static JTextField jtf1;  //时间
    private JLabel jl1;
    private static JTextField jtf2;  //得分
    private JLabel jl2;
    private JLabel jl3;  //2048

    private JMenuBar bar;

    private JMenu fileMenu;
    private JMenu setMenu;
    private JMenu gameMenu;

    static private Box[][] box;
    static private Box[][] box1;  //对战方
    static private int row, col;

    static private Color[] color;

    private play Play;

    static private mainWindows mainWindows;
    static private mainWindows others;  //对战方显示
    static private MysqlConnection2 mysqlConnection2;
    //static


    public mainWindows(int row, int col,int mode) {
        super("J_2048");

        setLocation(400, 60);
        setSize(520, 670);

        mysqlConnection2=new MysqlConnection2();

        mainWindows = this;
        setLayout(new BorderLayout());

        color = new Color[12];
        this.row = row;
        this.col = col;

        initBox();

        color[0] = new Color(0xcdc1b4); //0
        color[1] = new Color(0xeee4da);  //2
        color[2] = new Color(0xede0c8);  //4
        color[3] = new Color(0xf2b179);  //8
        color[4] = new Color(0xf59563);  //16
        color[5] = new Color(0xf67c5f);  //32
        color[6] = new Color(0xf65e3b); //64
        color[7] = new Color(0xedcf72); //128
        color[8] = new Color(0xedcc61);  //256
        color[9] = new Color(0xedc850); //512
        color[10] = new Color(0xedc53f); //1024
        color[11] = new Color(0xedc22e);  //2048

        if(!(mode==3))
            setFocusable(true);
        addKeyListener(new Listener());

        MenuPart();

        NorthPart();

        add(jp1, BorderLayout.NORTH);

        setVisible(true);

        jp2=new Box_Panel(0);
        if(mysqlConnection2.isExist())
        {
            int result = JOptionPane.showConfirmDialog(null,"是否接着上次游戏？","Good player you are", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
                try {
                    mysqlConnection2.createAFrame();

                } catch (SQLException e) {
                }

                jp2=new Box_Panel(1);
            }
        }

        add(jp2, BorderLayout.CENTER);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "你确定要退出游戏吗?", "Good player you are", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_NO_OPTION) {
                    setVisible(false);
                    System.exit(0);
                } else {
                    setVisible(true);
                }
            }
        });

        Play=new play(mode,1);
    }

    private void initBox() {
        box = new Box[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                box[i][j] = new Box(0, false, i, j);
            }
        }
    }

    private void MenuPart() {
        bar = new JMenuBar();
        bar.setBackground(Color.white);
        setJMenuBar(bar);
        fileMenu = new JMenu("File");   //帮助  ； 游戏截图
        setMenu = new JMenu("Setting");  // 音效开 ； 音效关
        gameMenu = new JMenu("Game");

        bar.add(fileMenu);
        bar.add(setMenu);
        bar.add(gameMenu);

        JMenuItem aboutItem = new JMenuItem("About");
        fileMenu.add(aboutItem);
        fileMenu.addSeparator();
        JMenuItem helpItem = new JMenuItem("Help");
        fileMenu.add(helpItem);
        fileMenu.addSeparator();
        JMenuItem hence_con_Item = new JMenuItem("Hence");
        fileMenu.add(hence_con_Item);
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                play.getTime().setIsRun(false);
                new About();
                play.getTime().setIsRun(true);
            }
        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                play.getTime().setIsRun(false);
                new Help();
                play.getTime().setIsRun(true);
            }
        });

        hence_con_Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                play.getTime().setIsRun(false);

                String ss = hence_con_Item.getText();
                if (ss.equals("Hence")) {
                    JOptionPane.showMessageDialog(null, "游戏暂停！\n继续开始游戏请点击file>>Continue");
                    play.setCondition(false);
                    hence_con_Item.setText("Continue");
                } else if (ss.equals("Continue")) {
                    JOptionPane.showMessageDialog(null, "游戏继续！\n祝您游戏愉快！");
                    play.setCondition(true);
                    hence_con_Item.setText("Hence");
                    play.getTime().setIsRun(true);
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showConfirmDialog(null, "你确定要退出游戏吗?", "Good player you are", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_NO_OPTION) {
                    setVisible(false);
                    BeginGame.visiblePanel();

                } else {
                    setVisible(true);
                }
            }
        });

        JMenuItem soundOpenItem = new JMenuItem("Sound Open");
        setMenu.add(soundOpenItem);
        JMenuItem soundCloseItem = new JMenuItem("Sound Close");
        setMenu.add(soundCloseItem);

        soundOpenItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                play.setSound(true);
            }
        });

        soundCloseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                play.setSound(false);
            }
        });

        JMenuItem newGameItem = new JMenuItem("New Game");
        gameMenu.add(newGameItem);
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showConfirmDialog(null, "你确定要开始新的游戏吗?", "Good player you are", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_NO_OPTION) {
                    play.newGame(play.getRow(), play.getCol());
                }

            }
        });

        JMenuItem saveGameItem = new JMenuItem("Save Game");
        gameMenu.add(saveGameItem);
        saveGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mysqlConnection2.insert_recorder();
            }
        });

        JMenuItem readRecorderItem = new JMenuItem("Play from recoder");
        gameMenu.add(readRecorderItem);
        readRecorderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    mysqlConnection2.createAFrame();
                }catch (SQLException se)
                {

                }
            }
        });

    }


        private void NorthPart ()
        {
            jp1 = new JPanel();
            jp1.setLayout(new BorderLayout());

            JPanel jp11 = new JPanel();
            jp11.setLayout(new GridLayout(1, 1, 200, 200));
            //jl3.setPreferredSize(new Dimension());
            //jl3.setBackground(new Color(255,255,0));
            jl3 = new JLabel("2048");
            jl3.setOpaque(true);
            jl3.setBackground(Color.YELLOW);
            jl3.setHorizontalTextPosition(JLabel.CENTER);
            jl3.setFont(new Font(FONT_NAME, Font.BOLD, 64));
            jl3.setForeground(new Color(105, 105, 105));
            jp11.add(jl3);
            jp1.add(jp11, BorderLayout.WEST);

            JPanel jp12 = new JPanel();
            jp12.setLayout(new GridLayout(2, 2));

            jl1 = new JLabel("时间：\n"/*+"00:00:00"*/);
            jl1.setHorizontalAlignment(JLabel.RIGHT);
            jl1.setOpaque(true);
            jl1.setBackground(new Color(238, 238, 209));

            jl2 = new JLabel("得分：\n"/*+"0"*/);
            jl2.setHorizontalAlignment(JLabel.RIGHT);
            jl2.setOpaque(true);
            jl2.setBackground(new Color(238, 238, 209));

            jtf1 = new JTextField("00:00:00");
            jtf1.setHorizontalAlignment(JLabel.RIGHT);
            jtf1.setEnabled(false);
            jtf1.setBackground(new Color(54, 54, 54));

            jtf2 = new JTextField("0");
            jtf2.setHorizontalAlignment(JLabel.RIGHT);
            jtf2.setEnabled(false);
            jtf2.setBackground(new Color(79, 79, 79));

            jp12.add(jl1);
            jp12.add(jtf1);
            jp12.add(jl2);
            jp12.add(jtf2);

            jp1.add(jp12, BorderLayout.CENTER);

        }

        public static Box[][] getBox ()
        {
            return box;
        }

        public static JPanel getJp2 ()
        {
            return jp2;
        }

        public static JTextField getjtf1 ()   //时间
        {
            return jtf1;
        }

        public static JTextField getjtf2 ()   //得分
        {
            return jtf2;
        }

        public static void repaintCenterPart ( int row, int col)
        {
            mainWindows.setVisible(false);
            mainWindows m = new mainWindows(row, col,play.getmode());
        }

        public static void drawOneBox (Box b,boolean f)
        {
            Box_Panel.drawCenterPart(jp2.getGraphics(), b, f);
        }

        public static Color[] getColor ()
        {
            return color;
        }

//        public void readOnly()
//        {
//            setFocusable(false);
//        }

        public play getPlay()
        {
            return Play;
        }
}

class Box_Panel extends JPanel
{
    private static final Color BG_COLOR = new Color(0xbbada0);
    private static final String FONT_NAME = "Arial";
    private static final int BOX_SIZE = 100;
    private static final int BOX_MARGIN = 25;
    private static final int FONT_SIZE = 36;
    private static final int LAST_SIZE = 15;

    private static  int mode ;

    public Box_Panel(int i)
    {
        setPreferredSize(new Dimension(520,500));
        mode=i;
        setOpaque(true);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(BG_COLOR);
        g.fillRect(0,0,this.getSize().width,this.getSize().height);
        for (int i=0;i<play.getRow();i++) {
            for (int j = 0; j < play.getCol(); j++) {
                if(mode==0)
                    drawCenterPart(g,mainWindows.getBox()[i][j],false);
                else
                    drawCenterPart(g,mainWindows.getBox()[i][j],true);
            }
        }
    }

    public static void drawCenterPart(Graphics g2 , Box b,boolean BG_Color)
    {
        Graphics2D g=((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);

        int xOffset = offsetCoors(b.p.x,"x");
        int yOffset = offsetCoors(b.p.y,"y");

        if(BG_Color)
            g.setColor(mainWindows.getColor()[0]);
        else
            g.setColor(mainWindows.getColor()[b.expn]);
        g.fillRoundRect(xOffset,yOffset,BOX_SIZE,BOX_SIZE,14,14);

        if(b.expn>0&&!BG_Color) {

            g.setColor(chooseFontColor(b));

            final Font font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
            g.setFont(font);
            String s = String.valueOf(1 << b.expn);
            final FontMetrics fm = g.getFontMetrics(font);
            final int w = fm.stringWidth(s);
            final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

            g.drawString(s, xOffset + (BOX_SIZE - w) / 2, yOffset + BOX_SIZE - (BOX_SIZE - h) / 2 - 2);
        }
    }

    private static int offsetCoors(int arg,String s) {
        if(s.equals("x"))
            return arg*(BOX_MARGIN+BOX_SIZE)+BOX_MARGIN;
        else return arg*(BOX_MARGIN+BOX_SIZE)+BOX_MARGIN;
    }

    private static boolean ifGap(int x,int y)
    {
        int xx=x%(BOX_MARGIN+BOX_SIZE),yy=y%(BOX_MARGIN+BOX_SIZE);
        if(xx<BOX_MARGIN||yy<BOX_MARGIN)
        {
            return true;
        }
        return false;
    }

    public static void drawMovingLine(Graphics g2,String ss,Box f,Box s,boolean ifMerge,boolean ifBuff)//左 0 右 1 上 2 下 3
    {
        Graphics2D g=((Graphics2D) g2);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);

        Point p1=f.p,p2=s.p;

        int xOffset1 = offsetCoors(f.p.x,"x");
        int yOffset1 = offsetCoors(f.p.y,"y");

        int xOffset2 = offsetCoors(s.p.x,"x");
        int yOffset2 = offsetCoors(s.p.y,"y");

        int xlen,ylen;

        xlen = 5;
        ylen = 5;

        switch(ss)
        {
            case "L":
                while(xOffset1>xOffset2)
                {

                    if(ifGap(xOffset1,yOffset1)) {
                        g.setColor(mainWindows.getColor()[0]);
                        g.fillRoundRect(xOffset1,yOffset1,xlen,BOX_SIZE,14,14);

                        try {
                            Thread.sleep(6);
                        }catch (InterruptedException e)
                        {
                            e.getStackTrace();
                        }

                        g.setColor(BG_COLOR);
                        g.fillRoundRect(xOffset1, yOffset1, xlen, BOX_SIZE, 14, 14);

                    }
                    xOffset1-=xlen;
                }

                break;
            case "R":
                while(xOffset1<xOffset2)
                {


                    if(ifGap(xOffset1,yOffset1)) {
                        g.setColor(mainWindows.getColor()[0]);
                        g.fillRoundRect(xOffset1,yOffset1,xlen,BOX_SIZE,14,14);

                        try {
                            Thread.sleep(6);
                        }catch (InterruptedException e)
                        {
                            e.getStackTrace();
                        }

                        g.setColor(BG_COLOR);
                        g.fillRoundRect(xOffset1, yOffset1, xlen, BOX_SIZE, 14, 14);
                    }
                    xOffset1+=xlen;
                }

                break;
            case "U":
                while(yOffset1>yOffset2)
                {

                    if(ifGap(xOffset1,yOffset1)) {

                        g.setColor(mainWindows.getColor()[0]);
                        g.fillRoundRect(xOffset1,yOffset1,BOX_SIZE,ylen,14,14);

                        try {
                            Thread.sleep(6);
                        }catch (InterruptedException e)
                        {
                            e.getStackTrace();
                        }

                        g.setColor(BG_COLOR);
                        g.fillRoundRect(xOffset1, yOffset1,BOX_SIZE,ylen, 14, 14);
                    }
                    yOffset1-=ylen;
                }

                break;
            case "D":
                while(yOffset1<yOffset2)
                {

                    if(ifGap(xOffset1,yOffset1)) {
                        g.setColor(mainWindows.getColor()[0]);
                        g.fillRoundRect(xOffset1,yOffset1,BOX_SIZE,ylen,14,14);

                        try {
                            Thread.sleep(6);
                        }catch (InterruptedException e)
                        {
                            e.getStackTrace();
                        }


                        g.setColor(BG_COLOR);
                        g.fillRoundRect(xOffset1, yOffset1,BOX_SIZE,ylen, 14, 14);
                    }
                    yOffset1+=ylen;
                }
        }
        if(ifMerge) {
            if(ifBuff) {
                g.setColor(mainWindows.getColor()[s.expn]);

                for (int i = 1; i <= LAST_SIZE; i++)
                    g.fillRoundRect((xOffset2 - i), (yOffset2 - i), BOX_SIZE + i, BOX_SIZE + i, 14, 14);

                g.setColor(BG_COLOR);
                g.fillRoundRect((xOffset2 - LAST_SIZE), (yOffset2 - LAST_SIZE), BOX_SIZE + LAST_SIZE, BOX_SIZE + LAST_SIZE, 14, 14);
            }
            drawCenterPart(g,s,false);
        }
    }

    private static  Color chooseFontColor(Box b)
    {
        return b.expn<3?new Color(0x776e65) : new Color(0xf9f6f2);
    }

}

class Box
{
    int expn;
    boolean flag;
    Point p;   //y:行 x:列

    Box(){};

    Box(int e,boolean f,int x,int y)
    {
        expn=e;
        flag=f;
        p=new Point();
        p.x=y;
        p.y=x;
    }

    public void setInfo(int e,boolean f)
    {
        expn=e;
        flag=f;
        if(e==0)
        {
            mainWindows.drawOneBox(this,true);
        }
        else
        {
            mainWindows.drawOneBox(this,false);
        }
    }

    public void reNewBox()
    {
        for(int i=0;i<play.getRow();i++)
            for(int j=0;j<play.getCol();j++) {
                System.out.println("("+i+j+")成背景色");
                mainWindows.getBox()[i][j].setInfo(0, true);
            }
    }

    public static void Show_All_Box()
    {
        System.out.println("&&&&&&&状况&&&&&&&");
        for(int i=0;i<play.getRow();i++)
        {
            for(int j=0;j<play.getCol();j++)
            {
                System.out.print(mainWindows.getBox()[i][j].flag+"||"+(1<<mainWindows.getBox()[i][j].expn)+"  ");
            }
            System.out.println();
        }
    }
}

class MysqlConnection2 {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/J_2048";
    static final String USERNAME = "root";
    static final String PASSWORD = "0Qtmmxhhbqdnlst";
  //  private String[] DATA;

    public static selectFrame selectFrame;

    public void createAFrame() throws SQLException
    {
        selectFrame=new selectFrame(1);
    }

    public void insert_recorder() {
        Connection con = null;
        Statement sta = null;

        String CurrentTime = String.valueOf(System.currentTimeMillis());

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Creating statement...");

            sta = con.createStatement();

            String sql = "INSERT INTO recorder(mode,date,Score,Hour,Min,Sec) VALUES(" + play.getmode() + ",\'" + CurrentTime + "\',"+play.getScore()+","+play.getHour()+","+play.getMin()+","+play.getSec()+"); ";
            sta.executeUpdate(sql);

//            con.setAutoCommit(false);
            PreparedStatement sql1=con.prepareStatement("INSERT INTO game_data(data,index_i,index_j,expn) VALUES(?,?,?,?) ");

            for (int i = 0; i < play.getRow(); i++) {
                for (int j = 0; j < play.getCol(); j++) {
                    sql1.addBatch("INSERT INTO game_data(data,index_i,index_j,expn) VALUES(\'" + CurrentTime + "\'," + i + "," + j + "," + mainWindows.getBox()[i][j].expn + "); ");
                    System.out.println("执行了("+ i + "," + j+")");
                }
            }
            sql1.executeBatch();
//            con.commit();


            sta.close();
            con.close();

        } catch (SQLException | ClassNotFoundException se) {//异常处理
            se.printStackTrace();
        } finally {
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
            }
        }
        JOptionPane.showMessageDialog(null, "保存成功！");

    }

    public  boolean isExist() {
        Connection con = null;
        Statement sta = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Creating statement...");

            sta = con.createStatement();
            String sql = "select count(mode) from recorder;";
            ResultSet rs = sta.executeQuery(sql);//使用statement对象执行简单的查询语句

            while (!rs.next()) {
                int m = rs.getInt("count(mode)");
                System.out.println("mode="+m);
                if (m >=0) {
                    return true;
                }
            }

            System.out.println("无保存数据！");
            return false;

        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
            }
        }
        return false;
    }

    public void read_recorder(String ss) {
        Connection con = null;
        Statement sta = null;
        String sql;
        ResultSet rs;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Creating statement...");
            sta = con.createStatement();

            sql = "Select Score,Hour,Min,Sec from recorder where date = \'" + ss + "\';";
            rs = sta.executeQuery(sql);
            while(rs.next())
            {
                String s=rs.getString("Score");
                play.setScore(s);
                String Hour=rs.getString("Hour");
                Integer hour = new Integer(Hour);

                String Min=rs.getString("Min");
                Integer min = new Integer(Min);

                String Sec=rs.getString("Sec");
                Integer sec = new Integer(Sec);
                play.setTimeValue(hour,min,sec);

                System.out.println("选取老记录的 Score="+s+" Hour="+Hour+" Min="+Min+" Sec="+Sec);

            }

             sql = "Select index_i,index_j,expn from game_data where data = \'" + ss + "\';";
             rs = sta.executeQuery(sql);

            mainWindows.getBox()[0][0].reNewBox();
             while (rs.next()) {
                int i = rs.getInt("index_i");
                int j = rs.getInt("index_j");
                int expn = rs.getInt("expn");

                if (expn > 0) {
                    mainWindows.getBox()[i][j].setInfo(expn, true);
                }
                System.out.println("index_i="+i+" index_j="+j+" expn="+expn);
            }

             mainWindows.getBox()[0][0].Show_All_Box();
            rs.close();
            sta.close();
            con.close();

        } catch (SQLException | ClassNotFoundException se) {//异常处理
            se.printStackTrace();
        } finally {
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
            }
        }

        JOptionPane.showMessageDialog(null,"读取记录成功！请您继续游戏！");
    }

    private String[] recorder() {
        Connection con = null;
        Statement sta = null;
        String[] data= new String[10];
        data[0]="NULL";
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Creating statement...");
            sta = con.createStatement();

            String sql = " SELECT * FROM recorder where mode= "+play.getmode()+";";
            ResultSet rs = sta.executeQuery(sql);

            rs.last();
            int count= rs.getRow();
            System.out.println("共有"+count+"条 mode= "+play.getmode()+"数据");
            List list= new List();
            rs.beforeFirst();

            String sql1 = " SELECT * FROM recorder where mode= "+play.getmode()+";";
            ResultSet rs1 = sta.executeQuery(sql1);
            rs1.beforeFirst();

            data = new String[count];
            int i=0;

            if(rs1.next()) {
                data[i]=rs1.getString("date");
                System.out.println("第"+i+"记录："+data[i]);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"没有游戏记录！请点击”Save Game“保存游戏！");
            }
            System.out.println("读取结束！");
        } catch (SQLException | ClassNotFoundException se) {//异常处理
            se.printStackTrace();
        } finally {
            try {
                if (sta != null) {
                    sta.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
            }
        }
        return data;
    }

        class selectFrame extends JFrame {

            public selectFrame(){}

            public selectFrame(int i) throws SQLException
            {
                super("选择记录");

                setSize(400, 300);
                setLocation(450, 200);

                JScrollPane jp=new JScrollPane();
                //jp.setLayout(new FlowLayout());
                JLabel jLabel=new JLabel("选择历史纪录");
                jp.add(jLabel,BorderLayout.NORTH);

                add(jp);
                panelPart(jp);


                setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }


            private void  panelPart(JScrollPane jp) throws SQLException
            {
               String[] data=recorder();

               if(data[0].equals("NULL"))
               {
                   JOptionPane.showMessageDialog(null,"没有游戏记录！请点击”Save Game“保存游戏！");
                    return ;
               }

               jp.setLayout(new GridLayout(data.length,1));

                int count=data.length;
                JButton[] btn=new JButton[count];
                for(int i=0;i<count;i++)
                {
                    btn[i]=new JButton();
                    btn[i].setText(data[i]);
                    jp.add(btn[i]);
                    btn[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            JButton btn=(JButton) actionEvent.getSource();
                            String str=btn.getText();
                            read_recorder(str);
                            setVisible(false);
                        }
                    });
                }

            }

    }


}