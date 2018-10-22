package com.rain.material_animations;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvSharedElements;
    private ImageView mIvSharedElementsGreen;
    private ImageView mIvSharedElementsYellow;
    private TextView mTvSharedElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setWindowAnimations();


    }

    // 设置window过场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setWindowAnimations() {
        Slide slide = new Slide(Gravity.LEFT);
        // 界面滑动方向,从左向右滑动
        slide.setDuration(500);
        getWindow().setReenterTransition(slide);
        getWindow().setExitTransition(slide);
    }

    private void initView() {
        findViewById(R.id.ll_circular).setOnClickListener(this);
        findViewById(R.id.ll_transitions).setOnClickListener(this);
        findViewById(R.id.ll_sharedelements).setOnClickListener(this);
        findViewById(R.id.ll_viewanimations).setOnClickListener(this);

        mIvSharedElements = findViewById(R.id.iv_shared_elements);
        mIvSharedElementsGreen = findViewById(R.id.iv_shared_elements_green);
        mIvSharedElementsYellow = findViewById(R.id.iv_shared_elements_yellow);
        mTvSharedElements = findViewById(R.id.tv_shared_elements);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_circular:
                Pair<View, String>[] pairs3 = TransitionHelper.createSafeTransitionParticipants(this, true,
                        new Pair<>(mIvSharedElementsYellow, getString(R.string.transition_reveal1)));
                startActivity(RevealActivity.class, pairs3);
                break;

            case R.id.ll_transitions:
                // 只是执行过渡动画
                Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
                startActivity(TransitionsActivity.class, pairs);
                break;

            case R.id.ll_sharedelements:
                // 执行共享元素动画
                Pair<View, String>[] pairs1 = TransitionHelper.createSafeTransitionParticipants(this, true,
                        new Pair<>(mIvSharedElements, getString(R.string.square_blue_name)),
                        new Pair<>(mTvSharedElements, getString(R.string.sample_blue_title))
                );
                startActivity(ElementsActivity.class, pairs1);
                break;

            case R.id.ll_viewanimations:
                Pair<View, String>[] pairs2 = TransitionHelper.createSafeTransitionParticipants(this, true,
                        new Pair<>(mIvSharedElementsGreen, getString(R.string.square_green_name)));
                startActivity(AnimationsActivity.class, pairs2);
                break;

            default:
        }
    }

    private void startActivity(Class target, Pair[] pairs) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(new Intent(this, target), optionsCompat.toBundle());
    }
}
