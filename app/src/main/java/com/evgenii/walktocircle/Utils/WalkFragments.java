package com.evgenii.walktocircle.utils;

import android.app.Fragment;

public class WalkFragments {
    public static Fragment getChildFragment(Fragment parentFragment, int fragmentId) {
        Fragment fragment = parentFragment.getChildFragmentManager().findFragmentById(fragmentId);

        if (fragment == null) {
            fragment = parentFragment.getActivity().getFragmentManager().findFragmentById(fragmentId);
        }

        return fragment;
    }
}
