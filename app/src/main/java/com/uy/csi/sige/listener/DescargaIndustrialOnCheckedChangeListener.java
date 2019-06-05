package com.uy.csi.sige.listener;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.ControlsUtil;

public class DescargaIndustrialOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

    private Saneamiento saneamiento;
    private EditText txtTipoEfluente;

    public DescargaIndustrialOnCheckedChangeListener( Saneamiento saneamiento, EditText txtTipoEfluente ) {
        this.txtTipoEfluente = txtTipoEfluente;
        this.saneamiento = saneamiento;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        saneamiento.setDescargaIndustrial( ConstanteText.NO );
        ControlsUtil.visibility(View.GONE, txtTipoEfluente );

        if (isChecked) {
            saneamiento.setDescargaIndustrial( ConstanteText.SI );
            ControlsUtil.visibility( View.VISIBLE, txtTipoEfluente );
        }
    }
}
