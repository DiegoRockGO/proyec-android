package com.uy.csi.sige.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.uy.csi.sige.tools.StringUtil.*;
/**
 * Created by Denisse on 03/08/2016.
 */
public class ConnectionDetector {

    private Context contexto;

    public ConnectionDetector(Context contexto) {
        this.contexto = contexto;
    }

    public boolean isConnected() {

        ConnectivityManager conectividad = (ConnectivityManager) contexto.getSystemService( Context.CONNECTIVITY_SERVICE );

        if ( !isEmpty(conectividad) ) {
            NetworkInfo[] allNetworkInfo = conectividad.getAllNetworkInfo();

            if ( !isEmpty( allNetworkInfo ) ){
                for ( NetworkInfo ni : allNetworkInfo ){
                    if ( ni.getState() == NetworkInfo.State.CONNECTED ){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
