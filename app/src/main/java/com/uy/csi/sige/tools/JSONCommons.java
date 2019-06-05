package com.uy.csi.sige.tools;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.uy.csi.sige.tools.StringUtil.*;

public class JSONCommons {

    private static String TAG = "JSONCommons";

    public static JSONArray toJsonArray(byte[] data){

        JSONArray jsonArray = null;
        try {
            String jsonString = new String( data );

            Log.i(TAG, "toJsonArray: obtainData " + jsonString);

            jsonString = jsonString.replace("\\\"", "'");
            jsonString = jsonString.replace("\"", "'");
            jsonArray = getJsonArray( jsonString );
        } catch (JSONException e) {
            Log.e(TAG, "toJsonArray: ", e);
        }

        return jsonArray;
    }

    private static JSONArray getJsonArray(String jsonStringObject) throws JSONException{

        if( startsWith(jsonStringObject, "'", "\"") ){
            return new JSONArray( jsonStringObject.substring(1, jsonStringObject.length() - 1) );
        }

        return new JSONArray( jsonStringObject );
    }

    public static String getString( JSONObject json, String fieldName ){
        if( isEmpty(json) ){
            return null;
        }

        if( isEmpty(fieldName) ){
            return null;
        }

        if( !isEmpty(json.opt(fieldName)) ){
            return json.opt(fieldName).toString();
        }

        return null;
    }

    public static Integer getInt( JSONObject object, String fieldName ){

        String value = getString( object, fieldName );

        if( isEmpty( value ) || in(value,"null", "NULL") ){
            return null;
        }

        return Integer.parseInt(value);
    }

    public static Double getDouble( JSONObject object, String fieldName ){

        String value = getString( object, fieldName );

        if( isEmpty( value ) || in(value,"null", "NULL") ){
            return null;
        }

        return Double.parseDouble(value);
    }

    public static Boolean getBoolean( JSONObject object, String fieldName ){
        String value = getString( object, fieldName );

        if( isEmpty( value ) || in(value,"null", "NULL") ){
            return null;
        }

        return Boolean.parseBoolean(value);
    }

}
