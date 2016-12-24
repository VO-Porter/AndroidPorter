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

    float[] gData = new float[3];           // Gravity or accelerometer
    float[] mData = new float[3];           // Magnetometer
    float[] orientation = new float[3];
    float[] Rmat = new float[9];
    float[] R2 = new float[9];
    float[] Imat = new float[9];
    boolean haveGrav = false;
    boolean haveAccel = false;
    boolean haveMag = false;


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
        Sensor gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor asensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(gyroListener, gsensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(gyroListener, asensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(gyroListener, msensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(gyroListener);
        super.onStop();
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {

            switch( event.sensor.getType() ) {
              case Sensor.TYPE_GRAVITY:
                gData[0] = event.values[0];
                gData[1] = event.values[1];
                gData[2] = event.values[2];
                haveGrav = true;
                break;
              case Sensor.TYPE_ACCELEROMETER:
                if (haveGrav) break;
                gData[0] = event.values[0];
                gData[1] = event.values[1];
                gData[2] = event.values[2];
                haveAccel = true;
                break;
              case Sensor.TYPE_MAGNETIC_FIELD:
                mData[0] = event.values[0];
                mData[1] = event.values[1];
                mData[2] = event.values[2];
                haveMag = true;
                break;
              default:
                return;
            }

            if ((haveGrav || haveAccel) && haveMag) {
                SensorManager.getRotationMatrix(Rmat, Imat, gData, mData);
                SensorManager.remapCoordinateSystem(Rmat, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, R2);
                SensorManager.getOrientation(R2, orientation);

                int roll = (int)(orientation[2]*500);
                if (roll < - 350) {
                    udp.sendX(-350);
                    spaceship.setX(leftPosition.getX());
                } else if (roll > 350) {
                    udp.sendX(350);
                    spaceship.setX(rightPosition.getX());
                } else {
                    float pos = ((roll+370)/700.0f)*(rightPosition.getX() - leftPosition.getX());
                    spaceship.setX(pos);
                    udp.sendX(roll);
                }

            }
      }

    };

}
