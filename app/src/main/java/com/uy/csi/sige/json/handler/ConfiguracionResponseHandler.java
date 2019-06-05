package com.uy.csi.sige.json.handler;

import android.content.Context;
import android.util.Log;

import com.uy.csi.sige.MainActivity;
import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import java.util.List;
import cz.msebera.android.httpclient.Header;

import static com.uy.csi.sige.tools.StringUtil.*;

public class ConfiguracionResponseHandler extends BaseResponseHandler {

    private static String TAG = "ConfiguracionResponseHandler";

    private MainActivity mainActivity;
    private List<Configuracion> configuraciones;
    private ConfiguracionDao confDao;

    public ConfiguracionResponseHandler(MainActivity mainActivity, Context context) {
        super(Configuracion.class);

        this.mainActivity = mainActivity;
        confDao = new ConfiguracionDaoImpl( context );
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

        try{
            processObject( response );
            configuraciones = ( List<Configuracion> ) getObjectRest();

            for( Configuracion configuracion: configuraciones){

                Configuracion cPaso = confDao.getConfiguracion( configuracion.getId() );
                if( !isEmpty(cPaso) ){
                    confDao.actualizar( configuracion );
                    continue;
                }
                confDao.insertar( configuracion );
            }

        }catch(Exception e){
            Log.e(TAG, "onSuccess: ", e);
        }

    }
}
