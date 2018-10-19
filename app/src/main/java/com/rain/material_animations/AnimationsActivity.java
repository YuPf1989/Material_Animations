package com.rain.material_animations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Author:rain
 * Date:2018/10/18 14:25
 * Description:
 * 需要注意Animation动画并未改变view的真正位置，属于补间动画
 */
public class AnimationsActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG  = "AnimationsActivity";

    private Toolbar mToolbar;
    private ImageView iv;
    private boolean sizechanged = false;
    private boolean positionChanged = false;
    private View root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animations);
        initView();
        initToolBar(mToolbar, true, "View animations");
    }

    private void initView() {
        findViewById(R.id.btn_change_position).setOnClickListener(this);
        findViewById(R.id.btn_change_size).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        mToolbar = findViewById(R.id.toolbar);
        iv = findViewById(R.id.iv);
        root = findViewById(R.id.root);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AnimationsActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_position:
                changePosition();
                break;

            case R.id.btn_change_size:
//                startScaleAnimation();
                changeLayout();
                break;

            case R.id.btn_next:
                transationTo(new Intent(this, AnimationsActivity2.class));
                break;

            default:
        }
    }

    private void changePosition() {
        positionChanged = !positionChanged;
        float fromX, toX;
        if (positionChanged) {
            fromX = 0;
            toX = 300;
        } else {
            fromX = 300;
            toX = 0;
        }
        // 属于属性动画
//        ObjectAnimator translationX = ObjectAnimator.ofFloat(iv, "translationX", fromX, toX);
//        translationX.setDuration(500);
//        translationX.start();
        // 或者
        iv.animate()
                .translationX(toX)
                .setDuration(500)
                .start();
    }

    private int savedWidth;

    private void changeLayout() {
        sizechanged = !sizechanged;
        // 加入以下代码是为了使params变化时有一定的过渡效果
        TransitionManager.beginDelayedTransition((ViewGroup) root);
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        // 方式二
        // 之所以只设置了一个宽度，是因为Imageview引入style后已经成为正方形了
        if (sizechanged) {
            savedWidth = params.width;
            params.width = 200;
        } else {
            params.width = savedWidth;
        }
        iv.setLayoutParams(params);
    }

    private void startScaleAnimation() {
        sizechanged = !sizechanged;
        float fromX, toX, fromY, toY;
        // 方式一
        if (sizechanged) {
            fromX = 1;
            toX = 0.4f;
            fromY = 1;
            toY = 0.4f;
        } else {
            fromX = 0.4f;
            toX = 1f;
            fromY = 0.4f;
            toY = 1f;
        }
        // pivot 轴心，相对于自身
        // 属于补间动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        // 做完动画后是否停留在最后位置上
        scaleAnimation.setFillAfter(true);
        iv.startAnimation(scaleAnimation);
    }
}
