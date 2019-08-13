package Dump;

import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static Dump.Go.TIMESTAMP;

class main implements Runnable {



    @Override
    public void run() {
        for ( int i = 0 ; i<30;i++)
            System.out.println("#"+TIMESTAMP.format(new Date())+"      "+i);

    }


}

    public class Go {
        public static SimpleDateFormat TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    @Test
    public void test(){

        Thread new_one = new Thread(new main());
        Thread new_two = new Thread(new main());


        new_one.start();
        new_two.start();
    }
}
