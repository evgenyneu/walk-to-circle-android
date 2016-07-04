package com.evgenii.walktocircle.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkCameraDistance;

public class WalkCongratulationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.congratulations_fragment, container, false);
        WalkCameraDistance.setFragmentCameraDistance(view);
        return view;
    }

    public void didTapProceedButton() {
        MainActivityState.saveShowCongratulationsScreen(false);
        WalkFragmentType.showWithAnimation();
    }
}
