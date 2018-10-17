package com.rain.material_animations;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        Slide slide = new Slide();
        // 界面滑动方向
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(500);
        getWindow().setReenterTransition(slide);
        getWindow().setExitTransition(slide);
    }

    private void initView() {
        findViewById(R.id.ll_circular).setOnClickListener(this);
        findViewById(R.id.ll_transitions).setOnClickListener(this);
        findViewById(R.id.ll_sharedelements).setOnClickListener(this);
        findViewById(R.id.ll_viewanimations).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_circular:
                
                break;

            case R.id.ll_transitions:
                startActivity(new Intent(this,TransitionsActivity.class));
                break;

            case R.id.ll_sharedelements:

                break;

            case R.id.ll_viewanimations:

                break;

                default:
        }
    }
}
