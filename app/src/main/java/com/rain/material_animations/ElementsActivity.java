package com.rain.material_animations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;

/**
 * Author:rain
 * Date:2018/10/17 17:32
 * Description:
 * Slide slide = new Slide(Gravity.LEFT);
 * LEFT 当前布局相对于上一个布局的相对位置
 */
public class ElementsActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);
        initView();
        initToolBar(mToolbar,true,"Shared Elements");
        setWindowAnimation();
        setLayout();
    }

    private void setLayout() {
        SharedElementFragment1 sharedElementFragment1 = SharedElementFragment1.newInstance();
        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(500);
        sharedElementFragment1.setExitTransition(slide);
        sharedElementFragment1.setReenterTransition(slide);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.add_fragment, sharedElementFragment1)
                .commit();
    }

    private void setWindowAnimation() {
        getWindow().getEnterTransition().setDuration(500);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
    }


}
