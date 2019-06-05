package com.uy.csi.sige.dto;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import com.uy.csi.sige.R;
import com.uy.csi.sige.adapters.MyConstruccionAdapter;
import com.uy.csi.sige.dialog.DialogContruccion;
import com.uy.csi.sige.entity.Construccion;
import com.uy.csi.sige.entity.Padron;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.ControlsCommons;
import com.uy.csi.sige.tools.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.uy.csi.sige.tools.ControlsCommons.*;
public class FormDeteccion extends FormEvento {

    public static final String TAG = "RANK: FormDeteccion";

    private EditText txtPadrones;
    private EditText txtNroReferencia;
    private EditText txtRegimen;
    private EditText txtAreaTerreno;
    private EditText txtAreaEdificada;
    private EditText txtLocalidad;
    private EditText txtUnidad;
    private EditText txtDireccion;
    private EditText txtEsquina;
    private EditText txtDescripcionCategoria;
    private EditText txtDescripcionEstado;
    private EditText txtAreaAproximadaReforma;
    private EditText txtDescripcionHumedadesPatologias;
    private EditText txtDescripcionGrietasHumedades;
    private EditText txtObservacionesGenerales;

    private Switch swVisitaInmueble;
    private Switch swReformas;
    private Switch swPatologias;
    private Switch swHumedadesPatologias;
    private Switch swGrietasHumedades;
    private Switch swInstSanitaria;
    private Switch swInstElectrica;

    private Spinner spnDepartamento;
    private Spinner spnCategoriaInmueble;
    private Spinner spnEstadoInmueble;

    private LinearLayout lyReformas;
    private LinearLayout lyVisita;
    private LinearLayout lyPatlogia;

    private Button btnAddContruccion;
    private ListView lv_construccion;
    private List<Construccion> construccionList;

    private MyConstruccionAdapter myad;

    private ControlsCommons cc;
    private Activity activity;
    private View view;

    public FormDeteccion(Context applicationContext, Resources resources, View view, Activity activity) {
        super(applicationContext, resources);
        this.activity = activity;
        this.view = view;
        this.cc = new ControlsCommons( view, R.id.class, activity );

        Log.i(TAG, "FormDeteccion: " + view );

        linkControls();
    }

