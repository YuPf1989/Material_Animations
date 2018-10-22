package com.rain.material_animations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/10/19 9:20
 * Description:
 */
public class AnimationsActivity2 extends BaseActivity {
    private static final String TAG  = "AnimationsActivity2";

    private Toolbar mToolbar;
    private Scene scene0;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
    private ArrayList<View> animationViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations2);
        initViews();
        initToolBar(mToolbar, true, "Scene");
        setLayout();
        setWindowAnimations();
    }

    private void setWindowAnimations() {
        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom));
        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                TransitionManager.go(scene0);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);

    }

    private void setLayout() {
        final ViewGroup sceneRoot = findViewById(R.id.scene_root);
        // 获取布局中的文字场景
        scene0 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_animations2_scene0, this);
        scene0.setEnterAction(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < animationViews.size(); i++) {
                    View view = animationViews.get(i);
                    view.animate()
                            .setStartDelay(i * 100)
                            .scaleX(1)
                            .scaleY(1);
                }
            }
        });

        scene0.setExitAction(new Runnable() {
            @Override
            public void run() {
                // sceneRoot 参数实际应该为包裹4个button的布局，这里的
                // 参数是不对的，介于布局不好修改就用sceneRoot代替了
                TransitionManager.beginDelayedTransition(sceneRoot);
                TextView textView = sceneRoot.findViewById(R.id.tv_title);
                textView.setScaleX(0);
                textView.setScaleY(0);
            }
        });

        Button btn1 = findViewById(R.id.sample3_button1);
        Button btn2 = findViewById(R.id.sample3_button2);
        Button btn3 = findViewById(R.id.sample3_button3);
        Button btn4 = findViewById(R.id.sample3_button4);

        animationViews.add(btn1);
        animationViews.add(btn2);
        animationViews.add(btn3);
        animationViews.add(btn4);

        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene2, this);
        scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene3, this);
        scene4 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_animations_scene4, this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(scene1,new ChangeBounds());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(scene2, TransitionInflater.from(AnimationsActivity2.this).inflateTransition(R.transition.slide_and_changebounds));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(scene3, TransitionInflater.from(AnimationsActivity2.this).
                        inflateTransition(R.transition.slide_and_changebounds_sequential));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(scene4, TransitionInflater.from(AnimationsActivity2.this).
                        inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators));
            }
        });
    }
}
