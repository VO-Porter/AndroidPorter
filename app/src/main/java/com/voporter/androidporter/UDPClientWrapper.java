package com.voporter.androidporter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramSocket;
import java.net.SocketException;

class UDPClientWrapper {

    private String dstAddress;
    private int dstPort;
    private DatagramSocket socket;

    UDPClientWrapper(String address, int port){
        dstAddress = address;
        dstPort = port;

        try {
            DatagramSocket socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    void sendX(int x){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("x", Double.valueOf(x));
            UDPClientTask client = new UDPClientTask(dstAddress, dstPort, socket);
            client.SetData(jsonObject);
            client.execute();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    void sendJump(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("jump", 1);
            UDPClientTask client = new UDPClientTask(dstAddress, dstPort, socket);
            client.SetData(jsonObject);
            client.execute();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
