package com.uy.csi.sige.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.tools.ControlsUtil;

import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_INSP_SNMT_OTRO;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_EJEC_CNX_DOM;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_INSP;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_INSP_PREV;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_INST_INT;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_SANEMIANETO_OTRO;
import static com.uy.csi.sige.tools.StringUtil.equiv;

public class InspeccionSaneamientoOnSelectListener implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "SnmntoOnSelectListener";

    private EditText txtInspSaneamiento;

    public InspeccionSaneamientoOnSelectListener(EditText txtInspSaneamiento) {
        this.txtInspSaneamiento = txtInspSaneamiento;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Configuracion inspeccion = ( Configuracion ) parent.getSelectedItem();

        ControlsUtil.visibility( View.GONE, txtInspSaneamiento );
        if( equiv(inspeccion.getKey(), VAL_PAR_INSP_SNMT_OTRO) ){
            ControlsUtil.visibility( View.VISIBLE, txtInspSaneamiento );
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
