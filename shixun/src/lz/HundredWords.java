package lz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 模拟百词斩，继承JFrame并实现ActionListener接口
 */
public class HundredWords extends JFrame implements ActionListener {
    private Word[] words;   //储存的单词
    private int index;      //当前单词的下标
    private int properNum;  //答对单词的数量
    private boolean[] proper;   //记录每个单词是否答对过
    private JLabel count;   //显示答对单词数的统计结果
    private JLabel wordLabel;   //显示当前单词
    //单词意思的选项组
    private ButtonGroup select;
    private JRadioButton r1;
    private JRadioButton r2;
    private JRadioButton r3;
    private JRadioButton r4;

    private JLabel result;  //显示选择是否正确的信息
    private JButton pre;    //显示上一题的按钮
    private JButton next;   //显示下一题的按钮

    /**
     * 构造方法初构建窗口
     */
    public HundredWords() {
        super("百词斩");
        init();
        setLocation(600, 400);  //设置窗口的位置
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置叉叉的点击事件
        setVisible(true);   //设置窗口可见
    }

    /**
     * 组装视图
     */
    public void init() {
        getWords();     //从文件中将单词加载到内存中的Word数组对象中
        randomWord();   //获取一个随机的单词索引
        //组装顶部：对count标签进行设置并将其放入top容器中
        JPanel top = new JPanel();
        count = new JLabel("答对单词数：0/" + words.length);  //设置count的文本内容
        top.setPreferredSize(new Dimension(300, 40));
        top.setAlignmentX(Component.LEFT_ALIGNMENT);    //设置top的水平对齐方式
        count.setFont(new Font("countFont", Font.HANGING_BASELINE, 15));    //设置字体
        count.setForeground(Color.red); //设置文本颜色
        top.add(count);

        //组装中部：设置wordLabel和单词选项的相关组件，再将它们放入mid容器里
        Box mid = Box.createVerticalBox();
        mid.setPreferredSize(new Dimension(300, 200));
        wordLabel = new JLabel(words[index].getSpell());    //设置wordLabel的文本内容
        mid.add(wordLabel);
        wordLabel.setFont(new Font("wordFont", Font.HANGING_BASELINE, 17)); //设置字体
        select = new ButtonGroup();
        r1 = addRadioButton(words[index].getOptions()[0], r1, mid);
        r2 = addRadioButton(words[index].getOptions()[1], r2, mid);
        r3 = addRadioButton(words[index].getOptions()[2], r3, mid);
        r4 = addRadioButton(words[index].getOptions()[3], r4, mid);
        result = new JLabel(" ");
        result.setFont(new Font("wordFont", Font.HANGING_BASELINE, 17));
        mid.add(result);
        //组装底部：将上一题和下一题按钮放入JPanel容器中，再将此容器放入窗口中；并监听这两个按钮
        JPanel down = new JPanel();
        down.setPreferredSize(new Dimension(300, 50));
        pre = new JButton("上一题");
        pre.setEnabled(false);  //开始时设置“上一题”按钮禁用
        pre.addActionListener(e -> {    //监听“上一题”按钮的点击事件
            buttonAction();
        });
        next = new JButton("下一题");
        next.addActionListener(e -> {   //监听“下一题”按钮的点击事件
            pre.setEnabled(true);
            buttonAction();
        });
        down.add(pre);
        down.add(next);
        //组装整体部分
        Box total = Box.createVerticalBox();  //创建Box容器
        total.add(top);
        total.add(mid);
        total.add(down);
        add(total);
    }
    /**
     * 对上下一题按钮监听后执行的操作
     */
    public void buttonAction() {
        Word temp = words[index];   //把已答过的单词放和最后面没答过的单词交换位置
        words[index] = words[words.length - properNum];
        words[words.length - properNum] = temp;
        randomWord();   //交换位置后从前面没答过的单词随机抽取一个单词继续答题
        renew();    //刷新单词答题页面
    }
    /**
     * 单词答题页面刷新的方法
     */
    public void renew() {
        select.clearSelection();    //清除已选
        result.setText(" ");    //清除选择是否正确的信息
        wordLabel.setText(words[index].getSpell()); //答题单词的拼写
        r1.setText(words[index].getOptions()[0]);   //选项1
        r2.setText(words[index].getOptions()[1]);   //选项2
        r3.setText(words[index].getOptions()[2]);   //选项3
        r4.setText(words[index].getOptions()[3]);   //选项4
    }
    /**
     * 监听4个选项的单选按钮被选定的事件
     */
    public void actionPerformed(ActionEvent e) {
        if (words[index].getMean().equals(e.getActionCommand())) {  //如果选择正确
            result.setText("正确");
            if (!words[index].isProper()) { //只有第一次被选对的单词才会记录到选对单词的数量中去
                words[index].setProper(true);
                properNum++;
                if (properNum == words.length) {    //如果单词全部答对了，就弹出消息，并退出程序
                    JOptionPane.showMessageDialog(this, "恭喜您，单词全部答对！");
                    System.exit(0);
                }
            }
            count.setText("答对单词数：" + properNum + "/" + words.length);
        }
        else
            result.setText("错误，答案：" + words[index].getSpell());
    }


    //获取随机单词索引的方法
    public void randomWord() {
        index = (int) (Math.random() * (words.length - properNum));
    }

    /**
     * 创建、设置并添加JRadioButton组件到容器中的方法
     */
    public JRadioButton addRadioButton(String text, JRadioButton r, Box box) {
        r = new JRadioButton(text);
        r.addActionListener(this);  //添加监听器
        r.setFont(new Font("wordFont", Font.HANGING_BASELINE, 17)); //设置字体
        select.add(r);
        box.add(r);
        return r;
    }

    /**
     * 将单词从文本中加载到内存中Word[]对象中的方法
     */
    public void getWords() {
        words = new Word[10];
        try {
            Scanner sc = new Scanner(new File("words.txt"));
            String str = "";
            while (sc.hasNext())
                str += sc.nextLine() + "\n";
            String[] rows = str.split("\n");
            String[][] items = new String[rows.length][6];
            for (int i = 0; i < rows.length; i++)
                items[i] = rows[i].split("    ");
            words = new Word[rows.length];
            for (int i = 0; i < words.length; i++)
                words[i] = new Word(items[i][0], items[i][1], new String[]{items[i][2], items[i][3], items[i][4], items[i][5]});
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new HundredWords();
    }
}
