package src.com.Moneyna.J_2048;

import javax.print.attribute.standard.Severity;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

class Client {
    private Socket s;
    private boolean flag = true;
    public boolean onlyOnes=true;
    //private Server server;

    private int Id;
    private int roomIndex=-1;
    private boolean ifBothReady=false;
    private mainWindows mine,others;
    //private play play;
//    private int fx,fy,fz;  //TODO:  others的随机数产生位置   ，  传给server 再由server给对手
//    private boolean othersMove=false,othersRandom=false;

    //public Client(){}
    public Client(/*Server s*/)
    {
        stratUp();
        Id=Server.getNum();
        //server=s;
    }

    private void stratUp() {

        try {
            s = new Socket("127.0.0.1", 5858);

            new Thread(new ClientThread()).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void setRelativeLocation()
    {
        Dimension screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();

        mine.setLocation((width/2-others.getWidth()),(height-others.getHeight())/2);
        others.setLocation(width/2,(height-others.getHeight())/2);

    }

    private class ClientThread implements Runnable {

        @Override
        public void run() {   //若收到对方亦开始信号，则对方与我方均可开始游戏
            while (true) {
                if (!flag)
                    break;
                if(ifBothReady&onlyOnes)   //让others窗口失去焦点(get！)，且修改双方相对方位(gey!)，改双方title（get！）
                {
                    onlyOnes=false;
                    others=new mainWindows(4,4,3);
                    //others.readOnly();         // TODO： 需考虑该窗口需不需要开线程一直放弃焦点
                    others.setTitle("J_2048 对手状态");
                    mine=new mainWindows(4, 4,1);
                    mine.setTitle("J_2048 当前游戏窗口");
                    setRelativeLocation();

                    //new play(1, 1);   //TODO： 移动加监听  主要是对手
                    ifBothReady=false;
                }


            }
        }

    }

//    public static void setBattleRoom(battleRoom b)
//    {
//        battleRoom=b;
//    }

//    public static battleRoom getBattleRoom()
//    {
//        return battleRoom;
//    }

    public int getId()
    {
        return Id;
    }

    public int getRoomIndex()
    {
        return roomIndex;
    }

    public void setRoomIndex(int i)
    {
        roomIndex=i;
    }

    public void setReadyState(boolean flag)
    {
        ifBothReady=flag;
    }

    public void setHandRandom(int x,int y,int z)
    {
//        othersRandom=true;
//        fx=x;
//        fy=y;
//        fz=z;
        others.getPlay().handRandom(x,y,z);
    }

    public void setHandMove(String s)
    {
//        othersMove=true;
//
        others.getPlay().handMoveBox(s);
    }
}
