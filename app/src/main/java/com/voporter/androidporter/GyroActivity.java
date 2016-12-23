package com.voporter.androidporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GyroActivity extends Activity {

    UDPClientWrapper udp;

    SensorManager sensorManager;
    Sensor sensor;

    ImageView spaceship;
    Button jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        startService(new Intent(this, GyroService.class));

        udp = UDPClientHolder.getInstance().getMyUDPClientWrapper();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        spaceship = (ImageView) findViewById(R.id.spaceship);
        jump = (Button) findViewById(R.id.jumpButton);

        final int spaceshipSize = spaceship.getLayoutParams().height;
        final int spaceshipJumpSize = (int)(spaceshipSize*1.2);

        jump.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ResizeAnimation spaceshipResizeBigger = new ResizeAnimation(
                                spaceship,
                                spaceshipJumpSize,
                                spaceship.getLayoutParams().height
                        );
                        spaceshipResizeBigger.setDuration(50);
                        spaceship.startAnimation(spaceshipResizeBigger);
                        jump.setPressed(true);
                        udp.sendJump();
                        return true;
                    case MotionEvent.ACTION_UP:
                        ResizeAnimation spaceshipResizeSmaller = new ResizeAnimation(
                                spaceship,
                                spaceshipSize,
                                spaceship.getLayoutParams().height
                        );
                        spaceshipResizeSmaller.setDuration(100);
                        spaceship.startAnimation(spaceshipResizeSmaller);
                        jump.setPressed(false);
                        return true;
                }
                return false;
            }
        });

    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        stopService(new Intent(this, GyroService.class));
        sensorManager.unregisterListener(gyroListener);
        super.onStop();
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            int x = (int)(event.values[0]*1000);
            if (x < - 350)
                udp.sendX(-350);
            else if (x > 350)
                udp.sendX(350);
            else
                udp.sendX(x);
        }
    };

}