    public void linkControls(){

        construccionList = new ArrayList<>();

        btnAddContruccion = cc.getButton( "btnAddContruccion" );
        lv_construccion = cc.getListView( "lv_construccion" );

        txtPadrones = cc.getEditText( "txtPadrones" );
        txtNroReferencia = cc.getEditText( "txtNroReferencia" );
        txtRegimen = cc.getEditText( "txtRegimen" );
        txtAreaTerreno = cc.getEditText( "txtAreaTerreno" );
        txtAreaEdificada = cc.getEditText( "txtAreaEdificada" );
        txtLocalidad = cc.getEditText( "txtLocalidad" );
        txtUnidad = cc.getEditText( "txtUnidad" );
        txtDireccion = cc.getEditText( "txtDireccion" );
        txtEsquina = cc.getEditText( "txtEsquina" );
        txtDescripcionCategoria = cc.getEditText( "txtDescripcionCategoria" );
        txtDescripcionEstado = cc.getEditText( "txtDescripcionEstado" );
        txtAreaAproximadaReforma = cc.getEditText( "txtAreaAproximadaReforma" );
        txtDescripcionHumedadesPatologias = cc.getEditText( "txtDescripcionHumedadesPatologias" );
        txtDescripcionGrietasHumedades = cc.getEditText( "txtDescripcionGrietasHumedades" );
        txtObservacionesGenerales = cc.getEditText( "txtObservacionesGenerales" );

        swVisitaInmueble = cc.getSwitch( "swVisitaInmueble" );
        swReformas = cc.getSwitch( "swReformas" );
        swPatologias = cc.getSwitch( "swPatologias" );
        swHumedadesPatologias = cc.getSwitch( "swHumedadesPatologias" );
        swGrietasHumedades = cc.getSwitch( "swGrietasHumedades" );
        swInstSanitaria = cc.getSwitch( "swInstSanitaria" );
        swInstElectrica = cc.getSwitch( "swInstElectrica" );

        spnDepartamento = cc.getSpinner("spnDepartamento");
        spnCategoriaInmueble = cc.getSpinner("spnCategoriaInmueble");
        spnEstadoInmueble = cc.getSpinner("spnEstadoInmueble");

        lyReformas = cc.getLinearLayout("lyReformas");
        lyVisita = cc.getLinearLayout("lyVisita");
        lyPatlogia = cc.getLinearLayout("lyPatlogia");

        lyVisita.setVisibility( View.GONE );
        lyReformas.setVisibility( View.GONE );
        lyPatlogia.setVisibility( View.GONE );

        swVisitaInmueble.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked ){
                    lyVisita.setVisibility( View.VISIBLE );
                }else{
                    lyVisita.setVisibility( View.GONE );
                }
            }
        });

        swReformas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked ){
                    lyReformas.setVisibility( View.VISIBLE );
                }else{
                    lyReformas.setVisibility( View.GONE );
                }
            }
        });

        swPatologias.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked ){
                    lyPatlogia.setVisibility( View.VISIBLE );
                }else{
                    lyPatlogia.setVisibility( View.GONE );
                }
            }
        });

        ArrayAdapter<String> ctgs = new ArrayAdapter<String>( applicationContext, R.layout.background_spinner, ConstanteText.CATEGORIAS );
        ArrayAdapter<String> estds = new ArrayAdapter<String>( applicationContext, R.layout.background_spinner, ConstanteText.ESTADOS );
        ArrayAdapter<String> dpts = new ArrayAdapter<>( applicationContext, R.layout.background_spinner, ConstanteText.DEPARTAMENTOS);
        ctgs.setDropDownViewResource(R.layout.spinner_row);
        estds.setDropDownViewResource(R.layout.spinner_row);
        dpts.setDropDownViewResource(R.layout.spinner_row);
        spnCategoriaInmueble.setAdapter( ctgs );
        spnEstadoInmueble.setAdapter( estds );
        spnDepartamento.setAdapter( dpts );

        myad = new MyConstruccionAdapter( applicationContext, R.layout.item_construccion, construccionList, lv_construccion );
        lv_construccion.setAdapter( myad );

        setTxtTituloFormulario( cc.getTextView( "lbl_title_event" ) );
        addListeners();
    }

    public void addListeners(){
        btnAddContruccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogContruccion dc = new DialogContruccion( activity, construccionList, myad );
                dc.show();
            }
        });
    }

    public void setValues(SharedPreferences preferences, int typeProcess){

        String titleTypeForm = "Nuevo ";
        if( typeProcess == ConstanteNumeric.UPDATE ){
            titleTypeForm = "Edición de ";
        }

        txtTituloFormulario.setText(StringUtil.join(titleTypeForm, resources.getString(R.string.name_deteccion_title)));

    }

    public void getEventObject( Padron p, Integer tipoProceso, Integer idUsuario, SharedPreferences sp_event ){

        p.setPadron( _int( txtPadrones ) );
        p.setNroReferencia( _text( txtNroReferencia ) );
        p.setRegimen( _text( txtRegimen ) );
        p.setAreaTerreno( _dbl( txtAreaTerreno ) );
        p.setAreaEdificada( _dbl( txtAreaEdificada ) );
        p.setLocalidad( _text( txtLocalidad ) );
        p.setUnidad( _text( txtUnidad ) );
        p.setDireccion( _text( txtDireccion ) );
        p.setEsquina( _text( txtEsquina ) );
        p.setDescripcionCategoria( _text( txtDescripcionCategoria ) );
        p.setDescripcionEstado( _text( txtDescripcionEstado ) );
        p.setAreaReforma( _text( txtAreaAproximadaReforma ) );
        p.setDescripcionHumedades( _text( txtDescripcionHumedadesPatologias ) );
        p.setDescripcionGrietas( _text( txtDescripcionGrietasHumedades ) );
        p.setObservacion( _text( txtObservacionesGenerales ) );

        p.setVisitaInteriorInmueble( _int( swVisitaInmueble ) );
        p.setReformas( _int( swReformas ) );
        p.setPatologia( _int( swPatologias ) );
        p.setHumedades( _int( swHumedadesPatologias ) );
        p.setGrietas( _int( swGrietasHumedades ) );
        p.setInstElectrica( _int( swInstElectrica ) );
        p.setInstSanitaria( _int( swInstSanitaria ) );

        p.setDepartamento( _int( spnDepartamento ) );
        p.setDepartamentoStr( _text( spnDepartamento ) );

        p.setCategoria( _int( spnCategoriaInmueble ) );
        p.setCategoriaStr( _text( spnCategoriaInmueble ) );

        p.setEstado( _int( spnEstadoInmueble ) );
        p.setEstadoStr( _text( spnEstadoInmueble ) );

        p.setUsuario( new Long(idUsuario) );
        p.setFechaRegistro( new Date().getTime() );
        p.setEstadoRegistro( ConstanteNumeric.ESTADO_ACTIVO );

    }
}
