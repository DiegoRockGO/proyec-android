package com.uy.csi.sige.listener;

import android.widget.RadioGroup;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.Saneamiento;


import static com.uy.csi.sige.tools.ConstanteText.*;

public class CmrProntaOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

    public static final String TAG = "CmrProntaOnChkLst";

    private Saneamiento saneamiento;

    public CmrProntaOnCheckedChangeListener(Saneamiento saneamiento) {
        this.saneamiento = saneamiento;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.optCmrProntaSi:
                saneamiento.setCamaraUnoPronta( SI );
                break;
            case R.id.optCmrProntaNo:
                saneamiento.setCamaraUnoPronta( NO );
                break;
            case R.id.optCmrProntaEnProceso:
                saneamiento.setCamaraUnoPronta( EN_PROCESO );
                break;
            default:
                saneamiento.setCamaraUnoPronta( null );
        }

    }


}
