import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//设定选项以及单词信息
class Dancizhan implements ActionListener {
    // 属性
    JFrame frame = new JFrame("百词斩");
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JLabel label1;  //设置标签一，显示答题数
    JLabel label2;  //设置标签二，用来显示单词
    JLabel label3 = new JLabel();//设置标签三，用来显示对错信息
    JButton jButton1;//上一题
    JButton jButton2;//下一题
    JRadioButton[] jrbs; //选项
    ButtonGroup group;//选项组
    Font f1 = new Font("宋体", Font.BOLD, 30);
    int I;//随机的单词下标数
    int m = 0;//记录答对次数
    int count = 0;//记录答题次数
    String[] str;//复制随机数组,并作为当前数组
    //    int[] flag;//标记数组用来存放str的顺序
    ArrayList<String[]> arrayList;  //存放单词信息

    //endregion
    //窗体显示-构造方法
    public Dancizhan() throws FileNotFoundException {
        //随机的一个数
        I = (int) (Math.random() * 4);
        //获取str
        getRandomStringArrays(I);
        //装载
        loading();
        //显示界面
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭即停止运行
        frame.setSize(400, 400);
        Point point = new Point(500, 500); // 设置坐标
        frame.setLocation(point); //设置窗体坐标
        frame.pack();
        frame.setVisible(true);
    }

    // 创建窗体得基本组件
    public void loading() {
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();//用来显示正确答案
        //设置四个面板的布局
        panel1.setLayout(new GridLayout(1, 1));
        panel2.setLayout(new GridLayout(6, 1));
        panel3.setLayout(new GridLayout(1, 2));

        //面板加入窗体中
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.SOUTH);

        // 将标签一放入第一个面板中,同时设置字体
        label1 = new JLabel("答对单词数： 0/4");
        label1.setForeground(Color.RED);//设置字体为红色
        label1.setFont(f1);//设置字体样式
        panel1.add(label1, BorderLayout.EAST);

        //设置第二个面板
        label2 = new JLabel(str[0]);
        label2.setFont(f1);
        panel2.add(label2, BorderLayout.NORTH);

        group = new ButtonGroup();
        jrbs = new JRadioButton[4];//存放选项的数组
        for (int i = 0; i < jrbs.length; i++) {
            jrbs[i] = new JRadioButton(str[i + 1]);
            jrbs[i].setFont(f1);//给每个选项添加字体
            group.add(jrbs[i]);//将每个选项存放在 ButtonGroup中
            panel2.add(jrbs[i]);//将选项加入面板中
            jrbs[i].addActionListener(this);
        }

        label3.setForeground(Color.GREEN);//设置字体为绿色
        label3.setFont(f1);
        panel2.add(label3);

        //设置面板三
        jButton1 = new JButton("上一题");
        jButton1.setEnabled(false);
        jButton2 = new JButton("下一题");
        //将按钮加入面板中
        panel3.add(jButton1);
        panel3.add(jButton2);
        //将自身注册为监听程序
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);
    }

    //获取单词信息
    public ArrayList getWordInfos() throws FileNotFoundException {
        String[] strings = new String[4];
        FileReader fr;
        BufferedReader f;
        String[] word1;
        String[] word2;
        String[] word3;
        String[] word4;
        ArrayList<String[]> arrayList = new ArrayList<>();//初始化
        f = new BufferedReader(new FileReader("E:\\test\\shixun\\src\\danci"));
        try {
            for (int i = 0; i < strings.length; i++) {
                strings[i] = f.readLine();//每次读取一行信息
            }
            //四个单词的信息分别放在三个数组中
            word1 = strings[0].split(" ");
            word2 = strings[1].split(" ");
            word3 = strings[2].split(" ");
            word4 = strings[3].split(" ");
            //将四个单词数组存放在集合中
            arrayList.add(word1);
            arrayList.add(word2);
            arrayList.add(word3);
            arrayList.add(word4);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    //获取随机单词
    public void getRandomStringArrays(int i) throws FileNotFoundException {
        arrayList = getWordInfos();
        str = arrayList.get(i);
    }

    //第n次正确后得答对题数
    public String getLanel() {
        return "答对单词数：" + m + "/4";
    }

    //装载选项
    public void loadingSelect() {
        for (int i = 0; i < jrbs.length; i++) {
            jrbs[i].setText(str[i + 1]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(str[5])) {
            label3.setText("√");
            label3.setForeground(Color.green);
            m++;//答对单词一次，就会加一，下一次就会显示
            label1.setText(getLanel());//重新设置答对单词数
        } else if (e.getActionCommand().equals("上一题")) {
            if (I == 0) {
                I = arrayList.size() - 1;//当当前的小标为0时，上一题的下标为3
            } else {
                I--;
            }
            str = arrayList.get(I);
            label3.setText(str[5]);//答案显示
            label2.setText(str[0]);//重置单词
            loadingSelect();//重置选项
        } else if (e.getActionCommand().equals("下一题")) {
            if (count < 4) {
                count++;
//                flag[count++] = I;
                if (I == arrayList.size() - 1) {
                    I = 0;
                } else {
                    I++;
                }
                jButton1.setEnabled(true);
                str = arrayList.get(I);
//                label1.setText(getLanel());//重新设置答对单词数
                label2.setText(str[0]);
                loadingSelect();
                label3.setText("");
            }
            if (count == 4) {
                new jumpWindow();
            }
            group.clearSelection();//清除上一次的选择
        } else {//答错了
            label3.setText("x" + str[5]);
            label3.setForeground(Color.red);
        }
    }

    class jumpWindow extends JFrame implements ActionListener {
        public jumpWindow() {
            JLabel jl = new JLabel("任务完成，点击下方按钮退出程序!");
            jl.setFont(f1);
            JButton jb = new JButton("完成任务");
            jb.setFont(f1);
            jb.addActionListener(this);
            setLayout(new GridLayout(2, 1));
            add(jl);
            add(jb);
            pack();
            setLocation(550, 550);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);//点击按钮时结束
        }
    }
}
public class guitest {
    public static void main(String[] args) throws FileNotFoundException {
        Dancizhan dancizhan = new Dancizhan();
    }
}