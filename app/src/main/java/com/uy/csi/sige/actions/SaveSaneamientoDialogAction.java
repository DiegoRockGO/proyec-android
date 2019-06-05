package com.uy.csi.sige.actions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.uy.csi.sige.MenuMainActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.dao.SaneamientoDao;
import com.uy.csi.sige.dao.SaneamientoDaoImpl;
import com.uy.csi.sige.entity.PictureTemporal;
import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.services.PictureEventService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogActions;
import com.uy.csi.sige.tools.DialogUtil;

import java.io.File;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

public class SaveSaneamientoDialogAction implements DialogActions {

    private Saneamiento saneamiento;
    private FragmentActivity fragmentActivity;
    private SaneamientoDao saneamientoDao;
    private PictureEventService pictureEventService;
    private Context context;

    private List<PictureTemporal> temporalList;

    public SaveSaneamientoDialogAction(Context context, Saneamiento saneamiento, List<PictureTemporal> temporalList, FragmentActivity fragmentActivity) {
        this.context = context;
        this.saneamiento = saneamiento;
        this.fragmentActivity = fragmentActivity;
        this.temporalList = temporalList;
        this.saneamientoDao = new SaneamientoDaoImpl( context );
        this.pictureEventService = new PictureEventService( context );
    }

    @Override
    public void confirm() {

        if( isEmpty( saneamiento.getLatitud()) || isEmpty( saneamiento.getLongitud() ) ){

            ConfirmSaneamientoDialogAction confirmSaneamientoDialogAction = new ConfirmSaneamientoDialogAction(context, saneamiento, temporalList, fragmentActivity );

            DialogUtil.showConfirm( fragmentActivity.getResources().getString( R.string.msj_save_sin_coordenadas ),
                    ConstanteText.NAME_FRAGMENT_DIALOG,
                    fragmentActivity.getFragmentManager(),
                    confirmSaneamientoDialogAction);

        }else{

            saneamiento = saneamientoDao.insertar(saneamiento);

            if (!isEmpty(temporalList)) {
                for (PictureTemporal temporal : temporalList) {

                    try {
                        if (temporal.getState() == ConstanteNumeric.OPEN) {
                            pictureEventService.save( PictureTemporal.toBeanFoto( temporal, saneamiento.getId() ) );
                        } else {
                            Archivo.eliminarArchivo( join(Archivo.pathStorage(), File.separator, ConstanteText.IMAGEN_DIRECTORY, File.separator, temporal.getName() ) );
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "confirm: ", e);
                    }

                }
            }

            Intent intent = new Intent(fragmentActivity, MenuMainActivity.class);
            intent.putExtra(ConstanteText.KEY_ID_MENU, ConstanteNumeric.OPC_SANEAMIENTO);
            intent.putExtra(ConstanteText.KEY_POSITION_MENU, ConstanteNumeric.OPC_SANEAMIENTO);
            fragmentActivity.startActivity(intent);
            fragmentActivity.finish();
            fragmentActivity.overridePendingTransition(0, 0);

        }

    }

    @Override
    public void cancel() {

    }

    @Override
    public void central() {

    }
}
