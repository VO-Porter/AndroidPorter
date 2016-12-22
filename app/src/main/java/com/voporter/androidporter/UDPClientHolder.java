package com.voporter.androidporter;


class UDPClientHolder {
    private UDPClientWrapper myUDPClientWrapper;
    UDPClientWrapper getMyUDPClientWrapper() {return myUDPClientWrapper;}
    void setMyUDPClient(UDPClientWrapper newUDPClientWrapper) {this.myUDPClientWrapper = newUDPClientWrapper; }

    private static final UDPClientHolder holder = new UDPClientHolder();
    static UDPClientHolder getInstance() { return holder; }
}
