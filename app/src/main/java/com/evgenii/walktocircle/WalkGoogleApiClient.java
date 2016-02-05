package com.evgenii.walktocircle;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class WalkGoogleApiClient implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    public GoogleApiClient getClient() { return mGoogleApiClient; }

    private static WalkGoogleApiClient ourInstance = new WalkGoogleApiClient();

    public static WalkGoogleApiClient getInstance() {
        return ourInstance;
    }

    public Runnable didConnectCallback;

    private WalkGoogleApiClient() {
    }

    public static boolean isConnected() {
        if (WalkGoogleApiClient.getInstance().mGoogleApiClient == null) { return false; }
        return WalkGoogleApiClient.getInstance().mGoogleApiClient.isConnected();
    }

    // Google API client
    // ----------------------

    public void create() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(WalkApplication.getAppContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        }
    }

    // GoogleApiClient.ConnectionCallbacks
    // ----------------------

    @Override
    public void onConnected(Bundle connectionHint) {
        if (didConnectCallback == null) { return; }
        didConnectCallback.run();
    }

    @Override
    public void onConnectionSuspended(int var1) {
    }

    // GoogleApiClient.OnConnectionFailedListener
    // ----------------------

    @Override
    public void onConnectionFailed(ConnectionResult var1) {
    }
}
