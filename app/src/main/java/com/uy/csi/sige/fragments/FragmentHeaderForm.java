package com.uy.csi.sige.fragments;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.uy.csi.sige.R;
import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.listener.DepartamentoOnSelectListener;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DateFormat;
import com.uy.csi.sige.tools.ListUtil;

import java.util.Date;
import java.util.List;

public class FragmentHeaderForm extends FragmentBase {

    private static String TAG = "FragmentHeaderForm";

    private TextView txtFecha;
    private TextView txtOtraCiudad;
    private Spinner lstDepartamento;
    private Spinner lstCiudad;
    private Spinner lstSector;
    private Context context;

    private ConfiguracionDao confDao;

    public void initHeaderForm( Context context ){

        this.context = context;
        txtFecha = getTextView( R.id.txtFecha );
        txtOtraCiudad = getTextView( R.id.txtOtraCiudad );
        lstDepartamento = getSpinner( R.id.lstDepartment );
        lstCiudad = getSpinner( R.id.lstCiudad );
        lstSector = getSpinner( R.id.lstSector );

        initServices();
        initListConfiguraciones();
        setDefaultValues();
    }

    public void initServices(){
        confDao = new ConfiguracionDaoImpl(getApplicationContext());
    }

    public void setDefaultValues(){
        txtFecha.setText( DateFormat.toString(DateFormat.FORMATO_DD_MM_AA_HHMMSS, new Date()) );
    }

    public void initListConfiguraciones(){

        List<Configuracion> departamentos = confDao.listConfiguracion( ConstanteText.GRUPO_DEPARTAMENTO );
        departamentos = new ListUtil<Configuracion>().addInPosition(0,
                Configuracion.generateUnselectOption(),
                departamentos );

        ArrayAdapter adapter = new ArrayAdapter( this , android.R.layout.simple_spinner_item, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstDepartamento.setAdapter(adapter);
        lstDepartamento.setOnItemSelectedListener( new DepartamentoOnSelectListener( this , lstCiudad, lstSector, txtOtraCiudad ) );

    }
}
