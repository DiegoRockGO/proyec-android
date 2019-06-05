package com.uy.csi.sige.listener;

import android.content.Context;
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

public class DepartamentoOnSelectListener implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "DepartamentoOnSelectListener";

    private ConfiguracionDao confDao;
    private FragmentActivity context;

    private Spinner lstDistritos;
    private Spinner lstSector;

    private TextView txtOtraCiudad;

    public DepartamentoOnSelectListener( FragmentActivity context, Spinner lstDistritos, Spinner lstSector, TextView txtOtraCiudad) {
        this.context = context;
        this.lstDistritos = lstDistritos;
        this.lstSector = lstSector;
        this.txtOtraCiudad = txtOtraCiudad;
        this.confDao = new ConfiguracionDaoImpl( context );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Configuracion departamento = ( Configuracion ) parent.getSelectedItem();
        List<Configuracion> distritos = confDao.listConfiguracionChild( departamento.getGrupo(), departamento.getKey() );
        distritos = new ListUtil<Configuracion>().addInPosition(0,
                Configuracion.generateUnselectOption(),
                distritos );

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, distritos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstDistritos.setOnItemSelectedListener( new CiudadOnSelectListener( context, lstSector, txtOtraCiudad ) );
        lstDistritos.setAdapter(adapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
