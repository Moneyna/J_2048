package src.com.Moneyna.J_2048;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

class Server {
    private List<ServerThread> clients = null;
    //private List<Client> clients;
    private static int num=0;
    private int largestRoom=6;
    //private int inRoomNum=0;
    private battleTwoPeople[] battlePair=new battleTwoPeople[6];



    public Server()
    {
        startUp();

    }

    private void startUp() {
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(5858);
            clients = new ArrayList<ServerThread>();
            while (true) {
                s = ss.accept();
                ServerThread st = new ServerThread(s);
                new Thread(st).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ss != null)
                    ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerThread implements Runnable {
        private Socket s = null;
        private boolean flag = true;
        private int Id;
        private int roomIndex;

        public ServerThread(Socket socket) throws IOException {
            Id=num;
            num++;
            this.s = socket;   //TODO:在监听反应的情况下设置roomIndex

            clients.add(this);
        }

        private void send(String msg) {
            for (ServerThread st : clients)
            {

            }
        }

        private void receive() throws IOException {



        }

        private void stop() {
            clients.remove(this);
            flag = false;
        }

        @Override
        public void run() {
            while (true) {
                if (!flag) break;
                for (int i = 0; i < largestRoom; i++) {
                    if (battlePair[i].ifFull()&battlePair[i].ifAllReady())       //如果双方准备，则投射游戏(get!)
                    {
                        if(battlePair[i].onlyOnes) {   //Onlyones还需斟酌，若取消准备呢
                            battlePair[i].onlyOnes = false;
                            for (int j = 0; i < 2; j++) {
                                battlePair[i].getClient(i).setReadyState(true);
                            }
                        }

                    }

                }
            }


        }

    }

    public static int getNum()
    {

        return num;
    }

    public void setBattlePair(int i,Client c)
    {
        int index=battlePair[i].whichEmpty();
        battlePair[i].setClient(c,index);
    }

    public battleTwoPeople[] getBattlePair()
    {
        return battlePair;
    }
}


class battleTwoPeople
{
    private Client[] player=new Client[2];

    public boolean onlyOnes=true;
    private boolean[] ifReady=new boolean[2];
    private boolean[] othersMove=new boolean[2],othersRandom=new boolean[2];
    private int[] fx=new int[2],fy=new int[2],fz=new int[2];
    private String[] moveDir=new String[2];

    public battleTwoPeople()
    {
//        player[0]=null;
//        player[1]=null;
//        //ifReady[0]=false;
//        //ifReady[1]=false;
    }



//    public battleTwoPeople(Client f)
//    {
//        player[0]=f;
//        player[1]=null;
//    }
//
//    public battleTwoPeople(Client f,Client s)
//    {
//        player[0]=f;
//        player[1]=s;
//    }

    public Client getClient(int i)
    {
        return player[i];
    }

    public void setClient(Client c,int i)
    {
        player[i]=c;
    }

    public boolean ifFull()
    {
        if((player[0]!=null)&&(player[1]!=null))
            return true;
        else
            return false;
    }

    public boolean ifAllReady()
    {
        if(ifReady[0]==true&ifReady[1]==true)
        {
            return true;
        }
        return false;
    }

    public void setReady(int i,boolean b)      //TODO: 关于准备状态的逻辑修改
    {
        ifReady[i]=b;
    }

    public int whichEmpty()
    {
        if(player[0]==null)
        {
            return 0;
        }else
        {
            if(player[1]==null)
            {
                return 1;
            }
        }
        return 0;
    }

    public int whichPlayerYouAre(Client c)
    {
        if(player[0].equals(c))
        {
            return 0;
        }
        if(player[1].equals(c))
        {
            return 1;
        }
        return -1;   //TODO: 若不是 记得异常判断
    }

    public void setRandomLocation(int i,int x,int y,int z)
    {
//        othersRandom[i]=true;  //记得完成random操作后，将标志改为false
////        fx[i]=x;
////        fy[i]=y;
////        fz[i]=z;

        if(i==0)
        {
            player[1].setHandRandom(x,y,z);
        }
        if(i==1)
        {
            player[0].setHandRandom(x,y,z);
        }
    }

    public void setMoveDir(int i,String s)
    {
//        othersMove[i]=true;  //记得完成random操作后，将标志改为false
//        moveDir[i]=s;
        if(i==0)
        {
            player[1].setHandMove(s);
        }
        if(i==1)
        {
            player[0].setHandMove(s);
        }
    }
}