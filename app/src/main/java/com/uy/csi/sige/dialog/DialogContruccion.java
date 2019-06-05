package com.uy.csi.sige.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.uy.csi.sige.R;
import com.uy.csi.sige.adapters.MyConstruccionAdapter;
import com.uy.csi.sige.entity.Construccion;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.ControlsCommons;

import java.util.List;

import static com.uy.csi.sige.tools.ControlsCommons.*;

public class DialogContruccion extends Dialog implements View.OnClickListener{

    private static final String TAG = "RANK: DialogContruccion";

    private List<Construccion> construccionList;
    private MyConstruccionAdapter adapter;

    private EditText txtNivel;
    private EditText txtHabtaciones;
    private EditText txtServicios;
    private EditText txtDestino;
    private EditText txtAreEdif;
    private EditText txtAnio;
    private EditText txtCubierta;

    private Spinner spnCategoria;
    private Spinner spnEstado;

    private Button btnCancelar;
    private Button btnGuardarConstruccion;

    public DialogContruccion(Activity activity, List<Construccion> construccionList, MyConstruccionAdapter adapter) {
        super( activity );
        this.construccionList = construccionList;
        this.adapter = adapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView( R.layout.dlg_frm_contruccion );

        getWindow().setLayout(  ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT );

        linkControls();
    }

    private void linkControls(){
        txtDestino = (EditText)findViewById( R.id.txtDestino );
        txtNivel = (EditText)findViewById( R.id.txtNivel );
        txtHabtaciones= (EditText)findViewById( R.id.txtHabitaciones );
        txtServicios= (EditText)findViewById( R.id.txtServicios );
        spnCategoria = (Spinner)findViewById( R.id.spnCategoria );
        spnEstado = (Spinner)findViewById( R.id.spnEstado );
        txtAreEdif = (EditText) findViewById( R.id.txtAreEdif );
        txtAnio = (EditText) findViewById( R.id.txtAnio );
        txtCubierta = (EditText) findViewById( R.id.txtCubierta );

        btnCancelar = (Button) findViewById( R.id.btnCancelar );
        btnGuardarConstruccion = (Button) findViewById( R.id.btnGuardarConstruccion );

        ArrayAdapter<String> ctgs = new ArrayAdapter<String>( getContext(), R.layout.background_spinner, ConstanteText.CATEGORIAS );
        ArrayAdapter<String> estds = new ArrayAdapter<String>( getContext(), R.layout.background_spinner, ConstanteText.ESTADOS );
        ctgs.setDropDownViewResource(R.layout.spinner_row);
        estds.setDropDownViewResource(R.layout.spinner_row);

        spnCategoria.setAdapter( ctgs );
        spnEstado.setAdapter( estds );

        btnCancelar.setOnClickListener(this);
        btnGuardarConstruccion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardarConstruccion:
                construccionList.add( toConstruccion() );
                adapter.notifyDataSetChanged();
                adapter.updateHeight();
                break;
        }
        dismiss();
    }

    public Construccion toConstruccion(){

        Construccion c = new Construccion();
        c.setDestino( _text( txtDestino ) );
        c.setNivel( _int( txtNivel ) );
        c.setHabitaciones( _int( txtHabtaciones ) );
        c.setServicios( _text( txtServicios ) );

        c.setCategoria( _index( spnCategoria ) );
        c.setCategoriaStr( _text( spnCategoria ) );

        c.setEstado( _index( spnEstado ) );
        c.setEstadoStr( _text( spnEstado ) );

        c.setAreaEdificada( _dbl( txtAreEdif ) );
        c.setCubierta( _text( txtCubierta ) );

        return c;
    }

}
