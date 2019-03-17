package src.com.Moneyna.J_2048;

import java.awt.event.*;

class Listener extends KeyAdapter
{
    play p=new play(1);
    public void keyPressed(KeyEvent e)
    {
        boolean ifright=false;  //是否按下指定按键
        int key=e.getKeyCode();
        //System.out.println(key);             //(get!)mode3状态  :hand move 与 hand随机位置
        if(play.getCondition()) {   //TODO: 上传服务器
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                System.out.println("Pressed L");
                ifright = true;
                if (p.movable("L")) {
                    if(play.getSound())
                    new AePlayWave("move.wav").start();
                    p.merge_box("L");
                    p.setScore();

                    //TODO: handmove
                    if(play.getmode()==3) {
                        BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setMoveDir(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()), "L");
                    }
                }

            } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                System.out.println("Pressed R");
                ifright = true;
                if (p.movable("R")) {
                    if (play.getSound())
                        new AePlayWave("move.wav").start();
                    p.merge_box("R");
                    p.setScore();
                    if (play.getmode() == 3) {
                        BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setMoveDir(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()), "R");
                    }
                }
            } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                System.out.println("Pressed U");
                ifright = true;
                if (p.movable("U")) {
                    if (play.getSound())
                        new AePlayWave("move.wav").start();
                    p.merge_box("U");
                    p.setScore();
                    if (play.getmode() == 3) {
                        BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setMoveDir(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()), "U");
                    }
                }
            } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                System.out.println("Pressed D");
                ifright = true;
                if (p.movable("D")) {
                    if (play.getSound())
                        new AePlayWave("move.wav").start();
                    p.merge_box("D");
                    p.setScore();
                    if (play.getmode() == 3) {
                        BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].setMoveDir(BeginGame.getServer().getBattlePair()[battleRoom.getClient().getRoomIndex()].whichPlayerYouAre(battleRoom.getClient()), "D");
                    }
                }
            }
            if (ifright) {
                try {
                    Thread.sleep(260);
                } catch (InterruptedException ex) {
                    ex.getStackTrace();
                }
                p.Random();

                p.Die();
                Box.Show_All_Box();
            }
        }
    }

}
