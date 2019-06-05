package com.uy.csi.sige.json.handler;

import android.content.Context;
import android.util.Log;

import com.uy.csi.sige.MainActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.User;
import com.uy.csi.sige.services.UserService;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogUtil;
import com.uy.csi.sige.widget.CustomProgress;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.uy.csi.sige.tools.StringUtil.*;

public class UsuarioResponseHandler extends BaseResponseHandler {
    private static String TAG = "UsuarioResponseHandler";

    private User usuario;

    private UserService usuarioService;

    private Context context;
    private CustomProgress progressDialog;
    private MainActivity mainActivity;

    public UsuarioResponseHandler(Context context, CustomProgress progressDialog, MainActivity mainActivity){

        super(User.class);

        this.progressDialog = progressDialog;
        this.context = context;
        this.mainActivity=mainActivity;
        usuarioService = new UserService( context );

    }

    private void closeProgress(){
        progressDialog.dismiss();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

        try{

            processObject( response );
            List objects = getObjectRest();

            if( !isEmpty(objects) ){
                usuario = (User) getObjectRest().get(0);
            }

            usuario.toString();
            messaggeAutenticacion( usuario.getId() );

        }catch(Exception e){
            Log.e(TAG, "onSuccess: ", e);
            DialogUtil.showDialog( e.getMessage(), ConstanteText.NAME_FRAGMENT_DIALOG, mainActivity.getFragmentManager() );
        }

    }

    private void messaggeAutenticacion(Integer result) throws Exception {

        boolean show_msg=true;

        String msj = null;

        if (result > 0) {
            User usuaExiste = usuarioService.loadUser(null,null,null, usuario.getId());

            if( !isEmpty( usuaExiste ) ){
                usuarioService.update(usuario.getId(), ConstanteNumeric.OPEN);
            }else{
                usuarioService.save( usuario );
            }

            show_msg=false;

        } else if (result == ConstanteNumeric.EXCEPTION_NO_EXISTE) {
            msj = context.getResources() .getString(R.string.msj_user_pssw_mistake);
        } else if (result == ConstanteNumeric.EXCEPTION_SIN_PERMISO_MOVIL) {
            msj = context.getResources().getString(R.string.msj_without_permits);
        } else if (result == ConstanteNumeric.EXCEPTION_ERROR_SERVIDOR) {
            msj = context.getResources().getString(R.string.msj_error_json_server);
        } else if (result == ConstanteNumeric.EXCEPTION_ERROR_JSON_SERVER) {
            msj = context.getResources().getString(R.string.msj_error_json_server);
        }else if (result == ConstanteNumeric.EXCEPTION_SERVIDOR_OFF) {
            msj = context.getResources().getString(R.string.msj_server_off);
        }else if (result == ConstanteNumeric.EXCEPTION_JSON_LOCAL) {
            msj = context.getResources().getString(R.string.msj_error_json_local);
        } else if (result == ConstanteNumeric.EXCEPTION_TIME_OUT) {
            msj = context.getResources().getString(R.string.msj_conection_time_out);
        }else if (result == ConstanteNumeric.EXCEPTION_RETRY) {
            msj = context.getResources().getString(R.string.msj_retry);
        }

        if(show_msg){
            closeProgress();
            DialogUtil.showDialog( msj, ConstanteText.NAME_FRAGMENT_DIALOG, mainActivity.getFragmentManager() );
        }else{
            mainActivity.loadMainMenu(usuario);
        }
    }
}
