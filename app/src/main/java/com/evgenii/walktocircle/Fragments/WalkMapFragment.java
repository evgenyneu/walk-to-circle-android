package com.evgenii.walktocircle.Fragments;
import com.evgenii.walktocircle.FragmentManager.WalkFragmentType;
import com.evgenii.walktocircle.MainActivityState;
import com.evgenii.walktocircle.R;
import com.evgenii.walktocircle.Utils.WalkGeo;
import com.evgenii.walktocircle.Utils.WalkLocation;
import com.evgenii.walktocircle.WalkApplication;
import com.evgenii.walktocircle.WalkConstants;
import com.evgenii.walktocircle.WalkGoogleApiClient;
import com.evgenii.walktocircle.WalkLocationPermissions;
import com.evgenii.walktocircle.WalkMap.DropPin;
import com.evgenii.walktocircle.WalkMap.PrepareMapForPin;
import com.evgenii.walktocircle.WalkMap.StartButton;
import com.evgenii.walktocircle.WalkTestReachCircle;
import com.google.android.gms.maps.GoogleMap;
import android.app.Fragment;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.evgenii.walktocircle.Utils.WalkCameraDistance;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class WalkMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private StartButton mStartButton;
    private DropPin mDropPin;
    private PrepareMapForPin mPrepareMapForPin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment, container, false);
        mStartButton = new StartButton();
        mDropPin = new DropPin();
        mPrepareMapForPin = new PrepareMapForPin();
        WalkCameraDistance.setFragmentCameraDistance(view);
        initMap();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mStartButton.stopCountdown();
    }

    @Override
    public void onResume() {
        super.onResume();
        zoomToLastLocationAndStartLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * Zoom the map to location if the map fragment is currently visible.
     */
    public static void ifVisibleEnableMyLocationAndZoomToLastLocation() {
        WalkMapFragment fragment = (WalkMapFragment) WalkFragmentType.Map.getFragmentIfCurrentlyVisibleAndShouldBeVisible();

        if (fragment != null) {
            fragment.enableMyLocationAndZoomToLastLocation();
        }
    }

    public void didTapStartButton() {
        Location lastLocation = getLastLocation();
        if (lastLocation == null) { return; }

        final Location pinLocation = WalkGeo.randomLocationAtDistanceRange(lastLocation,
                WalkConstants.minCircleDistanceFromCurrentLocationMeters,
                WalkConstants.maxCircleDistanceFromCurrentLocationMeters);

        MainActivityState.saveCurrentCircleLocation(WalkLocation.latLngFromLocation(pinLocation));
        WalkApplication.getLocationService().startLocationUpdatesIfNeeded();
        WalkTestReachCircle.getInstance().testCircleReachedAfterSeconds(8);

        Point mapSizePixels = mapSize();
        Point startButtonSizePixels = mStartButton.getSizePixels();
        mStartButton.rotateAndShowInitialNumber();
        mDropPin.showPinCircle(pinLocation, mMap);

        mPrepareMapForPin.prepare(lastLocation, pinLocation, mMap,
                mapSizePixels, startButtonSizePixels, new Runnable() {

            @Override
            public void run() {
                mDropPin.dropPin(pinLocation, mMap);
                mStartButton.startCountdown();
            }
        });
    }

    private Point mapSize() {
        View view = getView();
        if (view == null) { return new Point(1,1); }
        return new Point(view.getWidth(), view.getHeight());
    }

    // Map
    // ----------------------

    void initMap() {
        if (!WalkFragmentType.Map.shouldBeVisibleNow()) { return; }
        MapFragment mapFragment =  MapFragment.newInstance();

        getChildFragmentManager().beginTransaction()
            .setCustomAnimations(R.animator.keep_child_fragment, 0, 0, 0)
            .add(R.id.map, mapFragment)
            .commit();

        mapFragment.getMapAsync(this);
    }

    /**
     * OnMapReadyCallback
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocationAndZoomToLastLocation();
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mStartButton.show();
    }

    public void enableMyLocationAndZoomToLastLocation() {
        if (WalkLocationPermissions.getInstance().hasLocationPermission()) {
            enableMyLocation();
            zoomToLastLocationAndStartLocationUpdates();
        }
    }

    // My location
    // ----------------------

    void enableMyLocation() {
        if (mMap != null && WalkLocationPermissions.getInstance().hasLocationPermission()) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private static Location getLastLocation() {
        if (WalkGoogleApiClient.isConnected() && WalkLocationPermissions.getInstance().hasLocationPermission()) {
            return LocationServices.FusedLocationApi.getLastLocation(
                    WalkGoogleApiClient.getInstance().getClient());
        }

        return null;
    }

    // Zoom to last location and start location updates.
    // This method is supposed to be called when the screen or map is initialized.
    private void zoomToLastLocationAndStartLocationUpdates() {
        // 1. First, get last location and zoom the map there.
        // Last location is returned immediately but can be null.
        // -------------

        Location lastLocation = getLastLocation();

        if (lastLocation != null) {
            zoomMapToLastLocation(lastLocation);
        }

        // 2. Start location updates.
        // Location updates are needed to determine more accurate user location
        // that will be used to drop the new pin.
        // -------------

        startLocationUpdates();
    }

    // The map is zoomed to last known location.
    private void zoomMapToLastLocation(Location location) {
        if (!WalkFragmentType.Map.shouldBeVisibleNow()) { return; }
        if (mMap == null) { return; }

        // Skip zoom if map is already centered correctly
        Location mapCenter =  WalkLocation.getMapCenter(mMap);
        if (mapCenter.distanceTo(location) < 150) {
            float currentZoom = mMap.getCameraPosition().zoom;

            // Skip zoom if already zoomed
            if (Math.abs(WalkConstants.mapInitialZoom - currentZoom) < 1.5) {
                return;
            }
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, WalkConstants.mapInitialZoom));
    }

    // Location updates
    // ----------------------

    void startLocationUpdates() {
        if (!WalkFragmentType.Map.shouldBeVisibleNow()) { return; }
        WalkApplication.getLocationService().startUpdatesForMap();
    }

    void stopLocationUpdates() {
        WalkApplication.getLocationService().stopUpdatesForMap();
    }

    public void didUpdateLocation(Location location) {
        if (!WalkFragmentType.Map.shouldBeVisibleNow()) { return; }
        zoomMapToLastLocation(location);
        stopLocationUpdates();
    }
}
