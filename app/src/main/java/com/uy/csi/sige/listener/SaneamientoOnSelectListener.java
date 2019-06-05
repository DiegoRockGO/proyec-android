package com.uy.csi.sige.listener;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.tools.ControlsUtil;
import com.uy.csi.sige.tools.ListUtil;

import java.util.List;

import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_DISTRITO_OTRO;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEAMIENTO_OTRO;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_EJEC_CNX_DOM;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_INSP;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_INSP_PREV;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_INST_INT;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_OTRO;
import static com.uy.csi.sige.tools.StringUtil.equiv;

public class SaneamientoOnSelectListener implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "SnmntoOnSelectListener";


    private LinearLayout pnlInsPrev;
    private LinearLayout pnlInspInstDom;
    private LinearLayout pnlEjecCnxDom;
    private LinearLayout pnlInspSan;
    private LinearLayout pnlOtros;
    private LinearLayout pnlEspPavAfectCalle;
    private LinearLayout pnlEspPavAfectPav;

    public SaneamientoOnSelectListener(LinearLayout pnlInsPrev,
                                       LinearLayout pnlInspInstDom,
                                       LinearLayout pnlEjecCnxDom,
                                       LinearLayout pnlInspSan,
                                       LinearLayout pnlOtros,
                                       LinearLayout pnlEspPavAfectCalle,
                                       LinearLayout pnlEspPavAfectPav) {

        this.pnlInsPrev = pnlInsPrev;
        this.pnlInspInstDom = pnlInspInstDom;
        this.pnlEjecCnxDom = pnlEjecCnxDom;
        this.pnlInspSan = pnlInspSan;
        this.pnlOtros = pnlOtros;
        this.pnlEspPavAfectCalle = pnlEspPavAfectCalle;
        this.pnlEspPavAfectPav = pnlEspPavAfectPav;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Configuracion saneamiento = ( Configuracion ) parent.getSelectedItem();

        ControlsUtil.visibility( View.GONE, pnlInsPrev, pnlInspInstDom, pnlEjecCnxDom, pnlInspSan, pnlOtros, pnlEspPavAfectCalle, pnlEspPavAfectPav );

        if( equiv(saneamiento.getKey(), VAL_PAR_SANEMIANETO_INSP_PREV ) ){
            ControlsUtil.visibility( View.VISIBLE, pnlInsPrev );
        }

        if( equiv(saneamiento.getKey(), VAL_PAR_SANEMIANETO_INST_INT ) ){
            ControlsUtil.visibility( View.VISIBLE, pnlInspInstDom );
        }

        if( equiv(saneamiento.getKey(), VAL_PAR_SANEMIANETO_EJEC_CNX_DOM ) ){
            ControlsUtil.visibility( View.VISIBLE, pnlInsPrev, pnlEjecCnxDom, pnlEspPavAfectCalle, pnlEspPavAfectPav );
        }

        if( equiv(saneamiento.getKey(), VAL_PAR_SANEMIANETO_INSP) ){
            ControlsUtil.visibility( View.VISIBLE, pnlInspSan );
        }

        if( equiv(saneamiento.getKey(), VAL_PAR_SANEMIANETO_OTRO) ){
            ControlsUtil.visibility( View.VISIBLE, pnlOtros );
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
