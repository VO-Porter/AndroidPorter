package com.voporter.androidporter;

/**
 * Created by Lovro on 22.12.2016..
 */

public class UDPClientHolder {
    private UDPClientWrapper myUDPClientWrapper;
    public UDPClientWrapper getMyUDPClientWrapper(){ return myUDPClientWrapper;}
    public void setMyUDPClient(UDPClientWrapper newUDPClientWrapper) {this.myUDPClientWrapper = newUDPClientWrapper; }


    private static final UDPClientHolder holder = new UDPClientHolder();
    public static UDPClientHolder getInstance() { return holder; }
}
