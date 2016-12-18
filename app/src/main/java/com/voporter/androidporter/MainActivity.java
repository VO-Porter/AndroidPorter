package com.voporter.androidporter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView response, message;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonTest;
    TCPClient myTCPClient;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        //buttonTest = (Button) findViewById(R.id.buttonTest);
        //response = (TextView) findViewById(R.id.responseTextView);
        message = (TextView) findViewById(R.id.messageTextView);


        buttonConnect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                TCPClientHolder.getInstance().setMyTCPClient(null);

                connectTask CT = new connectTask();
                CT.addr = editTextAddress.getText().toString();
                CT.port = Integer.parseInt(editTextPort.getText().toString());



                try {
                    CT.execute(" ");

                    /*
                    TODO:   Next part of the code is intended for transition to next activity!
                    TODO:   Change "NewActivity" with the real name of next activity and uncomment the rest
                    TODO: IMPORTANT! in NewActivity use "TCPClientHolder.getInstance().getMyTCPClient();" to get TCPClient instance we created here with IP Address and port
                    myIntent = new Intent(getApplicationContext(), NewActivity.class);

                    myIntent.putExtra("TCPClient",  myTCPClient);

                    // wait for TCPClient to be set
                    // Bad, but works for now
                    int killCounter = 0;
                    while(TCPClientHolder.getInstance().getMyTCPClient() == null && killCounter < 100000){
                        killCounter++;
                    }

                    Log.e("KillCounter", ""+killCounter);

                    if(killCounter < 100000) {
                        //Log.e("WHY", "WHY???");
                        startActivity(myIntent);
                    }else{
                        String newMessage = message.getText().toString();
                        newMessage += "Check your internet connection and input information and then try again.";
                        message.setText(newMessage);
                    }
                    */
                } catch (Exception e){
                    // TODO: make Exceptions great again!
                }

            }
        });
    }

    public class connectTask extends AsyncTask<String, String, TCPClient>{
        public String addr;
        public int port;

        @Override
        protected TCPClient doInBackground(String... message_arg){
            try {
                myTCPClient = new TCPClient(addr, port);

                myTCPClient.run();
            } catch (Exception e){
                // TODO: Make it "better", return exception and appropriate message, not generic one
            }

            return myTCPClient;

        }
    }
}
