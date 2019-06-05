package com.uy.csi.sige.json.handler;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.uy.csi.sige.tools.ClassCommons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.uy.csi.sige.tools.JSONCommons.toJsonArray;
import static com.uy.csi.sige.tools.StringUtil.isEmpty;

public class BaseResponseHandler extends AsyncHttpResponseHandler {

    private static String TAG = "BaseResponseHandler";

    private ClassCommons ccommons;
    private List objectRest;

    public BaseResponseHandler(Class<?> clazz){
        ccommons = new ClassCommons( clazz );
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
        try{
            processObject( response );
        }catch(Exception e){
            Log.e(TAG, "onSuccess: ", e);
        }
    }

    public void processObject( byte[] response ) throws Exception{
        if ( !isEmpty(response) ) {
            objectRest = new ArrayList();
            JSONArray jsonArray = toJsonArray( response );
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                objectRest.add( ccommons.toObject( object ) );
            }
        }
    }

    @Override
    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] response, Throwable e) {
        e.printStackTrace();
    }


    @Override
    public void onStart() {
        // called before request is started
    }


    @Override
    public void onRetry(int retryNo) {

    }

    public List getObjectRest() {
        return objectRest;
    }

    public void setObjectRest(List objectRest) {
        this.objectRest = objectRest;
    }
}
