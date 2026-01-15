package codes;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

public class GameFrame extends JFrame implements KeyListener{
    //int[] tiles = new int[16];
    //用于进行数据测试
    //int[] tiles = new int[]{4,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536};
    int[] tiles = new int[]{0,0,0,0,0,0,0,0,0,0,0,1024,1024,0,0,0};
    int score = 0;
    boolean won = false; //do not win for a second time
    long startTime;
    long endTime;

    public GameFrame(){
        InitFrame();
        // InitImage();
        startGame();
        this.setVisible(true);
    }

    public void InitFrame(){
        this.setAlwaysOnTop(true);
        this.setSize(584, 624);
        this.setTitle("2048");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("images\\2048.png").getImage());
        // this.setLayout(null);
        // 4this.addKeyListener(this);
    }

    public void InitImage(){
        this.getContentPane().removeAll();
        this.setLayout(null);

        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score: " + score);
        scoreLabel.setFont(new Font(null, 0, 20));
        scoreLabel.setBounds(72, 50, 500, 30);
        this.getContentPane().add(scoreLabel);

        for (int i = 0; i < tiles.length; i++) {
            // 如果是空（0）不显示
            JLabel jl = new JLabel(tiles[i] == 0 ? "" : ("" + tiles[i]));
            jl.setBounds(72 + 105 * (i / 4), 86 + 105 * (i % 4), 105, 105);
            jl.setFont(new Font("Times New Roman", 0, 48));
            jl.setIcon(new ImageIcon("images\\" + tiles[i] + ".png"));
            
            jl.setBorder(new BevelBorder(1));
            this.getContentPane().add(jl);
        }
        this.getContentPane().repaint();//重新刷新

        if (fail()) {
            this.removeKeyListener(this);
            throwModal("You lose");
        }
        System.out.println(Arrays.toString(tiles));

        if (win()) {
            // this.removeKeyListener(this);
            throwModal("You win");
            long speedrun = endTime - startTime;
            long minutes = speedrun / 60000;
            long seconds = (speedrun / 1000) % 60;
            long miliseconds = speedrun % 1000;
            throwModal("Your time:\r\n" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds) + ":" + miliseconds);
        }
    }

    public void startGame(){
        this.addKeyListener(this);
        tiles = new int[16];
        Random r = new Random();
        int first = r.nextInt(0,16);
        int second = first;
        System.out.println(first);
        while (second == first){
            second = r.nextInt(0, 16);
        }
        System.out.println(second);
        tiles[first] = TwoOrFour();
        tiles[second] = TwoOrFour();
        // System.out.println("a");
        InitImage();

        startTime = System.currentTimeMillis();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        int[] pre = null; //节约资源
        if (key == 38){
            System.out.println("up");
            pre = tiles;
            //分析每一行
            int[] row1 = analyzeTile(new int[]{tiles[0], tiles[1], tiles[2], tiles[3]});
            int[] row2 = analyzeTile(new int[]{tiles[4], tiles[5], tiles[6], tiles[7]});
            int[] row3 = analyzeTile(new int[]{tiles[8], tiles[9], tiles[10], tiles[11]});
            int[] row4 = analyzeTile(new int[]{tiles[12], tiles[13], tiles[14], tiles[15]});
            //重置数据
            tiles = new int[]{row1[0], row1[1], row1[2], row1[3], row2[0], row2[1], row2[2], row2[3], row3[0], row3[1], row3[2], row3[3], row4[0], row4[1], row4[2], row4[3]};
            if (!Arrays.equals(pre,tiles)) generateNewTile();
            InitImage();
        }else if (key == 37){
            System.out.println("left");
            pre = tiles;
            int[] column1 = analyzeTile(new int[]{tiles[0], tiles[4], tiles[8], tiles[12]});
            int[] column2 = analyzeTile(new int[]{tiles[1], tiles[5], tiles[9], tiles[13]});
            int[] column3 = analyzeTile(new int[]{tiles[2], tiles[6], tiles[10], tiles[14]});
            int[] column4 = analyzeTile(new int[]{tiles[3], tiles[7], tiles[11], tiles[15]});
            tiles = new int[]{column1[0], column2[0], column3[0], column4[0], column1[1], column2[1], column3[1], column4[1], column1[2], column2[2], column3[2], column4[2], column1[3], column2[3], column3[3], column4[3]};
            if (!Arrays.equals(pre,tiles)) generateNewTile();
            InitImage();
        }else if (key == 40){
            System.out.println("down");
            pre = tiles;
            int[] row1 = analyzeTile(new int[]{tiles[3], tiles[2], tiles[1], tiles[0]});
            int[] row2 = analyzeTile(new int[]{tiles[7], tiles[6], tiles[5], tiles[4]});
            int[] row3 = analyzeTile(new int[]{tiles[11], tiles[10], tiles[9], tiles[8]});
            int[] row4 = analyzeTile(new int[]{tiles[15], tiles[14], tiles[13], tiles[12]});
            tiles = new int[]{row1[3], row1[2], row1[1], row1[0], row2[3], row2[2], row2[1], row2[0], row3[3], row3[2], row3[1], row3[0], row4[3], row4[2], row4[1], row4[0]};
            if (!Arrays.equals(pre,tiles)) generateNewTile();
            InitImage();
        }else if (key == 39){
            System.out.println("right");
            pre = tiles;
            int[] column1 = analyzeTile(new int[]{tiles[12], tiles[8], tiles[4], tiles[0]});
            int[] column2 = analyzeTile(new int[]{tiles[13], tiles[9], tiles[5], tiles[1]});
            int[] column3 = analyzeTile(new int[]{tiles[14], tiles[10], tiles[6], tiles[2]});
            int[] column4 = analyzeTile(new int[]{tiles[15], tiles[11], tiles[7], tiles[3]});
            tiles = new int[]{column1[3], column2[3], column3[3], column4[3], column1[2], column2[2], column3[2], column4[2], column1[1], column2[1], column3[1], column4[1], column1[0], column2[0], column3[0], column4[0]};
            if (!Arrays.equals(pre,tiles)) generateNewTile();
            InitImage();
        }
    }

    public int[] analyzeTile(int[] arr){
        // arr has length 4
        // 所有数组传入时都是设定往0方向移动
        if (arr[0] == arr[1] && arr[2] == arr[3] && arr[0] != 0 && arr[2] != 0){
            score += 2 * arr[0] + 2 * arr[2];
            return new int[]{arr[0] * 2, arr[2] * 2, 0, 0};
        } else if (arr[0] == arr[1] && arr[0] != 0){
            score += 2 * arr[0];
            if (arr[2] == 0) return new int[]{arr[0] * 2, arr[3], 0, 0};
            else return new int[]{arr[0] * 2, arr[2], arr[3], 0};
        } else if (arr[0] == arr[2] && arr[1] == 0 && arr[0] != 0){
            score += 2 * arr[0];
            return new int[]{arr[0] * 2, arr[3], 0, 0};
        } else if (arr[0] == arr[3] && arr[1] == 0 && arr[2] == 0 && arr[0] != 0){
            score += 2 * arr[0];
            return new int[]{arr[0] * 2, 0, 0, 0};
        } else if (arr[1] == arr[2] && arr[1] != 0){
            score += 2 * arr[1];
            if (arr[0] == 0) return new int[]{arr[1] * 2, arr[3], 0, 0};
            else return new int[]{arr[0], arr[1] * 2, arr[3], 0};
        } else if (arr[1] == arr[3] && arr[2] == 0 && arr[1] != 0){
            score += 2 * arr[1];
            if (arr[0] == 0) return new int[]{arr[1] * 2, 0, 0, 0};
            else return new int[]{arr[0], arr[1] * 2, 0, 0};
        } else if (arr[2] == arr[3] && arr[2] != 0){
            score += 2 * arr[2];
            if (arr[0] == 0 && arr[1] == 0) return new int[]{arr[2] * 2, 0, 0, 0};
            else if (arr[0] != 0 && arr[1] == 0) return new int[]{arr[0], arr[2] * 2, 0, 0};
            else if (arr[0] == 0 && arr[1] != 0) return new int[]{arr[1], arr[2] * 2, 0, 0};
            else if (arr[0] != 0 && arr[1] != 0) return new int[]{arr[0], arr[1], arr[2] * 2, 0};
        } else {
            //无法merge
            int[] result = new int[4];
            int index = 0;
            for (int i : arr) {
                if (i != 0){
                    result[index] = i;
                    index++;
                }
            }
            return result;
        }
        return null;
    }

    public ArrayList<Integer> getAllEmpty(){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) list.add(i);
        }
        return list;
    }

    public void generateNewTile(){
        ArrayList<Integer> list = getAllEmpty();
        if (list.size() == 0) return; //防止出异常直接结束方法
        Collections.shuffle(list);
        tiles[list.get(0)] = TwoOrFour();
    }

    public int TwoOrFour(){
        // 90% 2 10% 4
        Random r = new Random();
        if (r.nextInt(0, 10) == 9) return 4;
        else return 2;
    }

    public boolean fail(){
        if (getAllEmpty().size() != 0) return false;
        else {
            //任意相邻的都不能合起来
            if (tiles[0] != tiles[1] &&
                tiles[1] != tiles[2] &&
                tiles[2] != tiles[3] &&
                tiles[4] != tiles[5] &&
                tiles[5] != tiles[6] &&
                tiles[6] != tiles[7] &&
                tiles[8] != tiles[9] &&
                tiles[9] != tiles[10] &&
                tiles[10] != tiles[11] &&
                tiles[12] != tiles[13] &&
                tiles[13] != tiles[14] &&
                tiles[14] != tiles[15] &&
                tiles[0] != tiles[4] &&
                tiles[1] != tiles[5] &&
                tiles[2] != tiles[6] &&
                tiles[3] != tiles[7] &&
                tiles[4] != tiles[8] &&
                tiles[5] != tiles[9] &&
                tiles[6] != tiles[10] &&
                tiles[7] != tiles[11] &&
                tiles[8] != tiles[12] &&
                tiles[9] != tiles[13] &&
                tiles[10] != tiles[14] &&
                tiles[11] != tiles[15] 
            )   return true;
        }
        return false;
    }

    public boolean win(){
        if (won) return false;
        for (int num : tiles) {
            if (num == 2048) {
                won = true;
                endTime = System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }

    public static void throwModal(String text){
        // 弹窗实现报错效果
        JDialog jd = new JDialog();
        jd.setSize(400,300);
        jd.setAlwaysOnTop(true);
        jd.setLocationRelativeTo(null);
        jd.setModal(true);
        jd.setLayout(null);

        JLabel jl = new JLabel(text);
        jl.setFont(new Font("Times New Roman", Font.BOLD, 30));
        jl.setBounds(120, 95, 500, 60);
        jd.getContentPane().add(jl);
        jd.setVisible(true);

    }
}