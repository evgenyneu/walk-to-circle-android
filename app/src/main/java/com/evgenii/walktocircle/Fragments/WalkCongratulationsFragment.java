package com.evgenii.walktocircle.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.TextView;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkCameraDistance;
import com.evgenii.walktocircle.WalkCongrats.WalkCirclesReachedToday;
import com.evgenii.walktocircle.WalkCongrats.WalkCongratsPhrase;
import com.evgenii.walktocircle.WalkWalk.WalkRandomQuote;

public class WalkCongratulationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.congratulations_fragment, container, false);
        WalkCameraDistance.setFragmentCameraDistance(view);
        showNumberOfCirclesReached(view);
        return view;
    }

    public void didTapProceedButton() {
        MainActivityState.saveShowCongratulationsScreen(false);
        WalkFragmentType.showWithAnimation();
    }

    private void showNumberOfCirclesReached(View view) {
        TextView textView =  (TextView) view.findViewById(R.id.congrats_circles_reached_today_text_view);
        textView.setText(WalkCirclesReachedToday.numberOfCirclesReachedTodayPhrase());
    }
}
