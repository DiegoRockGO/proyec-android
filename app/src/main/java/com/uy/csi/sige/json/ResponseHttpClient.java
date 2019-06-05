package com.uy.csi.sige.json;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.SyncHttpClient;
import com.uy.csi.sige.tools.ConstanteText;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import static com.uy.csi.sige.tools.StringUtil.*;

public class ResponseHttpClient extends SyncHttpClient {

    private static String TAG = "ResponseHttpClient";

    private String resource;
    private String URI;
    private String header;
    private JSONObject jsonObject;
    private StringEntity stringEntity;

    public static final String APPLICATION_JSON;

    static{
      APPLICATION_JSON = "application/json";
    }

    public ResponseHttpClient(String resource, String header, JSONObject jsonObject){
        super();
        this.resource = resource;
        this.jsonObject = jsonObject;
        this.header = header;
        this.URI = ConstanteText.URL_REST;
        addHeader("Accept", header);
        addHeader("Content-type", header);
        initStringEntity(header);
    }

    private void initStringEntity(String header){
        try{
            stringEntity = new StringEntity( jsonObject.toString() );
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, header));
        }catch(UnsupportedEncodingException e){
            Log.e(TAG, "initStringEntity: ", e);
        }catch(Exception e){
            Log.e(TAG, "initStringEntity: ", e);
        }
    }

    public RequestHandle put(Context context, AsyncHttpResponseHandler handler){
        Log.i(TAG, "get URL: " + join(URI, resource));
        Log.i(TAG, "get Context: " + context);
        Log.i(TAG, "get StringEntity: " + stringEntity);
        Log.i(TAG, "get Header: " + header);
        Log.i(TAG, "get Handler: " + handler);
        return put( context, join(URI, resource), stringEntity, header, handler);
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
