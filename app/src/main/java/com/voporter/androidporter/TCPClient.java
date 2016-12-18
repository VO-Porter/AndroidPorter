package com.voporter.androidporter;

/**
 * Created by Lovro on 18.12.2016..
 */

import android.util.Log;

import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient implements Serializable{
    private String serverMessage;
    public String dstAddress;
    private int dstPort;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    /*
        Constructor of the class
     */
    public TCPClient(/*OnMessageReceived listener,*/ String address, int port){
        //mMessageListener = listener;
        dstAddress = address;
        dstPort = port;
    }

    public void sendMessage(JSONObject jsonObject){
        if(out != null && !out.checkError()){
            out.println(jsonObject);
            out.flush();
        }
    }

    public void stopClient(){
        mRun = false;
    }

    public void run(){
        mRun = true;

        try{
            InetAddress serverAddr = InetAddress.getByName(dstAddress);
            Log.e("TCP Client", "C: Connecting....");

            Socket socket = new Socket(serverAddr, dstPort);
            try{

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                Log.e("TCP Client", "C: Sent");

                Log.e("TCP Client", "C: Done");

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                TCPClientHolder.getInstance().setMyTCPClient(this);

                while(mRun){
                    serverMessage = in.readLine();
                    if(serverMessage != null && mMessageListener != null){
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
                }

                Log.e("RESPONSE FROM SERVER", "S: Received message: "+serverMessage);

            }catch (Exception e){
                // TODO: make useful/nicer/better
                Log.e("TCP", "S: ERROR", e);
            }finally {
                socket.close();
            }
        }catch (Exception e){
            // TODO: make useful/nicer/better
            Log.e("TCP", "C: ERROR", e);
        }
    }

    public interface OnMessageReceived{
        public void messageReceived(String message);
    }

}
