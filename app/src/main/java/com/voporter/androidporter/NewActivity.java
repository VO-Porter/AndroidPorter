package com.voporter.androidporter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *      THIS IS A TEST ACTIVITY
 *      This activity shows how to get the UDPClientWrapper created on MainActivity
 *      as well as tests the send functions of that UDPClientWrapper
 */

public class NewActivity extends AppCompatActivity {

    UDPClientWrapper myUDPClientWrapper;
    Button buttonJump, buttonTestXY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        myUDPClientWrapper = UDPClientHolder.getInstance().getMyUDPClientWrapper();

        buttonJump = (Button) findViewById(R.id.button);
        buttonTestXY = (Button) findViewById(R.id.button2);


        buttonJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myUDPClientWrapper.SendJump();

            }
        });

        buttonTestXY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myUDPClientWrapper.SendXY(-1.5, 2.65);

            }
        });

    }
}
