package com.voporter.androidporter;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.voporter.androidporter.GyroActivity.JumpState;

class JumpAnimation extends Animation {

    private View view;
    private JumpState state;
    private final int targetHeight;
    private final int targetWidth;
    private int startHeight;
    private int startWidth;

    JumpAnimation(View view, int targetHeight) {
        this.view = view;
        this.startHeight = view.getLayoutParams().height;
        this.startWidth = view.getLayoutParams().width;
        this.targetHeight = targetHeight;
        this.targetWidth = targetHeight * startWidth / startHeight;
        this.state = JumpState.UP;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        if (state == JumpState.UP) {
            view.getLayoutParams().height = (int) (startHeight + (targetHeight - startHeight) * 3 * interpolatedTime);
            view.getLayoutParams().width = (int) (startWidth + (targetWidth - startWidth) * 3 * interpolatedTime);
            if (view.getLayoutParams().height >= targetHeight) {
                state = JumpState.DOWN;

            }
        }
        else if (state == JumpState.DOWN) {
            view.getLayoutParams().height = (int) (targetHeight - (targetHeight - startHeight) * 3 / 2 * (interpolatedTime - 0.333));
            view.getLayoutParams().width = (int) (targetWidth - (targetWidth - startWidth) * 3 / 2 * (interpolatedTime - 0.333));
            if (view.getLayoutParams().height <= startHeight) {
                state = JumpState.INACTIVE;
            }
        }
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

}
