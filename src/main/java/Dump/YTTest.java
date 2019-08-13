package Dump;
import com.google.gson.Gson;
import com.ruSkidexLoadTest.RestMethod.Gson.*;
import com.ruSkidexLoadTest.RestMethod.HTTPMethodRpc;
import com.ruSkidexLoadTest.RestMethod.HttpLocationCheck;
import com.ruSkidexLoadTest.RestMethod.MethodSearch;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;


import static Dump.YTTest.paperCatalogIdT1;
import static Dump.YTTest.paperCatalogIdT2;

class mainT1 implements Runnable {



    @Override
    public void run() {
        MethodSearch mt = new MethodSearch();
        for(String i:paperCatalogIdT1) {
            System.out.println(i);
            try {
                mt.search("t");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}


class mainT2 implements Runnable {

    @Override
    public void run() {

        MethodSearch mt = new MethodSearch();
        for(String i:paperCatalogIdT2) {
            System.out.println(i);
            try {
                mt.search("t");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(mt.search("t"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}


public class YTTest {
    HTTPMethodRpc htttpRpc = new HTTPMethodRpc();
    String uri = "/";
    Gson g = new Gson();
    String currentPaperCatalogUrl;

    String[] currentPaperCatalogUrlArray;

    public ArrayList<String> paperCatalogId = new ArrayList<>();

    public static ArrayList<String> paperCatalogIdT1 = new ArrayList<>();
    public static ArrayList<String> paperCatalogIdT2 = new ArrayList<>();
    int httpResponseCod;







    @Test
    public void yTTest() {

        String answerRpcPaperCatalog = htttpRpc.getRpcMethodAnswer("{\n" +
                "\"messages\": [" +
                "{\n" +
                "    \"type\": \"paperCatalogs\" \n" +
                "}" +
                "]" +
                "}");


        int paperCatalogCount = g.fromJson(answerRpcPaperCatalog, RpcPaperCatalogGson.MethodPaperCatalogs.class).
                getMessages().
                get(0).
                getPayload().
                getCatalogs().
                size();
        for (int x = 0; x < paperCatalogCount; x++) {
            currentPaperCatalogUrl = g.fromJson(answerRpcPaperCatalog, RpcPaperCatalogGson.MethodPaperCatalogs.class).
                    getMessages().
                    get(0).
                    getPayload().
                    getCatalogs().
                    get(x).
                    getUrl();
            currentPaperCatalogUrlArray = currentPaperCatalogUrl.split("/");

            for (int i = 0; i < currentPaperCatalogUrlArray.length; i++) {

                if (currentPaperCatalogUrlArray[i].contains("skidex.ru")) {

                    for (int j = i + 1; j < currentPaperCatalogUrlArray.length; j++) {
                        if (currentPaperCatalogUrlArray[j].matches("-?\\d+(\\.\\d+)?")) {
                            paperCatalogId.add(currentPaperCatalogUrlArray[j]);
                        }
                        uri = uri + currentPaperCatalogUrlArray[j] + "/";
                    }

                    i = currentPaperCatalogUrlArray.length + 1;

                }

            }
//
            uri = "/";
        }
        // Подготовка данных
        int paperCatalogIdSize = paperCatalogId.size() ;
        int average = (paperCatalogIdSize-1)/2;
        boolean thred1 = true;
    for(int p = 0;p<paperCatalogIdSize;p++){

        if(thred1) {
            paperCatalogIdT1.add(paperCatalogId.get(p));
        }else{
            paperCatalogIdT2.add(paperCatalogId.get(p));
        }
        if(p == average){

            thred1 = false;
        }


}


        //Thread one = new Thread(new mainT1());
        Thread two = new Thread(new mainT2());


        //one.start();
        two.start();

        try {
            two.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Разделение на потоки




    }

}
