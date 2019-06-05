package com.uy.csi.sige.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.tools.ListUtil;

import java.util.List;

import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_DIAMETRO_CLCTO_MAY_500;
import static com.uy.csi.sige.tools.ConstanteNumeric.VAL_PAR_DISTRITO_OTRO;
import static com.uy.csi.sige.tools.StringUtil.equiv;

public class DiametroColectorOnSelectListener implements AdapterView.OnItemSelectedListener {

    private ConfiguracionDao confDao;
    private Context context;
    private TextView txtDiametroColectorOtro;

    public DiametroColectorOnSelectListener(Context context, TextView txtDiametroColectorOtro) {
        this.context = context;
        this.txtDiametroColectorOtro = txtDiametroColectorOtro;
        confDao = new ConfiguracionDaoImpl( context );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Configuracion diametroColector = ( Configuracion ) parent.getSelectedItem();

        txtDiametroColectorOtro.setVisibility( View.GONE );

        if( equiv(diametroColector.getKey(), VAL_PAR_DIAMETRO_CLCTO_MAY_500 ) ){
            txtDiametroColectorOtro.setVisibility( View.VISIBLE );
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
