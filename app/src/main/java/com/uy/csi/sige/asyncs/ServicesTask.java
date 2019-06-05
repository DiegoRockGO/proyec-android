package com.uy.csi.sige.asyncs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.uy.csi.sige.MainActivity;
import com.uy.csi.sige.json.ResponseHttpClient;
import com.uy.csi.sige.json.handler.ConfiguracionResponseHandler;
import com.uy.csi.sige.tools.ConstanteText;

import org.json.JSONObject;

public class ServicesTask extends AsyncTask<String, String, String> {

    private static String TAG = "ServicesTask";

    ConfiguracionResponseHandler confResponse;
    Context context;

    public ServicesTask(MainActivity mainActivity) {

        this.context = mainActivity.getApplicationContext();
        this.confResponse = new ConfiguracionResponseHandler( mainActivity, context );

    }

    @Override
    protected String doInBackground(String... strings) {

        Log.i(TAG, "doInBackground: Llamando a los servicios");
        ResponseHttpClient response = new ResponseHttpClient(ConstanteText.RESOURCE_CONFIGURACIONES, ResponseHttpClient.APPLICATION_JSON, new JSONObject());
        response.put( context, confResponse );

        return "";

    }

}
