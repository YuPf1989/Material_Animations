package com.rain.material_animations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Author:rain
 * Date:2018/10/18 10:37
 * Description:
 */
public class SharedElementFragment1 extends Fragment {

    private ImageView iv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_shared_element, container, false);
        inflate.findViewById(R.id.sample2_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment(false);
            }
        });
        inflate.findViewById(R.id.sample2_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment(true);
            }
        });
        iv = inflate.findViewById(R.id.iv_shared_elements);

        return inflate;
    }

    private void nextFragment(boolean overlap) {
        Slide slide = new Slide(Gravity.RIGHT);
        slide.setDuration(300);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(300);
        SharedElementFragment2 sharedElementFragment2 = SharedElementFragment2.newInstance();
        sharedElementFragment2.setEnterTransition(slide);
        sharedElementFragment2.setSharedElementEnterTransition(changeBounds);
        sharedElementFragment2.setAllowEnterTransitionOverlap(overlap);
        sharedElementFragment2.setAllowReturnTransitionOverlap(overlap);
        getFragmentManager().beginTransaction()
                .replace(R.id.add_fragment, sharedElementFragment2)
                .addSharedElement(iv, getString(R.string.square_blue_name))
                .addToBackStack(null)
                .commit();
    }

    public static SharedElementFragment1 newInstance() {

        Bundle args = new Bundle();

        SharedElementFragment1 fragment = new SharedElementFragment1();
        fragment.setArguments(args);
        return fragment;
    }

}
