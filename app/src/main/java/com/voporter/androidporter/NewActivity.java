package com.voporter.androidporter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

/*
    THIS IS A TEST ACTIVITY
    This activity shows how to get the TCPClient created on MainActivity
    as well as tests the send functions of that TCPClient
 */

public class NewActivity extends AppCompatActivity {

    TCPClient myTCPClient;
    Button buttonJump, buttonTestXY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        myTCPClient = TCPClientHolder.getInstance().getMyTCPClient();


        buttonJump = (Button) findViewById(R.id.button);
        buttonTestXY = (Button) findViewById(R.id.button2);


        buttonJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("jump", "1");
                    myTCPClient.sendMessage(jsonObject);

                }catch (Exception e){
                    // TODO:
                }

                //clientWrapper.SendJump();
                //clientWrapper.SendXY(0.5, 0.8);
            }
        });

        buttonTestXY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("x", "1");
                    jsonObject.put("y", "-1");
                    myTCPClient.sendMessage(jsonObject);

                }catch (Exception e){
                    // TODO:
                }

                //clientWrapper.SendJump();
                //clientWrapper.SendXY(0.5, 0.8);
            }
        });

    }
}
