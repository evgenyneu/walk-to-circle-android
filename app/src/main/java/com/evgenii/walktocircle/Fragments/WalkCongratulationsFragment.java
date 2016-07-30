package com.evgenii.walktocircle.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.WalkConstants;
import com.evgenii.walktocircle.fragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.utils.WalkCameraDistance;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.walkCongrats.WalkCirclesReachedToday;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsImages;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsPhrase;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsSounds;

public class WalkCongratulationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.congratulations_fragment, container, false);
        WalkCameraDistance.setFragmentCameraDistance(view);

        showCongratulationImage(view);
        showCongratulationPhrase(view);
        showNumberOfCirclesReached(view);
        playSound();

        return view;
    }

    public void didTapProceedButton() {
        MainActivityState.saveShowCongratulationsScreen(false);
        WalkFragmentType.showWithAnimation();
    }

    public void didTapImageDescription() {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Milky_Way"));
        MainActivity.instance.startActivity(intent);
    }

    public void didTapShowImageDescription() {
    }

    private void showCongratulationImage(View view) {
        ImageView imageView =  (ImageView) view.findViewById(R.id.congrats_image_view);
        imageView.setImageResource(WalkCongratsImages.getImageId());
    }

    private void showNumberOfCirclesReached(View view) {
        TextView textView =  (TextView) view.findViewById(R.id.congrats_circles_reached_today_text_view);
        textView.setText("By Ian Norman");
    }

    private void showCongratulationPhrase(View view) {
        TextView textView =  (TextView) view.findViewById(R.id.congrats_well_done_phrase_text_view);
        textView.setText("Heavens Above Her");
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void playSound() {
        int soundId = WalkCongratsSounds.getSoundId();
        WalkApplication.getSounds().playSound(soundId, WalkConstants.congratsApplauseVolume);
    }
}
