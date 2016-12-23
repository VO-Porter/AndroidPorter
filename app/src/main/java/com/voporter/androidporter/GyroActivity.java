package com.voporter.androidporter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 *      THIS IS A TEST ACTIVITY
 *      This activity shows how to get the UDPClientWrapper created on MainActivity
 *      as well as tests the send functions of that UDPClientWrapper
 */

public class GyroActivity extends Activity {

    UDPClientWrapper myUDPClientWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);


        myUDPClientWrapper = UDPClientHolder.getInstance().getMyUDPClientWrapper();
/*
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
*/
    }
}
