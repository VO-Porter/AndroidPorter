package com.voporter.androidporter;

/**
 * Created by Lovro on 22.12.2016..
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.os.AsyncTask;

import org.json.JSONObject;

import android.util.Log;

public class UDPClient extends AsyncTask<Void, Void, Void> {
    String dstAddress;
    int dstPort;
    String response = "";
    JSONObject jsonObject;

    UDPClient(String addr, int port){
        dstAddress = addr;
        dstPort = port;
    }

    @Override
    protected Void doInBackground(Void... arg0){

        Log.d("DEBUG", jsonObject.toString());

        DatagramSocket sendSocket = null;
        try{
            sendSocket = new DatagramSocket();

            DatagramPacket dp = new DatagramPacket(jsonObject.toString().getBytes() , jsonObject.toString().getBytes().length, InetAddress.getByName(dstAddress), dstPort);



            sendSocket.send(dp);



        } catch (UnknownHostException e){
            //
            e.printStackTrace();
            response = "UnknownHostException: "+e.toString();
        } catch (IOException e){
            //
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if(sendSocket != null){
                try{
                    sendSocket.close();
                }catch (Exception e){
                    //
                    e.printStackTrace();
                }
            }
        }


        return  null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }

    // TODO: Test this method
    public void SetData(JSONObject new_jsonObject){
        jsonObject = new_jsonObject;
    }
}
