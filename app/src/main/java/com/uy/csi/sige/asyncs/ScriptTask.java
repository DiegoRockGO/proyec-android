package com.uy.csi.sige.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import com.uy.csi.sige.MainActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.json.ResponseHttpClient;
import com.uy.csi.sige.json.handler.ScriptMovilResponseHandler;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogUtil;
import com.uy.csi.sige.widget.CustomProgress;

import org.json.JSONObject;

public class ScriptTask extends AsyncTask<String, String, String> {


    private Context context;
    private Integer typeWS;
    private CustomProgress progressDialog;
    private MainActivity mainActivity;

    public ScriptTask(Context context, Integer typeWS, MainActivity mainActivity) {
        this.context = context;
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
            autenticacionRest();
        } catch (Exception e) {
            return ("Error : " + e.getMessage());
        }
        return "";
    }


    @Override
    protected void onPostExecute(final String errMsg) {
    }

    private void openProgress(String msg) {
        progressDialog = new CustomProgress();
        progressDialog.setArgs(msg, 0, 0, 0, true);

//        if (typeWS == ConstanteNumeric.WS_AUTENTICATION) {
//            DialogUtil.showDialogFragment( progressDialog, ConstanteText.NAME_FRAGMENT_PROGRESS, mainActivity.getFragmentManager());
//        }
    }

    private void autenticacionRest() {
        ResponseHttpClient response = new ResponseHttpClient(ConstanteText.RESOURCE_SCRIPTS, ResponseHttpClient.APPLICATION_JSON, new JSONObject());
        ScriptMovilResponseHandler uHandler = new ScriptMovilResponseHandler(mainActivity, context);
        response.put(context, uHandler);
    }
}
