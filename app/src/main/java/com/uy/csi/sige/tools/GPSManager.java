package com.uy.csi.sige.tools;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.uy.csi.sige.interfaces.GPSCallback;

import java.util.List;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class GPSManager {

    private static final int gpsMinTime = 500;
    private static final int gpsMinDistance = 1;
    //private int gpsMinTime;
    //private int gpsMinDistance;

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private GPSCallback gpsCallback = null;


    public GPSManager() {


        locationListener = new LocationListener() {
            @Override
            public void onProviderDisabled(final String provider) {

            }

            @Override
            public void onProviderEnabled(final String provider) {

            }

            @Override
            public void onStatusChanged(final String provider, final int status, final Bundle extras) {

            }

            @Override
            public void onLocationChanged(final Location location) {

                /*
                if (location != null && gpsCallback != null) {
                    gpsCallback.onGPSUpdate(location);


                }*/
            }
        };


    }



    public void startListening(Activity activity) {

		/*if ( Build.VERSION.SDK_INT >= 23 &&
				ContextCompat.checkSelfPermission( context, android.t.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return  ;
		}*/


        if (locationManager == null) {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        }

        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);


        //final String bestProvider = locationManager.getBestProvider(criteria, true);
        String bestProvider = locationManager.getBestProvider(criteria, true);


        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            bestProvider=LocationManager.NETWORK_PROVIDER;

        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            bestProvider=LocationManager.GPS_PROVIDER;

        }else if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            bestProvider=LocationManager.PASSIVE_PROVIDER;
        }



        System.out.println("***GPS bestProvider=>" + bestProvider);


        if (bestProvider != null && bestProvider.length() > 0) {
            //locationManager.requestLocationUpdates(bestProvider,GPSManager.gpsMinTime,GPSManager.gpsMinDistance, locationListener);
            System.out.println("***GPS bestProvider !=null=>" );
            locationManager.requestLocationUpdates(bestProvider,gpsMinTime,gpsMinDistance, locationListener);

        } else {
            final List<String> providers = locationManager.getProviders(true);

            for (final String provider : providers) {
                System.out.println("***else GPS provider !=null=>"+provider );
                //locationManager.requestLocationUpdates(provider, GPSManager.gpsMinTime, GPSManager.gpsMinDistance, locationListener);
                locationManager.requestLocationUpdates(provider, gpsMinTime, gpsMinDistance, locationListener);
            }
        }



    }

    public void stopListening() {
        try {
            if (locationManager != null && locationListener != null) {
                locationManager.removeUpdates(locationListener);
            }
            locationManager = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setGPSCallback(GPSCallback gpsCallback) {
        this.gpsCallback = gpsCallback;
    }

    public GPSCallback getGPSCallback() {
        return gpsCallback;
    }
}
