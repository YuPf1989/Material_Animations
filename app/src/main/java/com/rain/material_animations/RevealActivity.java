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
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Author:rain
 * Date:2018/10/22 10:11
 * Description:
 */
public class RevealActivity extends BaseActivity implements View.OnTouchListener {
    private static final String TAG = "RevealActivity";
    private Toolbar mToolbar;
    private Interpolator interpolator;
    private ViewGroup buttonRoot;
    private TextView body;
    private ImageView btnRed;
    private ImageView ivReveal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        initView();
        // 这里设置的title被布局遮挡住了
        initToolBar(mToolbar, true, "Reveal");
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
        fade.addListener(new SimpleTransitionListener() {
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
                // 这行代码没什么用，并没有预期的效果
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
        ivReveal = findViewById(R.id.iv_reveal);
        findViewById(R.id.square_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealBlue();
            }
        });

        btnRed = findViewById(R.id.square_red);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealRed();
            }
        });

        findViewById(R.id.square_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealGreen();
            }
        });

        findViewById(R.id.square_yellow).setOnTouchListener(this);
    }

    // 黄色幕布效果
    private void revealYellow(float cx, float cy) {
        animateRevealColorCoordinates(buttonRoot, R.color.sample_yellow, (int) cx, (int) cy);
        body.setText(R.string.reveal_body1);
        body.setTextColor(ContextCompat.getColor(RevealActivity.this, R.color.theme_yellow_background));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (view.getId() == R.id.square_yellow) {
                revealYellow(motionEvent.getRawX(), motionEvent.getRawY());
                return true;
            }
        }
        return false;
    }

    // 蓝色幕布效果
    private void revealBlue() {
        animateButtonsOut();
        Animator anim = animateRevealColorCoordinates(buttonRoot, R.color.sample_blue, buttonRoot.getWidth() / 2, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animateButtonsIn();
            }
        });
        body.setText(R.string.reveal_body4);
        body.setTextColor(ContextCompat.getColor(RevealActivity.this, R.color.theme_blue_background));
    }

    // 红色按钮的幕布效果
    private void revealRed() {
        final RelativeLayout.LayoutParams originalParams = (RelativeLayout.LayoutParams) btnRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new SimpleTransitionListener() {
            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealColor(buttonRoot, R.color.sample_red);
                body.setText(R.string.reveal_body3);
                body.setTextColor(ContextCompat.getColor(RevealActivity.this, R.color.theme_red_background));
                btnRed.setLayoutParams(originalParams);
            }
        });

        TransitionManager.beginDelayedTransition(buttonRoot, transition);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnRed.setLayoutParams(layoutParams);
    }

    // 绿色按钮的幕布效果
    private void revealGreen() {
        animateRevealColor(buttonRoot, R.color.sample_green);
        body.setText(R.string.reveal_body2);
        body.setTextColor(ContextCompat.getColor(this, R.color.theme_green_background));
    }

    private void animateRevealColor(ViewGroup viewRoot, int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorCoordinates(viewRoot, color, cx, cy);
    }

    private Animator animateRevealColorCoordinates(ViewGroup viewRoot, int color, int cx, int cy) {
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        // Math.hypot 已知两边求斜边
        double finalRadius = Math.hypot(viewRoot.getHeight(), viewRoot.getWidth());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, (float) finalRadius);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(500);
        anim.start();
        return anim;
    }
}
