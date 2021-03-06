package com.voporter.androidporter;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView message;
    EditText editTextAddress, editTextPort;
    Button buttonConnect;
    Intent myIntent;
    boolean errorHappened;

    UDPClientWrapper clientWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        message = (TextView) findViewById(R.id.messageTextView);

        WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
        editTextAddress.setText(ip);

        buttonConnect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                errorHappened = false;
                clientWrapper = new UDPClientWrapper(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
                UDPClientHolder.getInstance().setMyUDPClient(clientWrapper);
                myIntent = new Intent(getApplicationContext(), GyroActivity.class);
                startActivity(myIntent);

            }
        });
    }

}
