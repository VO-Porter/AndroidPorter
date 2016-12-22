package com.voporter.androidporter;

import org.json.JSONException;
import org.json.JSONObject;

class UDPClientWrapper {
    private String dstAddress;
    private int dstPort;

    UDPClientWrapper(String address, int port){
        dstAddress = address;
        dstPort = port;
    }

    void SendXY(double x, double y){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("x", Double.valueOf(x));
            jsonObject.put("y", Double.valueOf(y));

            UDPClient myClient = new UDPClient(dstAddress, dstPort);

            myClient.SetData(jsonObject);

            myClient.execute();

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    void SendJump(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("jump", 1);

            UDPClient myClient = new UDPClient(dstAddress, dstPort);

            myClient.SetData(jsonObject);

            myClient.execute();

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
