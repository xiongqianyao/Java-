import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str1 = new String();
        System.out.println("请输入一串字符：");
        str1 = sc.nextLine();
        char[] str2 = new char[str1.length()];
        //将字符串转化到字符数组中
        for (int i = 0; i < str1.length(); i++) {
            str2[i] = str1.charAt(i);
        }
        //固定字符串中的一个字母，将他与后面的每一个字符进行比较，如果相同就计数+1，然后将字母改变成null，在进行循环，如若等于null，就跳过
        int count = 0, s = 0;
        for (int i = 0; i < str1.length(); i++) {
            count = 0;
            for (int j = i + 1; j < str1.length(); j++) {
                if (str2[i] == str2[j]) {
                    count++;
                    str2[j] = '0';
                }
                if (str2[i] == '0') {
                    break;
                }
            }
            if (str2[i] != '0') {
                System.out.println(str2[i] + "出现的次数为：" + (count + 1));
                s++;
            }
        }
        System.out.println("共有" + s + "种不同的字符");
    }
}
