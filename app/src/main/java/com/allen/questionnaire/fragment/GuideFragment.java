package com.allen.questionnaire.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.questionnaire.R;

/**
 * 引导页的Fragment
 *
 * @author Renjy
 */

public class GuideFragment extends Fragment {
    private View mView;
    private int mBackgroundId;

    public static GuideFragment getInstance(int backgroundId) {
        GuideFragment guideFragment = new GuideFragment();
        guideFragment.mBackgroundId = backgroundId;
        return guideFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_guide, null);
        if (0 != mBackgroundId) {
            mView.setBackground(getResources().getDrawable(mBackgroundId));
        }
        return mView;
    }
}
