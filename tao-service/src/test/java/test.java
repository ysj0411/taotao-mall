import com.taotao.comon.utils.IDUtils;
import org.junit.Test;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class test {


    @Test
    public void showMax() {
        int moneyUp = 188;

        int[] price = {11, 43, 2, 4, 234, 2, 77};

        int max = 0;
        Set<Integer> set = new TreeSet<>();

        for (int i = 0; i < price.length; i++) {
            int balance = moneyUp - price[i];
            System.out.println(balance);
            if (balance > 0) {
                balance = balance - price[i--];
            }
            set.add(balance);


        }

//    System.out.println( moneyUp);


    }
    @Test
    public void show1(){
        long itemId = IDUtils.genItemId();
        System.out.println(itemId);
    }
}








