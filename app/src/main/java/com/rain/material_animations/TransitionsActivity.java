package com.rain.material_animations;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;

/**
 * Author:rain
 * Date:2018/10/17 15:40
 * Description:
 */
public class TransitionsActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);
        setWindowAnimations();
        mToolbar = findViewById(R.id.toolbar);
        initToolBar(mToolbar,true,"Transitions");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);
    }


}
