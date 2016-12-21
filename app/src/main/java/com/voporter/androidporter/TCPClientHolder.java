package com.voporter.androidporter;

/**
 * Created by Lovro on 18.12.2016..
 */

public class TCPClientHolder {
    private TCPClient myTCPClient;
    public TCPClient getMyTCPClient(){ return myTCPClient;}
    public void setMyTCPClient(TCPClient newTCPClient) {this.myTCPClient = newTCPClient; }


    private static final TCPClientHolder holder = new TCPClientHolder();
    public static TCPClientHolder getInstance() { return holder; }
}
