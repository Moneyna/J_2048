package src.com.Moneyna.J_2048;

import javax.swing.*;
import java.sql.SQLException;
import java.util.*;
import javax.sound.sampled.*;
import java.io.*;

class play {
    private static int row;
    private static int col;
    private static int mode;  //mode 3对战方显示窗口 mode2 倒计时   mode 1  经典
    private static Box[][] box;

    private static int hour = 0;
    private static int min = 0;
    private static int sec = 0;

    private static Integer Score = 0;

    private static timeCount time;

    private static boolean isRun;
    private static boolean hasSound;

    //private static int fx,fy,fz;

    public play(int mode,int i) {
        time=new timeCount();
        this.mode=mode;

        if(mode!=3) {
            if (mode == 2) {
                hour = sec = 0;
                min = 3;
            }
            init(4, 4, true);
            Random();
            Random();
            Box.Show_All_Box();

            isRun = true;
            hasSound = true;
        }else {
                isRun = false;
            hasSound = false;
        }

    }

    public play(int i)
    {
        init(play.row,play.col,true);
    }

    public void init(int row,int col,boolean f)
    {
        if(f) {
            box = mainWindows.getBox();
        }
        else{
            mainWindows.repaintCenterPart(row,col);
            box=mainWindows.getBox();
        }
        this.row=row;
        this.col=col;
    }

