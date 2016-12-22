package com.voporter.androidporter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import android.os.AsyncTask;

import org.json.JSONObject;

import android.util.Log;

class UDPClientTask extends AsyncTask<Void, Void, Void> {

    private String dstAddress;
    private int dstPort;
    private JSONObject jsonObject;
    private DatagramSocket socket;

    UDPClientTask(String addr, int port, DatagramSocket socket){
        dstAddress = addr;
        dstPort = port;
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(Void... arg0){

        Log.d("DEBUG", jsonObject.toString());

        try {
            socket = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(jsonObject.toString().getBytes() , jsonObject.toString().getBytes().length, InetAddress.getByName(dstAddress), dstPort);
            socket.send(dp);
        } catch (IOException e){
            e.printStackTrace();
        /*} finally {
            if (sendSocket != null){
                try{
                    sendSocket.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        */
        }

        return  null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }

    // TODO: Test this method
    void SetData(JSONObject new_jsonObject){
        jsonObject = new_jsonObject;
    }
}
