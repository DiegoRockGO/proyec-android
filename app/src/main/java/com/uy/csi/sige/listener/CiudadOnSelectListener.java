package com.uy.csi.sige.listener;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.tools.ListUtil;

import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;
import static com.uy.csi.sige.tools.ConstanteNumeric.*;

public class CiudadOnSelectListener implements AdapterView.OnItemSelectedListener {

    private ConfiguracionDao confDao;
    private FragmentActivity fragmentActivity;
    private Spinner lstSector;
    private TextView txtOtraCiudad;

    public CiudadOnSelectListener(FragmentActivity fragmentActivity, Spinner lstSector, TextView txtOtraCiudad) {
        this.fragmentActivity = fragmentActivity;
        this.lstSector = lstSector;
        this.txtOtraCiudad = txtOtraCiudad;
        confDao = new ConfiguracionDaoImpl(fragmentActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Configuracion ciudad = ( Configuracion ) parent.getSelectedItem();
        List<Configuracion> sectores = confDao.listConfiguracionChild( ciudad.getGrupo(), ciudad.getKey() );
        sectores = new ListUtil<Configuracion>().addInPosition(0,
                Configuracion.generateUnselectOption(),
                sectores );
        ArrayAdapter adapter = new ArrayAdapter(fragmentActivity, android.R.layout.simple_spinner_item, sectores );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstSector.setAdapter(adapter);

        lstSector.setVisibility( View.VISIBLE );
        txtOtraCiudad.setVisibility( View.GONE );

        if( equiv(ciudad.getKey(), VAL_PAR_DISTRITO_OTRO ) ){
            lstSector.setVisibility( View.GONE );
            txtOtraCiudad.setVisibility( View.VISIBLE );
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