    public static void Random() {

        if (iffull()) {
            return;
        }

        Random rand = new Random();
        int x = rand.nextInt(row);
        int y = rand.nextInt(col);
        int z = rand.nextInt(2) + 1; //产生2还是4

        while (box[x][y].flag&&(box[x][y].expn>0)) {
            x = rand.nextInt(row);
            y = rand.nextInt(col);

        }
        box[x][y].setInfo(z, true);
        System.out.println("Random:"+"box["+x+"]["+y+"]="+box[x][y].expn);

        mainWindows.drawOneBox(box[x][y],false);

        if(mode==3) {
            BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setRandomLocation(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()), x, y, z);
        }
    }

    public  void handRandom(int x,int y,int z) {

        if (iffull()) {
            return;
        }

        box[x][y].setInfo(z, true);
       // System.out.println("Random:"+"box["+x+"]["+y+"]="+box[x][y].expn);

        mainWindows.drawOneBox(box[x][y],false);
    }

    public static boolean iffull() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!box[i][j].flag)
                    return false;
            }
        }
        return true;
    }

    class timeCount extends Thread
    {
        private boolean isRun=true;
        int i=0;
        public timeCount()
        {
            this.start();
        }

        public void run() {
            while (true) {

//                System.out.println("进入:"+i++);
                if (isRun) {
                    // break;
                    if(mode==1) {
                        sec += 1;
                        if (sec >= 60) {
                            sec -= 60;
                            min += 1;
                        }
                        if (min >= 60) {
                            min -= 60;
                            hour += 1;
                        }
                    }else if(mode==2)
                    {
                        sec-=1;
                        if(sec<=0)
                        {
                            sec+=60;
                            min-=1;
                        }

                        if(hour==0&&min==0&&sec<=0)
                        {
                            play p=new play(1);

                            setIsRun(false);

                            int result= JOptionPane.showConfirmDialog(null,"游戏结束！\n是否再来一局？","You can do it again",JOptionPane.YES_NO_OPTION);
                            if(result==JOptionPane.YES_NO_OPTION)
                            {
                                p.newGame(row,col);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,"Thanks for your playing!");
                            }
                        }
                    }
                    showTime();
                }
                else
                {
                    System.out.println("Hello？");
                    try {
                        sleep(1000);
                    }catch (InterruptedException e)
                    {
                        e.getStackTrace();
                    }
                }
            }

        }

        public void showTime() {
            String ss = "";
            if (hour < 10) {
                ss = "0" + hour + ":";
            } else {
                ss = hour + ":";
            }

            if (min < 10) {
                ss = ss + "0" + min + ":";
            } else {
                ss = ss + min + ":";
            }

            if (sec < 10) {
                ss = ss + "0" + sec;
            } else {
                ss = ss + sec;
            }
            try {
                sleep(1000);
            }catch (InterruptedException e)
            {
                e.getStackTrace();
            }
            mainWindows.getjtf1().setText(ss);
        }

        public void setIsRun (boolean f)
        {
            isRun=f;
            if(f)
             System.out.println("Sam,childish");
            else
            {
                System.out.println("It's time.");
            }
        }
    }


    public static timeCount getTime()
    {
        return time;
    }

    public static void setTimeVarible(timeCount t)
    {
        play.time=t;
    }

    public static boolean movable(String ss) {
        switch (ss) {
            case "L":
                for (int i = 0; i < row; i++) {
                    for (int j = col - 1; j >= 0; j--)   //从右往左
                    {
                        // boolean ff=false;
                        if (!box[i][j].flag) {
                            return true;
                        }
                        if (box[i][j].flag) {
                            for (int k = j - 1; k >= 0; k--) {
                                if (box[i][j].expn == box[i][k].expn) {
                                   return true;
                                }
                                if(box[i][k].expn>0)
                                    break;
                            }
                        }
                    }
                }
                break;
            case "R":
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++)   //从左往右
                    {
                        // boolean ff=false;
                        if (!box[i][j].flag) {
                            return true;
                        }
                        if (box[i][j].flag) {
                            for (int k = j + 1; k < col; k++) {
                                if (box[i][j].expn == box[i][k].expn) {
                                    return true;
                                }
                                if(box[i][k].expn>0)
                                    break;
                            }
                        }
                    }
                }
                break;
            case "U":
                for (int i = 0; i < col; i++)  //从下往上
                {
                    for (int j = row - 1; j >= 0; j--) {
                        // boolean ff=false;
                        if (!box[j][i].flag) {
                            return true;
                        }
                        if (box[j][i].flag) {
                            for (int k = j - 1; k >= 0; k--) {
                                if (box[j][i].expn == box[k][i].expn) {
                                    return true;
                                }
                                if(box[k][i].expn>0)
                                    break;
                            }
                        }
                    }
                }
                break;
            case "D":
                for (int i = 0; i < col; i++)  //从上往下
                {
                    for (int j = 0; j < row; j++) {
                        // boolean ff=false;
                        if (!box[j][i].flag) {
                            return true;
                        }
                        if (box[j][i].flag) {
                            for (int k = j + 1; k < row; k++) {
                                if (box[j][i].expn == box[k][i].expn) {
                                    return true;
                                }
                                if(box[k][i].expn>0)
                                    break;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    public static void merge_box(String ss) {
        Box f=new Box(),s=new Box();

        boolean hasf=false,has_s=false,first_void=false;
        switch (ss) {
            case "L":
                System.out.println("[L]Merge_ing");
                for(int i=0;i<row;i++) {
                    for (int j = 0; j < col; j++) {
                        hasf =false;
                        has_s =false;
                        first_void=false;


                        if (!box[i][j].flag) {
                            first_void = true;
                            for (int k = (j + 1); k < col; k++) {
                                if (box[i][k].flag) {
                                    f = box[i][k];
                                    hasf = true;
                                    break;
                                }
                            }
                            if (!hasf)
                                break;
                        } else {
                            if (box[i][j].flag) {
                                f = box[i][j];
                                hasf = true;
                            }

                        }
                        for (int k =( f.p.x + 1); k < col; k++) {
                            if (box[i][k].expn == f.expn) {
                                s = box[i][k];
                                has_s = true;
                                break;
                            } else {
                                if (box[i][k].flag)
                                    break;
                            }
                        }
                        if (has_s) {
                            box[i][j].setInfo((++f.expn), true);
                            Score+=(1<<box[i][j].expn);

                            if(first_void) {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "L", s, f, false, false);
                                s.setInfo(0,false);
                                if(hasSound)
                                new AePlayWave("merge.wav").start();

                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "L", f, box[i][j], true, true);
                                f.setInfo(0,false);

                            }else
                            {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "L", s,f, true, true);
                                s.setInfo(0,false);
                            }

                        } else {
                            if (first_void) {
                                box[i][j].setInfo(f.expn, true);
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "L", f, box[i][j], true, false);
                                f.setInfo(0,false);

                            }

                        }
                    }
                }
                System.out.println("[L]Done_Merge");
                play.Win();
                play.Die();
                break;
            case "R":
                System.out.println("[R]Merge_ing");
                for(int i=0;i<row;i++) {
                    for (int j = (col-1); j >=0; j--) {
                        hasf =false;
                        has_s =false;
                        first_void=false;
                        if (!box[i][j].flag) {
                            first_void = true;
                            for (int k = (j - 1); k >=0; k--) {
                                if (box[i][k].flag) {
                                    f = box[i][k];
                                    hasf = true;
                                    break;
                                }
                            }
                            if (!hasf)
                                break;
                        } else {
                            if (box[i][j].flag) {
                                f = box[i][j];
                                hasf = true;
                            }

                        }
                        for (int k = (f.p.x - 1); k >=0; k--) {
                            if (box[i][k].expn == f.expn) {
                                s = box[i][k];
                                has_s = true;
                                break;
                            } else {
                                if (box[i][k].flag)
                                    break;
                            }
                        }
                        if (has_s) {
                            box[i][j].setInfo((++f.expn), true);
                            Score+=(1<<box[i][j].expn);
                            if(hasSound)
                                new AePlayWave("merge.wav").start();

                            if(first_void) {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "R", s, f, false, false);
                                s.setInfo(0,false);

                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "R", f, box[i][j], true, true);
                                f.setInfo(0,false);

                            } else {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "R", s,f,true, true);
                                s.setInfo(0,false);

                            }
                        } else {
                            if (first_void) {
                                box[i][j].setInfo(f.expn, true);
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "R", f, box[i][j], true, false);
                                f.setInfo(0,false);
                            }
                        }
                    }
                }
                System.out.println("[R]Done_Merge");
                play.Win();
                play.Die();

                break;

            case "U":
                System.out.println("[U]Merge_ing");
                for(int i=0;i<col;i++) {
                    for (int j = 0; j < row; j++) {
                        hasf =false;
                        has_s =false;
                        first_void=false;

                        if (!box[j][i].flag) {
                            first_void = true;
                            for (int k = (j + 1); k < row; k++) {
                                if (box[k][i].flag) {
                                    f = box[k][i];
                                    hasf = true;
                                    break;
                                }
                            }
                            if (!hasf)
                                break;
                        } else {
                            if (box[j][i].flag) {
                                f = box[j][i];
                                hasf = true;
                            }
                        }
                        for (int k = (f.p.y + 1); k < row; k++) {
                            if (box[k][i].expn == f.expn) {
                                s = box[k][i];
                                has_s = true;
                                break;
                            } else {
                                if (box[k][i].flag)
                                    break;
                            }
                        }
                        if (has_s) {
                            box[j][i].setInfo((++f.expn), true);
                            Score+=(1<<box[i][j].expn);
                            if(hasSound)
                            new AePlayWave("merge.wav").start();

                            if(first_void) {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "U", s, f, false, false);
                                s.setInfo(0,false);


                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "U", f, box[j][i], true, true);
                                f.setInfo(0,false);

                            }
                            else
                            {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "U", s,f, true, true);
                                s.setInfo(0,false);

                            }

                        } else {
                            if (first_void) {
                                box[j][i].setInfo(f.expn, true);
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "U", f, box[j][i], true, false);
                                f.setInfo(0,false);

                            }

                        }
                    }
                }
                System.out.println("[U]Done_Merge");
                play.Win();
                play.Die();
                break;
            case "D":
                System.out.println("[D]Merge_ing");
                for(int i=0;i<col;i++) {
                    for (int j = (row-1); j >=0; j--) {
                        hasf =false;
                        has_s =false;
                        first_void=false;
                        if (!box[j][i].flag) {
                            first_void = true;
                            for (int k = (j - 1); k >=0; k--) {
                                if (box[k][i].flag) {
                                    f = box[k][i];
                                    hasf = true;
                                    break;
                                }
                            }
                            if (!hasf)
                                break;
                        } else {
                            if (box[j][i].flag) {
                                f = box[j][i];
                                hasf = true;
                            }

                        }
                            for (int k = (f.p.y - 1); k >= 0; k--) {
                                if (box[k][i].expn == f.expn) {
                                    s = box[k][i];
                                    has_s = true;
                                    break;
                                } else {
                                    if (box[k][i].flag)
                                        break;
                                }
                            }

                        if (has_s) {
                            box[j][i].setInfo(++f.expn, true);
                            Score+=(1<<box[i][j].expn);
                            if(hasSound)
                            new AePlayWave("merge.wav").start();

                            if(first_void) {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "D", s, f, false, false);
                                s.setInfo(0,false);


                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "D", f, box[j][i], true, true);
                                f.setInfo(0,false);

                            }
                            else
                            {
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "D", s,f,  true, true);
                                s.setInfo(0,false);

                            }

                        } else {
                            if (first_void) {
                                box[j][i].setInfo(f.expn, true);
                                Box_Panel.drawMovingLine(mainWindows.getJp2().getGraphics(), "D", f, box[j][i], true, false);
                                f.setInfo(0,false);
                            }

                        }
                    }
                }
                System.out.println("[D]Done_Merge");
                play.Win();
                play.Die();
        }
        setScore();

    }

    private static void Win()
    {
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                if(box[i][j].expn==11)
                {
                    time.setIsRun(false);
                    int result=JOptionPane.showConfirmDialog(null,"游戏胜利！\n是否再来一局?","Congragulation!",JOptionPane.YES_NO_OPTION);

                    play p=new play(1);
                    if(result==JOptionPane.YES_NO_OPTION)
                    {
                        p.newGame(play.row,play.col);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Thanks for your playing!");
                    }
                }
            }
        }
    }

    public static void Die()
    {
        play p=new play(1);
        if(iffull()&&!movable("L")&&!movable("R")&&!movable("U")&&!movable("D"))
        {
            time.setIsRun(false);
            int result= JOptionPane.showConfirmDialog(null,"游戏结束！\n是否再来一局？","You can do it again",JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_NO_OPTION)
            {
                p.newGame(row,col);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Thanks for your playing!");
            }
        }

    }


    public static void newGame(int row,int col)
    {
        play p=new play(1);

        for(int i=0;i<row;i++)
        {
           for(int j=0;j<col;j++)
           {
               box[i][j].setInfo(0,false);
               mainWindows.drawOneBox(box[i][j],false);
           }
        }
            Score=0;
            setScore();

            if(mode==1) {
                hour = min = sec = 0;
            }
            else
            {
                hour=sec=0;
                min=3;
            }

        time=p.new timeCount();
        time.showTime();

        Random();
        Random();

    }
    public static void setScore(String s)
    {

        mainWindows.getjtf2().setText(s);
    }

    public static void setScore()
    {
        mainWindows.getjtf2().setText(Score.toString());
    }

    public static int getScore()
    {
        return Score;
    }

    public static int getHour()
    {
        return hour;
    }

    public static int getMin()
    {
        return min;
    }

    public static int getSec()
    {
        return sec;
    }

    public static int getRow()
    {
        return row;
    }

    public static int getCol()
    {
        return col;
    }

    public static void setCondition(boolean f)
    {
        isRun=f;
    }

    public static boolean getCondition()
    {
        return isRun;
    }

    public static void setSound(boolean f)
    {
        hasSound=f;
    }

    public static boolean getSound()
    {
        return hasSound;
    }

    public static int getmode()
    {
        return mode;
    }

    public static void setTimeValue(int h,int m,int s)
    {
        hour=h;
        min=m;
        sec=s;
    }

    public void handMoveBox(String s)
    {
        if (movable(s)){
            if(play.getSound())
                new AePlayWave("move.wav").start();
            merge_box(s);
            setScore();
        }
    }
}

class AePlayWave extends Thread {
    private String filename;
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb

    public AePlayWave(String wavfile) {
        filename = wavfile;
    }

    public void run() {
        File soundFile = new File(Thread.currentThread().getContextClassLoader().getResource("")+File.separator+"src"+File.separator+"wav"+File.separator+filename);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
            return;
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }
    }
}
