package com.voporter.androidporter;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.Animation.AnimationListener;


public class GyroActivity extends Activity {

    enum JumpState { INACTIVE, UP, DOWN}

    UDPClientWrapper udp;

    SensorManager sensorManager;
    Sensor sensor;

    ImageView spaceship, leftPosition, rightPosition;
    Button jump;

    JumpState jumpState = JumpState.INACTIVE;
    AnimationListener jumpListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        udp = UDPClientHolder.getInstance().getMyUDPClientWrapper();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        spaceship = (ImageView) findViewById(R.id.spaceship);
        leftPosition = (ImageView) findViewById(R.id.spaceshipLeft);
        rightPosition= (ImageView) findViewById(R.id.spaceshipRight);
        jump = (Button) findViewById(R.id.jumpButton);

        final int spaceshipSize = spaceship.getLayoutParams().height;
        final int spaceshipJumpSize = (int)(spaceshipSize*1.5);

        jumpListener = new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpState = JumpState.INACTIVE;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };

        jump.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (jumpState == JumpState.INACTIVE) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        jumpState = JumpState.UP;
                        JumpAnimation spaceshipJump = new JumpAnimation(
                                spaceship, spaceshipJumpSize
                        );
                        spaceshipJump.setAnimationListener(jumpListener);
                        spaceshipJump.setDuration(500);
                        spaceship.startAnimation(spaceshipJump);
                        jump.setPressed(true);
                        udp.sendJump();
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        jump.setPressed(false);
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
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
        sensorManager.unregisterListener(gyroListener);
        super.onStop();
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {
            int x = (int)(event.values[0]*1000);
            if (x < - 350) {
                udp.sendX(-350);
                spaceship.setX(leftPosition.getX());
            } else if (x > 350) {
                udp.sendX(350);
                spaceship.setX(rightPosition.getX());
            } else {
                float pos = ((x+350)/700.0f)*(rightPosition.getX() - leftPosition.getX());
                spaceship.setX(pos);
                udp.sendX(x);
            }
        }
    };

}
