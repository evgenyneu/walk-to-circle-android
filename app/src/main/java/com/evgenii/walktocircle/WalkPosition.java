package com.evgenii.walktocircle;

import com.google.android.gms.maps.model.LatLng;

public class WalkPosition {

    public LatLng latLng;
    public String name;
    public Boolean reached = false;

    public WalkPosition(String name, LatLng latLng) {
        this.latLng = latLng;
        this.name = name;
    }
}
