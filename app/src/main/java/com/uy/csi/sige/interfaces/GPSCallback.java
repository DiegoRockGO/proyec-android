package com.uy.csi.sige.interfaces;

import android.location.Location;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public interface GPSCallback {

    public abstract void onGPSUpdate(Location location);
}
