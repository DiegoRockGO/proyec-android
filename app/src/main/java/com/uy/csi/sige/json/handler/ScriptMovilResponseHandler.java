package com.uy.csi.sige.json.handler;

import android.content.Context;
import android.util.Log;

import com.uy.csi.sige.MainActivity;
import com.uy.csi.sige.dao.GenericDao;
import com.uy.csi.sige.entity.ScriptMovil;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogUtil;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ScriptMovilResponseHandler extends BaseResponseHandler {

    private static String TAG = "ScriptMovilResponseHandler";

    private MainActivity mainActivity;
    private List<ScriptMovil> scriptMovils;
    private GenericDao<ScriptMovil> gDao;

    public ScriptMovilResponseHandler(MainActivity mainActivity, Context context) {
        super(ScriptMovil.class);

        this.mainActivity = mainActivity;
        this.gDao = new GenericDao<>(ScriptMovil.class, context, "ScriptMovil");
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

        try{
            processObject( response );
            scriptMovils = ( List<ScriptMovil> ) getObjectRest();

            Log.i(TAG, "onSuccess: scripts " + scriptMovils.size());

            for( ScriptMovil scriptMovil : scriptMovils){
                try{
                    gDao.execSQL( scriptMovil.getScript()
                            .replaceAll("\\<\\<", "'")
                            .replaceAll("\\>\\>", "'") );
                }catch(Exception e){
                    Log.e(TAG, "onSuccess: No se puede insertar " + e.getMessage() );
                }


            }

        }catch(Exception e){
            Log.e(TAG, "onSuccess: ", e);
        }

    }
}
