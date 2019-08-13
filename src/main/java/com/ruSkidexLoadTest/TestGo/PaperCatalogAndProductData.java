package com.ruSkidexLoadTest.TestGo;

import com.google.gson.Gson;
import com.ruSkidexLoadTest.RestMethod.Gson.*;
import com.ruSkidexLoadTest.RestMethod.Gson.Reqest.RpcMessage;
import com.ruSkidexLoadTest.RestMethod.Gson.Reqest.RpcPaperCatalogProductListPayload;
import com.ruSkidexLoadTest.RestMethod.HTTPMethodRpc;
import org.testng.annotations.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static com.ruSkidexLoadTest.TestGo.PaperCatalogAndProductData.*;
import static com.ruSkidexLoadTest.Configuration.Configuration.*;
import static com.ruSkidexLoadTest.TestGo.ThreadPaperProductOne.paperCatalogProductListSetMessage;

class ThreadPaperProductOne implements Runnable {
    private String answerRpcPaperCatalogProduct;

    @Override
    public void run() {
        HTTPMethodRpc htttpRpc = new HTTPMethodRpc();
        RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC methodPaperCatalogsProducts;
        Gson g = new Gson();
        RpcMessage paperCatalogProductListReq = new RpcMessage();
        String RpcJsonReq;

        for (String pcId : paperCatalogIdT1) {

            paperCatalogProductListReq.setMessages(paperCatalogProductListSetMessage(
                    Integer.valueOf(pcId),
                    1,
                    "price",
                    "ASC",
                    "paperCatalogProductList"));

            RpcJsonReq = g.toJson(paperCatalogProductListReq);

            answerRpcPaperCatalogProduct = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

            methodPaperCatalogsProducts = g.fromJson(answerRpcPaperCatalogProduct, RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC.class);

            if(methodPaperCatalogsProducts != null) {
                List<RpcPaperCatalogProductGson.Message> messagesResp = methodPaperCatalogsProducts.getMessages();
                if (!messagesResp.isEmpty()) {
                    RpcPaperCatalogProductGson.Message messageResp = messagesResp.get(0);
                    if (messageResp != null) {
                        RpcPaperCatalogProductGson.Payload payloadResp = messageResp.getPayload();
                        if (payloadResp != null) {
                            Integer code = payloadResp.getCode();
                            if (code == null) {
                                int pagesCount = methodPaperCatalogsProducts.getMessages().get(0).getPayload().getAdditionalData().getPagesCount();

                                for (int page = 1; page < pagesCount + 1; page++) {

                                    paperCatalogProductListReq.setMessages(paperCatalogProductListSetMessage(
                                            Integer.valueOf(pcId),
                                            page,
                                            "price",
                                            "ASC",
                                            "paperCatalogProductList"));

                                    RpcJsonReq = g.toJson(paperCatalogProductListReq);

                                    answerRpcPaperCatalogProduct = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

                                    methodPaperCatalogsProducts = g.fromJson(answerRpcPaperCatalogProduct, RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC.class);

                                    ThreadPaperProductTwo paperCatalogProductPageUriT1 = new ThreadPaperProductTwo();

                                    paperProductUrlT1.add(paperCatalogProductPageUriT1.paperCatalogProductPageUri(methodPaperCatalogsProducts));

                                }
                            }else if(code.equals(500)){
                                try {
                                    throw new Exception("\"code\": 500" +"    "+pcId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }else{
                                System.out.println("\"code\":unknown (error)");
                                System.exit(1);
                            }
                        }else {
                            try {
                                throw new Exception("payload = null");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.exit(1);

                        }

                    }else {
                        try {
                            throw new Exception("message = null");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.exit(1);

                    }

                }else {
                    try {
                        throw new Exception("messages = null");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.exit(1);

                }

            }else {
                try {
                    throw new Exception("methodPaperCatalogsProducts = null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(1);

            }
            }



        }




    public static List<RpcMessage.Message> paperCatalogProductListSetMessage(
                                                            int payloadSetCompanyId,
                                                            int payloadSetPages,
                                                            String payloadsetSort,
                                                            String payloadSetSortDirection,
                                                            String messageSetType) {
        List< RpcMessage.Message > messagesReq = new ArrayList<>();
        RpcMessage.Message messageReq = new RpcMessage.Message();

        RpcPaperCatalogProductListPayload.Payload payloadReq = new RpcPaperCatalogProductListPayload.Payload();

        payloadReq.setCompanyId(payloadSetCompanyId);
        payloadReq.setPage(payloadSetPages);
        payloadReq.setSort(payloadsetSort);
        payloadReq.setSortDirection(payloadSetSortDirection);
        messageReq.setPayload(payloadReq);
        messageReq.setType(messageSetType);

        messagesReq.add(messageReq);

        return messagesReq;
    }
}

class ThreadPaperProductTwo  {
    private String answerRpcPaperCatalogProduct;
    private String currentPaperProductUrl;
    private String[] currentPaperProductUrlArray;
    private StringBuilder uri = new StringBuilder();
    private StringBuilder uris = new StringBuilder();


    public void run() {
        HTTPMethodRpc htttpRpc = new HTTPMethodRpc();
        RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC methodPaperCatalogsProducts;
        Gson g = new Gson();
        RpcMessage paperCatalogProductListReq = new RpcMessage();

        String RpcJsonReq;

        for (String pcId : paperCatalogIdT2) {
            paperCatalogProductListReq.setMessages(paperCatalogProductListSetMessage(
                    Integer.valueOf(pcId),
                    1,
                    "price",
                    "ASC",
                    "paperCatalogProductList"));

            RpcJsonReq = g.toJson(paperCatalogProductListReq);

            answerRpcPaperCatalogProduct = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

            methodPaperCatalogsProducts = g.fromJson(answerRpcPaperCatalogProduct, RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC.class);

            if(methodPaperCatalogsProducts != null) {
                List<RpcPaperCatalogProductGson.Message> messagesResp = methodPaperCatalogsProducts.getMessages();
                if (!messagesResp.isEmpty()) {
                    RpcPaperCatalogProductGson.Message messageResp = messagesResp.get(0);
                    if (messageResp != null) {
                        RpcPaperCatalogProductGson.Payload payloadResp = messageResp.getPayload();
                        if (payloadResp != null) {
                            Integer code = payloadResp.getCode();
                            if (code == null) {
                                int pagesCount = methodPaperCatalogsProducts.getMessages().get(0).getPayload().getAdditionalData().getPagesCount();

                                for (int page = 1; page < pagesCount + 1; page++) {

                                    paperCatalogProductListReq.setMessages(paperCatalogProductListSetMessage(
                                            Integer.valueOf(pcId),
                                            page,
                                            "price",
                                            "ASC",
                                            "paperCatalogProductList"));

                                    RpcJsonReq = g.toJson(paperCatalogProductListReq);

                                    answerRpcPaperCatalogProduct = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

                                    methodPaperCatalogsProducts = g.fromJson(answerRpcPaperCatalogProduct, RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC.class);

                                    ThreadPaperProductTwo paperCatalogProductPageUriT2 = new ThreadPaperProductTwo();

                                    paperProductUrlT2.add(paperCatalogProductPageUriT2.paperCatalogProductPageUri(methodPaperCatalogsProducts));
                                }
                            }else if(code.equals(500)){
                                try {
                                    throw new Exception("\"code\": 500" + pcId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }else{
                                System.exit(1);
                            }
                        }else {
                            try {
                                throw new Exception("payload = null");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.exit(1);

                        }

                        }else {
                        try {
                            throw new Exception("message = null");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.exit(1);

                    }

                    }else {
                    try {
                        throw new Exception("messages = null");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.exit(1);

                }

                }else {
                try {
                    throw new Exception("methodPaperCatalogsProducts = null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(1);

            }

            }
        }


    public String paperCatalogProductPageUri(RpcPaperCatalogProductGson.MethodPaperCatalogsProductsRPC methodPaperCatalogsProducts) {
        uri.append("/");
        if ((methodPaperCatalogsProducts != null)) {
            List<RpcPaperCatalogProductGson.Message> messages = methodPaperCatalogsProducts.
                    getMessages();

            if (messages != null && !messages.isEmpty()) {
                RpcPaperCatalogProductGson.Payload payload = messages.
                        get(0).
                        getPayload();

                if ((payload != null)) {

                    List<RpcPaperCatalogProductGson.Product> products1 = payload.
                            getProducts();
                    if ((products1 != null)) {
                        int paperProductCount = products1.size();

                        for (int w = 0; w < paperProductCount; w++) {
                            currentPaperProductUrl = payload.getProducts().get(w).getUrl();
                            currentPaperProductUrlArray = currentPaperProductUrl.split("/");

                            for (int p = 0; p < currentPaperProductUrlArray.length; p++) {
                                if (currentPaperProductUrlArray[p].contains("skidex.ru")) {

                                    for (int j = p + 1; j < currentPaperProductUrlArray.length; j++) {
                                        uri.append(currentPaperProductUrlArray[j]).append("/");

                                    }

                                    p = currentPaperProductUrlArray.length + 1;
                                }
                            }
                            uris.append(uri).append("\n");
                            uri.setLength(0);
                            uri.append("/");

                        }

                    }
                }

            }
        }
        return uris.toString();

    }


}

class WriteFile {

    private BufferedWriter outFile;

    public void run() {

        if (!paperProductUrlT1.isEmpty() && !paperProductUrlT2.isEmpty()) {
            uriAll.addAll(paperProductUrlT1);
            uriAll.addAll(paperProductUrlT2);
            try {
                outFile = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(FILE_OUT_REP + "ammoPaperCatalogAndProduct.txt"), "UTF-8")
                );
                outFile.write("[Connection: close]\n" +
                        "[Host: t.skidex.ru]\n" +
                        "[User-Agent: YTank]\n" +
                        "[Cookie: PHPSESSID:6avm7htb95ldu9cqugjpuj6v5v]\n" +
                        "[Accept-encoding: gzip, deflate, br]\n");
                outFile.flush();
            } catch (UnsupportedEncodingException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < uriAll.size(); i++) {

                try {
                    outFile.write(uriAll.get(i));
                    if (i % 10 == 0 && i != 0) {
                        outFile.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                outFile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                throw new Exception("Empty paperCatalogIdT1 or paperCatalogIdT2");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(1);
        }


        try {
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


public class PaperCatalogAndProductData {
    public static List<String> uriAll = new ArrayList<>();
    private HTTPMethodRpc htttpRpc = new HTTPMethodRpc();
    private StringBuilder uri = new StringBuilder();
    private Gson g = new Gson();
    private String currentPaperCatalogUrl;

    private String[] currentPaperCatalogUrlArray;

    public List<String> paperCatalogId = new ArrayList<>();

    public static List<String> paperCatalogIdT1 = new ArrayList<>();
    public static List<String> paperCatalogIdT2 = new ArrayList<>();

    public static Thread one = new Thread(new ThreadPaperProductOne());
    public static ThreadPaperProductTwo two = new ThreadPaperProductTwo();
    public static WriteFile three = new WriteFile();
    public static List<String> paperProductUrlT1 = new ArrayList<>();
    public static List<String> paperProductUrlT2 = new ArrayList<>();
    private RpcMessage paperCatalogsReq = new RpcMessage();
    private String RpcJsonReq;

    public static List<RpcMessage.Message> PaperCatalogsSetMessage(String messageSetType) {

        List<RpcMessage.Message> messagesReq = new ArrayList<>();
        RpcMessage.Message messageReq = new RpcMessage.Message();
        messageReq.setType(messageSetType);
        messagesReq.add(messageReq);

        return messagesReq;
    }

    @Test
    public void paperCatalogAndProductData() {
        uri.append("/");
        paperCatalogsReq.setMessages(PaperCatalogsSetMessage("paperCatalogs"));
        RpcJsonReq = g.toJson(paperCatalogsReq);

        String answerRpcPaperCatalog = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

        RpcPaperCatalogGson.MethodPaperCatalogs methodPaperCatalogs = g.fromJson(answerRpcPaperCatalog, RpcPaperCatalogGson.MethodPaperCatalogs.class);
        if (methodPaperCatalogs != null) {
            List<RpcPaperCatalogGson.Message> messages = methodPaperCatalogs.
                    getMessages();
            if (messages != null && !messages.isEmpty()) {
                RpcPaperCatalogGson.Message message = messages.
                        get(0);
                if (message != null) {
                    RpcPaperCatalogGson.Payload payload = message.
                            getPayload();
                    if (payload != null) {
                        List<RpcPaperCatalogGson.Catalog> catalogs = payload.
                                getCatalogs();
                        if (catalogs != null && !catalogs.isEmpty()) {


                            int paperCatalogCount = catalogs.
                                    size();
                            for (int x = 0; x < paperCatalogCount; x++) {
                                List<RpcPaperCatalogGson.Message> messages1 = g.fromJson(answerRpcPaperCatalog, RpcPaperCatalogGson.MethodPaperCatalogs.class).
                                        getMessages();
                                if (messages1 != null) {
                                    RpcPaperCatalogGson.Message message1 = messages1.
                                            get(0);
                                    if (message1 != null) {
                                        RpcPaperCatalogGson.Payload payload1 = message1.
                                                getPayload();
                                        if (payload1 != null) {
                                            List<RpcPaperCatalogGson.Catalog> catalogs1 = payload1.
                                                    getCatalogs();
                                            if (catalogs1 != null && !catalogs.isEmpty()) {
                                                RpcPaperCatalogGson.Catalog catalog = catalogs1.
                                                        get(x);
                                                if (catalog != null) {
                                                    currentPaperCatalogUrl = catalog.
                                                            getUrl();

                                                    currentPaperCatalogUrlArray = currentPaperCatalogUrl.split("/");
                                                    for (int i = 0; i < currentPaperCatalogUrlArray.length; i++) {

                                                        if (currentPaperCatalogUrlArray[i].contains("skidex.ru")) {

                                                            for (int j = i + 1; j < currentPaperCatalogUrlArray.length; j++) {
                                                                if (currentPaperCatalogUrlArray[j].matches("-?\\d+(\\.\\d+)?")) {
                                                                    paperCatalogId.add(currentPaperCatalogUrlArray[j]);
                                                                }
                                                                uri.append(currentPaperCatalogUrlArray[j]).append("/");
                                                            }

                                                            i = currentPaperCatalogUrlArray.length + 1;

                                                        }
                                                    }
                                                    uriAll.add(uri + "\n");
                                                    uri.setLength(0);
                                                    uri.append("/");
                                                }

                                            }

                                        }

                                    }

                                }

                            }
                            // Подготовка данных
                            dataPreparatiop();
                            // Разделение выполнения на потоки
                            one.start();
                            two.run();
                            try {
                                one.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            three.run();

                        }

                    }

                }

            }

        }

    }

    private void dataPreparatiop() {

        int paperCatalogIdSize = paperCatalogId.size();
        if (paperCatalogIdSize != 0) {
            int average = (paperCatalogIdSize) / 2;
            boolean thred1 = true;
            for (int p = 0; p < paperCatalogIdSize; p++) {
                if (p == average) {

                    thred1 = false;
                }
                if (thred1) {
                    paperCatalogIdT1.add(paperCatalogId.get(p));
                } else {
                    paperCatalogIdT2.add(paperCatalogId.get(p));
                }
            }
        } else {
            try {
                throw new Exception("Empty paperCatalogId");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(1);
        }
    }
}