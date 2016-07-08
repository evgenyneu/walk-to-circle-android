package com.evgenii.walktocircle.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;


import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkCameraDistance;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkWalk.WalkQuote;
import com.evgenii.walktocircle.WalkWalk.WalkRandomQuote;

public class WalkWalkFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.walk_fragment, container, false);
        WalkCameraDistance.setFragmentCameraDistance(view);
        showQuote(view);
        return view;
    }

    // Show quote
    // -----------

    private void showQuote(View view) {
        TextView textViewQuoteText =  (TextView) view.findViewById(R.id.walk_quote_text_text_view);
        TextView textViewQuoteAuthor =  (TextView) view.findViewById(R.id.walk_quote_author_text_view);

        WalkQuote quote = WalkRandomQuote.getQuoteToShow();
        textViewQuoteText.setText(quote.text);
        textViewQuoteAuthor.setText(quote.author);
    }

    // Cancel button
    // --------------

    public void didTapCloseButton() {
        // Ask the user if they want to abandon the current walk

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        abandonTheWalk();
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);
        builder.setMessage(R.string.walk_screen_abandon_this_walk)
                .setPositiveButton(R.string.dialog_button_yes, dialogClickListener)
                .setNegativeButton(R.string.dialog_button_no, dialogClickListener).show();

    }

    private void abandonTheWalk() {
        MainActivityState.saveCurrentCircleLocation(null);
        WalkApplication.getLocationService().stopLocationUpdatesIfNeeded();
        WalkFragmentType.showWithAnimation();
    }
}
