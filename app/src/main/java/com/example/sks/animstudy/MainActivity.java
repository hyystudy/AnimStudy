package com.example.sks.animstudy;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleProgress mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.arc_view).setOnClickListener(this);

        mProgress = (CircleProgress) findViewById(R.id.progress);
        mProgress.setVisibility(View.GONE);

    }

    public void startAnim(){
        Animator animator = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.rotate_anim);
        animator.setTarget(findViewById(R.id.arc_view));
        animator.setInterpolator(new CustomInterpolator());

        Animator scaleAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.scale_anim);
        scaleAnim.setTarget(findViewById(R.id.arc_view));
        scaleAnim.setStartDelay(4200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnim);

        Animator zoomAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.zoom_anim);
        zoomAnim.setTarget(findViewById(R.id.arc_view));
        zoomAnim.setStartDelay(500);

       /* Animator rotateAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.rotate_bounce_anim);
        rotateAnim.setTarget(findViewById(R.id.arc_view));
        rotateAnim.setInterpolator(new BounceInterpolator());*/

        Keyframe keyframe = Keyframe.ofFloat(0, 0f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 60f);
        Keyframe keyframe3 = Keyframe.ofFloat(0.8f, -20f);
        Keyframe keyframe4 = Keyframe.ofFloat(0.9f, 20f);
        Keyframe keyframe5 = Keyframe.ofFloat(1f, 0);
        keyframe5.setInterpolator(new BounceInterpolator());
        PropertyValuesHolder rotation = PropertyValuesHolder.ofKeyframe("rotation", keyframe, keyframe2, keyframe3, keyframe4, keyframe5);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(findViewById(R.id.arc_view), rotation);
        objectAnimator.setDuration(1200);
        objectAnimator.setStartDelay(500);

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(zoomAnim, objectAnimator);

        AnimatorSet totalAnimatorSet = new AnimatorSet();
        totalAnimatorSet.playSequentially(animatorSet, animatorSet1);
        totalAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                int  progress = (int) (Math.random() * 100);
                Toast.makeText(getApplicationContext(), String.valueOf(progress), Toast.LENGTH_LONG).show();
                setCircularProgress(progress);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        totalAnimatorSet.start();
    }

    private void setCircularProgress(int progress) {
        Animator zoomAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.zoom_anim);
        zoomAnim.setTarget(mProgress);
        zoomAnim.setDuration(500);
        zoomAnim.start();
        mProgress.setProgress(1f);
        mProgress.setVisibility(View.VISIBLE);
        //zoomAnim.setStartDelay(500);
        int toProgress = 100 - progress;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, toProgress);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                int currProgress = 100 - animatedValue;
                mProgress.setProgress(currProgress * 1.f / 100);
            }
        });
        valueAnimator.setDuration(10 * toProgress);
        valueAnimator.setStartDelay(500);
        valueAnimator.start();
    }

    @Override
    public void onClick(View view) {
        startAnim();
    }
}
