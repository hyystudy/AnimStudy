package com.example.sks.animstudy;

import android.animation.TimeInterpolator;

/**
 * Created by sks on 2016/11/29.
 */
public class CustomInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float v) {
        return v * 2f;
    }
}
