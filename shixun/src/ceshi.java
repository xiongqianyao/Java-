import java.util.Random;

public class ceshi {
    public static void main(String[] args) {
      int [] str={1,2,3};
        Random random=new Random(2);
        System.out.print(str[random.nextInt(3)]);


    }
}
