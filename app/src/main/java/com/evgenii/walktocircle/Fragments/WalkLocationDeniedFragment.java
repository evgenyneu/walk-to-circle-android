package com.evgenii.walktocircle.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkCameraDistance;
import com.evgenii.walktocircle.Utils.WalkView;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkLocationPermissions;

public class WalkLocationDeniedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_walk_location_denied, container, false);
        WalkCameraDistance.setFragmentCameraDistance(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Activity activity = getActivity();
        if (activity == null) { return; }

        View requestPermissionGroup = activity.findViewById(R.id.location_denied_request_permission_group);
        View enablePermissionGroup = activity.findViewById(R.id.location_denied_enable_permission_group);

        if (requestPermissionGroup == null || enablePermissionGroup == null) { return; }

        boolean requestPermission = WalkLocationPermissions.getInstance().shouldShowLocationDeniedScreen();

        WalkView.toggleView(requestPermissionGroup, requestPermission);
        WalkView.toggleView(enablePermissionGroup, !requestPermission);
    }

    public void didTapOpenSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + WalkApplication.getAppContext().getPackageName()));
        startActivity(intent);
    }
}
