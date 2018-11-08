package com.app.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {
    public static void main(String[] argv) {

      /*  doPostConnection("101","sathish","arun","12/11/2015",true,"msg");
        doPostConnection("103","sathish","marik","10/11/2011",false,"msg");
        doPostConnection("105","sathish","tamil","11/11/2013",true,"msg");
        //doGetAllConnection();

        doPostMessage("101","1232142","sathish","arun","How are you?","12/11/2015",true);
        doPostMessage("104","1232142","sathish","arun","How?","12/11/2015",true);
        doPostMessage("106","1232142","sathish","arun","Where?","12/11/2015",true);
       // doGetAllMessage();*/
    }

    public static void doPostConnection(String connectionId,String senderId, String receiverId, String connectionDate, Boolean connectionStatus,
                              String connectionType){
        try {
            URL url = new URL("http://localhost:8080/api/connections");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);

            con.setDoOutput(true);

            JSONObject driver = new JSONObject();
            driver.put("connectionId",connectionId);
            driver.put("senderId",senderId);
            driver.put("receiverId",receiverId);
            driver.put("connectionDate",connectionDate);
            driver.put("connectionStatus",connectionStatus);
            driver.put("connectionType",connectionType);

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(driver.toString());
            wr.flush();


            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            con.disconnect();
        }
        catch(Exception e) {
            e.printStackTrace();

        }

    }


    public static void doGetAllConnection() {
        try {
            URL url = new URL("http://localhost:8080/api/connections");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void doDeleteAllConnection() {
        try {
            URL url = new URL("http://localhost:8080/api/connections");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //------------------------------------------------------------------------

    public static void doPostMessage(String messageId,String connectionId, String senderId, String receiverId, String messageContent,
                                        String messageDate, Boolean messageStatus){
        try {
            URL url = new URL("http://localhost:8080/api/messages");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoInput(true);

            con.setDoOutput(true);

            JSONObject driver = new JSONObject();
            driver.put("messageId",messageId);
            driver.put("connectionId",connectionId);
            driver.put("senderId",senderId);
            driver.put("receiverId",receiverId);
            driver.put("messageContent",messageContent);
            driver.put("messageDate",messageDate);
            driver.put("messageStatus",messageStatus);

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(driver.toString());
            wr.flush();


            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            con.disconnect();
        }
        catch(Exception e) {
            e.printStackTrace();

        }

    }


    public static void doGetAllMessage() {
        try {
            URL url = new URL("http://localhost:8080/api/messages");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void doDeleteAllMessage() {
        try {
            URL url = new URL("http://localhost:8080/api/messages");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            int status = con.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
