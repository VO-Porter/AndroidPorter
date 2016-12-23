package com.voporter.androidporter;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.voporter.androidporter.GyroActivity.JumpState;

class ResizeAnimation extends Animation {

    private View view;
    private JumpState state;
    private final int targetHeight;
    private final int targetWidth;
    private int startHeight;
    private int startWidth;

    ResizeAnimation(View view, int targetHeight) {
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
            view.getLayoutParams().height = (int) (startHeight + (targetHeight - startHeight) * 2 * interpolatedTime);
            view.getLayoutParams().width = (int) (startWidth + (targetWidth - startWidth) * 2 * interpolatedTime);
            if (view.getLayoutParams().height >= targetHeight) {
                state = JumpState.DOWN;

            }
        }
        else if (state == JumpState.DOWN) {
            view.getLayoutParams().height = (int) (targetHeight - (targetHeight - startHeight) * 2 * (interpolatedTime - 0.5));
            view.getLayoutParams().width = (int) (targetWidth - (targetWidth - startWidth) * 2 * (interpolatedTime - 0.5));
            if (view.getLayoutParams().height <= startHeight) {
                state = JumpState.INACTIVE;
                Log.d("as", state.toString());
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
