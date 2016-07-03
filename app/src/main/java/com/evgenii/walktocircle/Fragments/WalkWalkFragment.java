package com.evgenii.walktocircle.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivity;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkCameraDistance;

public class WalkWalkFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.walk_fragment, container, false);
        WalkCameraDistance.setFragmentCameraDistance(view);
        return view;
    }

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
        MainActivityState.getInstance().savePinLocation(null);
        WalkFragmentType.showWithAnimation();
    }
}
