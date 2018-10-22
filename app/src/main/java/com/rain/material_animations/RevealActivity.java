package com.rain.material_animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

/**
 * Author:rain
 * Date:2018/10/22 10:11
 * Description:
 */
public class RevealActivity extends BaseActivity {

    private Toolbar mToolbar;
    private Interpolator interpolator;
    private ViewGroup buttonRoot;
    private TextView body;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        initView();
        // 这里设置的title被布局遮挡住了
        initToolBar(mToolbar,true,"Reveal");
        setWindowAnimations();

    }

    private void setWindowAnimations() {
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        setEnterAnimations();
        setExitAnimations();
    }

    private void setExitAnimations() {
        // 设置界面返回动画
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
        fade.setStartDelay(300);
        fade.addListener(new SimpleTransitionListener(){
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide(buttonRoot);
            }
        });
    }

    private void animateButtonsOut() {
        for (int i = 0; i < buttonRoot.getChildCount(); i++) {
            View child = buttonRoot.getChildAt(i);
            child.animate()
                    .setInterpolator(interpolator)
                    .setStartDelay(i)
                    .alpha(0)
                    .scaleX(0)
                    .scaleY(0);
        }
    }

    private void animateRevealHide(final ViewGroup viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initRadius = viewRoot.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    private void setEnterAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new SimpleTransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                hideTarget();
                animateRevealShow(mToolbar);
                animateButtonsIn();
            }

        });
    }

    // 底部4个按钮的进入动画
    private void animateButtonsIn() {
        for (int i = 0; i < buttonRoot.getChildCount(); i++) {
            View child = buttonRoot.getChildAt(i);
            child.animate()
                    .setInterpolator(interpolator)
                    .setStartDelay(100 + 100 * i)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    // 创建toolbar上的动画效果
    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getHeight(), viewRoot.getWidth());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    // 隐藏toolbar上的sharedElement
    private void hideTarget() {
        findViewById(R.id.iv_reveal).setVisibility(View.GONE);
    }

    private void initView() {
        TextView title = findViewById(R.id.title);
        title.setText("Reveal");
        mToolbar = findViewById(R.id.toolbar);
        buttonRoot = findViewById(R.id.reveal_root);
        body = findViewById(R.id.sample_body);
        findViewById(R.id.square_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        findViewById(R.id.square_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.square_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealGreen();
            }
        });

        findViewById(R.id.square_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void revealGreen() {
        animateRevealColor(buttonRoot, R.color.sample_green);
        body.setText(R.string.reveal_body2);
        body.setTextColor(ContextCompat.getColor(this, R.color.theme_green_background));
    }

    private void animateRevealColor(ViewGroup viewGroup, int color) {

    }
}
