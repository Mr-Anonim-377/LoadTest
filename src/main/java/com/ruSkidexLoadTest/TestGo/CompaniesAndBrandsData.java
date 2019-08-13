package com.ruSkidexLoadTest.TestGo;

import com.google.gson.Gson;
import com.ruSkidexLoadTest.RestMethod.Gson.*;
import com.ruSkidexLoadTest.RestMethod.Gson.Reqest.RpcBrandsAndCompaniesByCharPayload;
import com.ruSkidexLoadTest.RestMethod.Gson.Reqest.RpcMessage;
import com.ruSkidexLoadTest.RestMethod.HTTPMethodRpc;

import org.testng.annotations.Test;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ruSkidexLoadTest.Configuration.Configuration.*;


public class CompaniesAndBrandsData {
    HTTPMethodRpc htttpRpc = new HTTPMethodRpc();
    String uri = "/";
    Gson g = new Gson();
    RpcMessage companiesFiltersReq = new RpcMessage();
    RpcMessage brandsFiltersReq = new RpcMessage();
    RpcMessage companiesByChar = new RpcMessage();
    RpcMessage brandsByChar = new RpcMessage();
    String RpcJsonReq;


    @Test
    public void yandexTankData() {
        try {
            NEW_FILE.createNewFile();
            WRITER_FILE_OUT = new FileWriter(FILE_OUT_REP + "ammoCompaniesAndBrands.txt", false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            WRITER_FILE_OUT.write("[Connection: close]\n" +
                    "[Host: t.skidex.ru]\n" +
                    "[User-Agent: YTank]\n" +
                    "[Cookie: PHPSESSID:6avm7htb95ldu9cqugjpuj6v5v]\n" +
                    "[Accept-encoding: gzip, deflate, br]");
            WRITER_FILE_OUT.append('\n');
            WRITER_FILE_OUT.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        companiesFiltersReq.setMessages(companiesBrandsFiltersSetMessage("companiesFilters"));
        RpcJsonReq = g.toJson(companiesFiltersReq);
            String ansverRpcCompaniesFiltres = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

    ArrayList<String> companiesFilters = new ArrayList<String>();
    RpcFiltresBrandsCompaniesGson.Filtres methodCompaniesRPC = g.fromJson(ansverRpcCompaniesFiltres, RpcFiltresBrandsCompaniesGson.Filtres.class);

    if (methodCompaniesRPC != null) {
        List<RpcFiltresBrandsCompaniesGson.Message> messages = methodCompaniesRPC.getMessages();
        if (messages != null) {
            RpcFiltresBrandsCompaniesGson.Message message = messages.get(0);
            if (message != null) {
                List<RpcFiltresBrandsCompaniesGson.Payload> payloads = message.getPayload();
                if (payloads != null) {
                    for (int i = 0; i < payloads.size(); i++) {
                        RpcFiltresBrandsCompaniesGson.Payload payload = payloads.get(i);
                        if (payload != null) {
                            if (payload.getTitle().equals("ru") || payload.getTitle().equals("en")) {
                                List<RpcFiltresBrandsCompaniesGson.Child> childrens = payload.getChildren();
                                if (childrens != null && !childrens.isEmpty()) {
                                    for (int o = 0; o < childrens.size(); o++) {
                                        RpcFiltresBrandsCompaniesGson.Child child = childrens.get(o);
                                        if (child != null) {
                                            companiesFilters.add(child.getTitle());

                                        }
                                    }
                                }
                            } else {
                                companiesFilters.add(payload.getTitle());

                            }
                        }
                    }
                }
            }
        }
    }


    for (int i = 0; i < companiesFilters.size(); i++) {
        int page = 0;
        int pageCount = 1;
        do {
            page++;

            companiesByChar.setMessages(companiesByCharSetMessage("companiesByChar",companiesFilters.get(i),page));
            RpcJsonReq = g.toJson(companiesByChar);
            String answerRpcCompanies = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

            RpcBrandsCompaniesGson.ByChar byChar = g.fromJson(answerRpcCompanies, RpcBrandsCompaniesGson.ByChar.class);
            if (byChar != null) {
                List<RpcBrandsCompaniesGson.Message> messages = byChar.getMessages();
                if (messages != null && !messages.isEmpty()) {
                    RpcBrandsCompaniesGson.Message message = messages.get(0);
                    if (message != null) {
                        RpcBrandsCompaniesGson.Payload payload = message.getPayload();
                        if (payload != null) {
                            List<RpcBrandsCompaniesGson.List_> lists = payload.getList();
                            if (lists != null && !lists.isEmpty()) {
                                int countList = lists.size();

                                for (int w = 0; w < countList; w++) {
                                    RpcBrandsCompaniesGson.List_ list = lists.get(w);
                                    if (list != null) {
                                        Long currentList = list.getId();
                                        try {
                                            WRITER_FILE_OUT.write("/companies/" + currentList + "\n");
                                            WRITER_FILE_OUT.flush();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }

                        }


                    }

                }

            }


        } while (pageCount != page);


    }








        brandsFiltersReq.setMessages(companiesBrandsFiltersSetMessage("brandsFilters"));
        RpcJsonReq = g.toJson(companiesFiltersReq);

        String ansverRpcBrandsFiltres = htttpRpc.getRpcMethodAnswer(RpcJsonReq);


    ArrayList<String> brandsFilters = new ArrayList<String>();
    RpcFiltresBrandsCompaniesGson.Filtres methodBrandsRPC = g.fromJson(ansverRpcBrandsFiltres, RpcFiltresBrandsCompaniesGson.Filtres.class);

    if (methodBrandsRPC != null) {
        List<RpcFiltresBrandsCompaniesGson.Message> messages = methodBrandsRPC.getMessages();
        if (messages != null) {
            RpcFiltresBrandsCompaniesGson.Message message = messages.get(0);
            if (message != null) {
                List<RpcFiltresBrandsCompaniesGson.Payload> payloads = message.getPayload();
                if (payloads != null) {
                    for (int i = 0; i < payloads.size(); i++) {
                        RpcFiltresBrandsCompaniesGson.Payload payload = payloads.get(i);
                        if (payload != null) {
                            if (payload.getTitle().equals("ru") || payload.getTitle().equals("en")) {
                                List<RpcFiltresBrandsCompaniesGson.Child> childrens = payload.getChildren();
                                if (childrens != null && !childrens.isEmpty()) {
                                    for (int o = 0; o < childrens.size(); o++) {
                                        RpcFiltresBrandsCompaniesGson.Child child = childrens.get(o);
                                        if (child != null) {
                                            brandsFilters.add(child.getTitle());

                                        }
                                    }
                                }
                            } else {
                                brandsFilters.add(payload.getTitle());

                            }
                        }
                    }
                }
            }
        }
    }


    for (int i = 0; i < brandsFilters.size(); i++) {
        int page = 0;
        int pageCount = 1;
        do {
            page++;
            brandsByChar.setMessages(companiesByCharSetMessage("brandsByChar",companiesFilters.get(i),page));
            RpcJsonReq = g.toJson(brandsByChar);


            String answerRpcBrands = htttpRpc.getRpcMethodAnswer(RpcJsonReq);

            RpcBrandsCompaniesGson.ByChar byChar = g.fromJson(answerRpcBrands, RpcBrandsCompaniesGson.ByChar.class);
            if (byChar != null) {
                List<RpcBrandsCompaniesGson.Message> messages = byChar.getMessages();
                if (messages != null && !messages.isEmpty()) {
                    RpcBrandsCompaniesGson.Message message = messages.get(0);
                    if (message != null) {
                        RpcBrandsCompaniesGson.Payload payload = message.getPayload();
                        if (payload != null) {
                            List<RpcBrandsCompaniesGson.List_> lists = payload.getList();
                            if (lists != null && !lists.isEmpty()) {
                                int countList = lists.size();
                                pageCount = payload.getPagesCount();
                                for (int w = 0; w < countList; w++) {
                                    RpcBrandsCompaniesGson.List_ list = lists.get(w);
                                    if (list != null) {
                                        try {
                                            WRITER_FILE_OUT.write("/brands/" + list.getId() + "\n");
                                            WRITER_FILE_OUT.flush();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }

                        }


                    }

                }

            }


        } while (pageCount != page);


    }


        try {
            WRITER_FILE_OUT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<RpcMessage.Message> companiesByCharSetMessage(String setMessageType,String setPayloadChar,int setPayloadPage) {
        List< RpcMessage.Message > messagesReq = new ArrayList<>();
        RpcMessage.Message messageReq = new RpcMessage.Message();
        RpcBrandsAndCompaniesByCharPayload.Payload payloadReq = new RpcBrandsAndCompaniesByCharPayload.Payload();

        payloadReq.setPage(setPayloadPage);
        payloadReq.setChar(setPayloadChar);
        messageReq.setPayload(payloadReq);
        messageReq.setType(setMessageType);

        messagesReq.add(messageReq);
        return messagesReq;
    }


    private List<RpcMessage.Message> companiesBrandsFiltersSetMessage(String messageSetType) {
        List<RpcMessage.Message> messagesReq = new ArrayList<>();
        RpcMessage.Message messageReq = new RpcMessage.Message();
        messageReq.setType(messageSetType);
        messagesReq.add(messageReq);

        return messagesReq;
    }

}