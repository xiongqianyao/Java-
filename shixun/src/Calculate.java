import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 通过类的继承创建窗体,既做窗体也做监听器
 * 先用构造方法，构造出计算器的基本组件。
 * 再打造出计算算法，通过符号来对应哪种计算算法
 */
public  class Calculate extends JFrame implements ActionListener {

    JPanel jp1 = new JPanel();
    String x = " ";//第一个数
    String y = " ";//第二个数
    String fh = " ";//运算符
    double answer = 0;//运算结果
    String answer2 = " ";//最终呈现在文本框中的结果
    JPanel jp2;//第一个面板
    JTextField jf1;
    JTextField jf2;
    JLabel label = new JLabel();

    //构造出计算器的组件结构
    public Calculate() {
        //用面板来装文本框
        jp2 = new JPanel(new GridLayout(2, 1));//两行一列的面板
        jf1 = new JTextField();
        jf2 = new JTextField();
        //将文本框放入面板中
        jp2.add(jf1);
        jp2.add(jf2);
        add(jp2, BorderLayout.NORTH);
        //输入的计算式出现在左边，同时初始值定为0
        jf1.setHorizontalAlignment(JTextField.LEFT);
        jf1.setText("0");
        jf1.setBackground(Color.lightGray);
        //设置第一个文本框的字体、粗细、大小
        Font f1 = new Font("宋体", Font.BOLD, 18);
        jf1.setFont(f1);
        //结果出现出现在第二个文本框的右边
        jf2.setHorizontalAlignment(JTextField.RIGHT);
        jf2.setBackground(Color.lightGray);
        Font f2 = new Font("宋体", Font.BOLD, 18);
        jf2.setFont(f2);

        add(jp1, BorderLayout.CENTER);//将第二个面板添加到窗体中

        jp1.setLayout(new GridLayout(4, 4));//利用面板制作表格布局
        //将按钮存放在数组中，并遍历数组将每个组件加入面板中
        String[] str = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "CE", "+", "="};
        for (String i : str) {
            addButton(i);
        }
    }

    public void addButton(String a) {
        JButton jb = new JButton(a);
        Font f3 = new Font("宋体", Font.BOLD, 18);//设置每个组件的字体，粗细，大小
        jb.setFont(f3);
        jp1.add(jb);
        jb.addActionListener(this);//将自身注册为自己的监听程序

    }

    //通过符号的对比来实现对应的计算功能
    public void yunsuan(String fh) {
        if (fh.equals("+")) {
            answer = Double.parseDouble(x) + Double.parseDouble(y);//通过包装类的方法将字符转化为对应的浮点型，计算出结果
        }
        if (fh.equals("-")) {
            answer = Double.parseDouble(x) - Double.parseDouble(y);//通过包装类的方法将字符转化为对应的浮点型，计算出结果
        }
        if (fh.equals("*")) {
            answer = (Double.parseDouble(x)) * (Double.parseDouble(y));//通过包装类的方法将字符转化为对应的浮点型，计算出结果
        }
        if (fh.equals("/")) {
            answer = Double.parseDouble(x) / Double.parseDouble(y);//通过包装类的方法将字符转化为对应的浮点型，计算出结果
        }
        if (fh.equals("=")) {
            answer2 = Double.toString(answer);
            jf2.setText(answer2);
        }
        if (fh.equals("CE")) {
            x = " ";//第一个数
            y = " ";//第二个数
            this.fh = " ";

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) throws IndexOutOfBoundsException {
        if (e.getActionCommand().equals("0")
                || e.getActionCommand().equals("1")
                || e.getActionCommand().equals("2")
                || e.getActionCommand().equals("3")
                || e.getActionCommand().equals("4")
                || e.getActionCommand().equals("5")
                || e.getActionCommand().equals("6")
                || e.getActionCommand().equals("7")
                || e.getActionCommand().equals("8")
                || e.getActionCommand().equals("9")
        ) {//判断那个位于运算符号的前面，设置其为对应的x,y，同时将他们呈现到文本框中
            if (fh.equals(" ")) {
                x = e.getActionCommand();
                jf1.setText(x);
            }
//            else if(fh.equals("CE"))
//            {
//                fh=" ";
//            }
            else {
                y = e.getActionCommand();
                jf1.setText(x + fh + y);
                yunsuan(fh);

            }
        } else {
            //判断符号，找到对应的符号对应的计算方法
            if (e.getActionCommand().equals("+")) {
                //第一次直接输出式子
                if (fh.equals(" ")) {
                    fh = "+";
                    jf1.setText(x + fh);
                }
                //如果有累加情况时，这时符号肯定不等于" ",可以根据这个进行判断是第一次加还是累加，如果不是第一次，则x为上一次的结果，然后后面程序不变
                else {
                    x = answer2;
                    jf1.setText(x);
                    fh = "+";
                    jf1.setText(x + fh);
                }
            }
            if (e.getActionCommand().equals("-")) {
                if (fh.equals(" ")) {
                    fh = "-";
                    jf1.setText(x + fh);
                } else {
                    x = answer2;
                    jf1.setText(x);
                    fh = "-";
                    jf1.setText(x + fh);
                }
            }
            if (e.getActionCommand().equals("*")) {
                if (fh.equals(" ")) {
                    fh = "*";
                    jf1.setText(x + fh);
                } else {
                    x = answer2;
                    jf1.setText(x);
                    fh = "*";
                    jf1.setText(x + fh);
                }

            }
            if (e.getActionCommand().equals("/")) {
                if (fh.equals(" ")) {
                    fh = "/";
                    jf1.setText(x + fh);
                } else {
                    x = answer2;
                    jf1.setText(x);
                    fh = "/";
                    jf1.setText(x + fh);
                }
            }
            if (e.getActionCommand().equals("=")) {
                jf1.setText(x + fh + y + "=");
                fh = "=";
                yunsuan(fh);
            }
            if (e.getActionCommand().equals("CE")) {
                //如果是清空键，则第一个文本框置为0，第二个文本框为空白，同时还需要通过yunsuan方法将之前的数据全部初始化
                jf1.setText("0");
                jf2.setText("");
                fh = "CE";
                yunsuan(fh);
            }
        }
    }
}

class testGUI {
    public static void main(String[] args) {
        JFrame calculate = new Calculate();
        calculate.setTitle("XQY Calculate");
        calculate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭即停止运行
        calculate.setSize(400, 400);
        Point point = new Point(500, 500); // 设置坐标
        calculate.setLocation(point); //设置窗体坐标

        calculate.setVisible(true);
    }
}