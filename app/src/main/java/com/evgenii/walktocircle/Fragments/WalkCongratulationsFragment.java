package com.evgenii.walktocircle.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.WalkConstants;
import com.evgenii.walktocircle.fragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.utils.WalkCameraDistance;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.walkCongrats.WalkCirclesReachedToday;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsImageDescription;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsImageDescriptions;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsImages;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsPhrase;
import com.evgenii.walktocircle.walkCongrats.WalkCongratsSounds;

public class WalkCongratulationsFragment extends Fragment {

    String imageUrl;

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
        openImageUrl();
    }

    private void  openImageUrl() {
        if (imageUrl == null) { return; }
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
        MainActivity.instance.startActivity(intent);
    }

    public void didTapShowImageDescription() {
        // Show Wikipedia image instead of "i"
        ImageButton informationButton = (ImageButton) getView().findViewById(R.id.congrats_show_image_description_button);
        informationButton.setBackgroundResource(R.drawable.wikipedia_button_with_pressed_state);

        View phraseScrollView = (View) getView().findViewById(R.id.congrats_phrase_scroll_view);
        View imageDescriptionScrollView = (View) getView().findViewById(R.id.congrats_image_description_scroll_view);

        if (imageDescriptionScrollView.getVisibility() == View.VISIBLE) {
            // Image description already visible, open image URL
            openImageUrl();
        }

        // Hide congrats message and show image description
        phraseScrollView.setVisibility(View.GONE);
        imageDescriptionScrollView.setVisibility(View.VISIBLE);
    }

    private void showCongratulationImage(View view) {
        ImageView imageView =  (ImageView) view.findViewById(R.id.congrats_image_view);
        int imageId = WalkCongratsImages.getImageId();
        imageView.setImageResource(imageId);
        showImageDescription(view, imageId);
    }

    private void showImageDescription(View view, int imageId) {
        WalkCongratsImageDescription imageDescription = WalkCongratsImageDescriptions.getDescription(imageId);

        imageUrl = imageDescription.titleUrl;

        // Show image title
        TextView textViewTitle =  (TextView) view.findViewById(R.id.congrats_image_description_text_view);
        textViewTitle.setText(imageDescription.title);

        // Underline the title
        textViewTitle.setPaintFlags(textViewTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Show image author
        TextView textViewAuthor =  (TextView) view.findViewById(R.id.congrats_image_author_text_view);
        textViewAuthor.setText(imageDescription.author);
    }

    private void showNumberOfCirclesReached(View view) {
        TextView textView =  (TextView) view.findViewById(R.id.congrats_circles_reached_today_text_view);
        textView.setText(WalkCirclesReachedToday.numberOfCirclesReachedTodayPhrase());
    }

    private void showCongratulationPhrase(View view) {
        TextView textView =  (TextView) view.findViewById(R.id.congrats_well_done_phrase_text_view);
        textView.setText(WalkCongratsPhrase.getRandomPhrase());
    }

    private void playSound() {
        int soundId = WalkCongratsSounds.getSoundId();
        WalkApplication.getSounds().playSound(soundId, WalkConstants.congratsApplauseVolume);
    }
}
