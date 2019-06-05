package com.uy.csi.sige.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import com.uy.csi.sige.MainActivity;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.User;
import com.uy.csi.sige.json.ResponseHttpClient;
import com.uy.csi.sige.json.handler.UsuarioResponseHandler;
import com.uy.csi.sige.services.EventService;
import com.uy.csi.sige.services.UserService;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogUtil;
import com.uy.csi.sige.tools.Encrypta;
import com.uy.csi.sige.widget.CustomProgress;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Denisse on 03/08/2016.
 */
public class Authentication extends AsyncTask<String, String, String> {

    private static String TAG = "Authentication";

    private Context context;
    private User usuario;
    private Integer typeWS;
    private CustomProgress progressDialog;
    private MainActivity mainActivity;

    public Authentication(Context context, User usuario, Integer typeWS, MainActivity mainActivity) {
        this.context = context;
        this.usuario = usuario;
        this.typeWS = typeWS;
        this.mainActivity = mainActivity;

    }

    @Override
    protected void onPreExecute() {
        if (typeWS == ConstanteNumeric.WS_AUTENTICATION) {
            openProgress(context.getResources().getString(R.string.msj_autenticate_user));
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (typeWS == ConstanteNumeric.WS_AUTENTICATION) {
                autenticacionRest( loadJSonObjectUserLogin(usuario.getNickname(), usuario.getPassword()) );
            }
        } catch (Exception e) {
            return ("Error : " + e.getMessage());
        }
        return "";
    }


    @Override
    protected void onPostExecute(final String errMsg) {
    }


    private JSONObject loadJSonObjectUserLogin(String usua, String pswd) {
        JSONObject objectMain = new JSONObject();
        try {
            objectMain.put("nickname", usua);
            objectMain.put("password", Encrypta.encriptar(pswd, Encrypta.ENCRIPTA_SHA1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objectMain;
    }

    private void openProgress(String msg) {

        progressDialog = new CustomProgress();
        progressDialog.setArgs(msg, 0, 0, 0, true);

        if (typeWS == ConstanteNumeric.WS_AUTENTICATION) {
            DialogUtil.showDialogFragment( progressDialog, ConstanteText.NAME_FRAGMENT_PROGRESS, mainActivity.getFragmentManager());
        }
    }

    private void autenticacionRest(JSONObject jsonObject) {
        ResponseHttpClient response = new ResponseHttpClient(ConstanteText.RESOURCE_IS_USUARIO, ResponseHttpClient.APPLICATION_JSON, jsonObject);
        UsuarioResponseHandler uHandler = new UsuarioResponseHandler(context, progressDialog, mainActivity);
        response.put(context, uHandler);
    }

}
