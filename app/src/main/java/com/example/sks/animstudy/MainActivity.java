package com.example.sks.animstudy;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.arc_view).setOnClickListener(this);

    }

    public void startAnim(){
        Animator animator = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.rotate_anim);
        animator.setTarget(findViewById(R.id.arc_view));
        animator.setInterpolator(new CustomInterpolator());
        //animator.setStartDelay(2000);

        Animator scaleAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.scale_anim);
        scaleAnim.setTarget(findViewById(R.id.arc_view));
        scaleAnim.setStartDelay(7200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnim);
        //animatorSet.start();

        Animator zoomAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.zoom_anim);
        zoomAnim.setTarget(findViewById(R.id.arc_view));
        zoomAnim.setStartDelay(500);

        Animator rotateAnim = AnimatorInflater.loadAnimator(getApplicationContext(),  R.animator.rotate_bounce_anim);
        rotateAnim.setTarget(findViewById(R.id.arc_view));
        rotateAnim.setInterpolator(new BounceInterpolator());
        //rotateAnim.setStartDelay(200);

        AnimatorSet totalAnimatorSet = new AnimatorSet();
        totalAnimatorSet.playSequentially(animatorSet, zoomAnim, rotateAnim);
        totalAnimatorSet.start();
    }

    @Override
    public void onClick(View view) {
        startAnim();
    }
}
