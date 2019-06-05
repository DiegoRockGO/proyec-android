package com.uy.csi.sige.listener;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.tools.ControlsUtil;

import static com.uy.csi.sige.tools.ConstanteNumeric.*;

public class TpCnxAlcOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

    public static final String TAG = "TpCnxAlcOnChkChangeList";

    private LinearLayout pnlInstPrevSanTipCnx;

    private Saneamiento saneamiento;

    public TpCnxAlcOnCheckedChangeListener(Saneamiento saneamiento, LinearLayout pnlInstPrevSanTipCnx) {
        this.saneamiento = saneamiento;
        this.pnlInstPrevSanTipCnx = pnlInstPrevSanTipCnx;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        ControlsUtil.visibility(View.GONE, pnlInstPrevSanTipCnx );

        switch (checkedId) {
            case R.id.rdGrpTipoConexionPrev:
                saneamiento.setTipCnxAlc( VAL_PAR_TIP_CNX_ALCANTARILLADO_CNX_PRE );
                ControlsUtil.visibility(View.VISIBLE, pnlInstPrevSanTipCnx );
                break;
            case R.id.rdGrpTipoConexionCam:
                saneamiento.setTipCnxAlc( VAL_PAR_TIP_CNX_ALCANTARILLADO_CAM_VER );
                ControlsUtil.visibility(View.VISIBLE, pnlInstPrevSanTipCnx );
                break;
            case R.id.rdGrpTipoConexionColeCalle:
                saneamiento.setTipCnxAlc( VAL_PAR_TIP_CNX_ALCANTARILLADO_COL_CAL );
                break;
            case R.id.rdGrpTipoConexionColeVereda:
                saneamiento.setTipCnxAlc( VAL_PAR_TIP_CNX_ALCANTARILLADO_COL_VER );
                break;
            case R.id.rdGrpTipoConexionEfluente:
                saneamiento.setTipCnxAlc( VAL_PAR_TIP_CNX_ALCANTARILLADO_EFL_DEC );
                break;
            default:
                saneamiento.setTipCnxAlc(null);
        }

    }


}
