package com.voporter.androidporter;

/**
 * Created by Lovro on 22.12.2016..
 */

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UDPClientWrapper {
    String dstAddress;
    int dstPort;

    UDPClientWrapper(String address, int port){
        dstAddress = address;
        dstPort = port;
    }

    public void SendXY(double x, double y){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("x", new Double(x));
            jsonObject.put("y", new Double(y));

            UDPClient myClient = new UDPClient(dstAddress, dstPort);

            myClient.SetData(jsonObject);

            myClient.execute();

        }catch (JSONException e){
            // TODO: Something - error
            e.printStackTrace();
        }
    }

    public void SendJump(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("jump", new Integer(1));

            UDPClient myClient = new UDPClient(dstAddress, dstPort);

            myClient.SetData(jsonObject);

            myClient.execute();

        }catch (JSONException e){
            // TODO: Something - error
            e.printStackTrace();
        }
    }
}
