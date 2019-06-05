package com.uy.csi.sige.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import com.uy.csi.sige.CustomDialogInputDataActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.actions.CancelarRegistroSaneamientoDialogAction;
import com.uy.csi.sige.actions.SaveSaneamientoDialogAction;
import com.uy.csi.sige.adapters.MyHorizontalListViewAdapter;
import com.uy.csi.sige.dao.ConfiguracionDao;
import com.uy.csi.sige.dao.ConfiguracionDaoImpl;
import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.entity.PictureTemporal;
import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.image.LruCacheManager;
import com.uy.csi.sige.interfaces.GPSCallback;
import com.uy.csi.sige.listener.CmrProntaOnCheckedChangeListener;
import com.uy.csi.sige.listener.DescargaIndustrialOnCheckedChangeListener;
import com.uy.csi.sige.listener.DiametroColectorOnSelectListener;
import com.uy.csi.sige.listener.InspeccionSaneamientoOnSelectListener;
import com.uy.csi.sige.listener.SaneamientoOnSelectListener;
import com.uy.csi.sige.listener.TpCnxAlcOnCheckedChangeListener;
import com.uy.csi.sige.services.PictureEventService;
import com.uy.csi.sige.services.PictureTemporalService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.ControlsUtil;
import com.uy.csi.sige.tools.DialogUtil;
import com.uy.csi.sige.tools.GPSManager;
import com.uy.csi.sige.tools.StringUtil;
import com.uy.csi.sige.widget.HorizontalListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FragmentSaneamiento extends FragmentHeaderForm implements GPSCallback {

    public static final String TAG = "FragmentSaneamiento";

    private PictureTemporalService pictureTemporalService;
    private LruCacheManager lruCacheManager;
    private HorizontalListView hlv;
    private Integer iconPictureEmpty;
    private SharedPreferences sp_foto;
    private SharedPreferences sp_usuario;
    private int screenSize;
    private int insesion;

    private ImageButton btnGuardarSaneamiento;
    private ImageButton btnBack;
    private ImageButton btnCamera;

    private Spinner lstSaneamiento;
    private Spinner lstDiametroConexion;
    private Spinner lstMaterialColector;
    private Spinner lstDiametroColector;
    private Spinner lstProfundidadColector;
    private Spinner lstCnxDomIntereferencia;
    private Spinner lstInspSaneamiento;
    private Spinner lstDepartment;
    private Spinner lstCiudad;
    private Spinner lstSector;

    private LinearLayout pnlInpsPrev;
    private LinearLayout pnlInspInstDom;
    private LinearLayout pnlEjecCnxDom;
    private LinearLayout pnlInspSan;
    private LinearLayout pnlOtros;
    private LinearLayout pnlInstPrevSanTipCnx;
    private LinearLayout pnlEspPavAfectCalle;
    private LinearLayout pnlEspPavAfectPav;

    private EditText txtDiametroColectorOtro;
    private EditText txtTipoEfluente;
    private EditText txtInspSaneamiento;
    private EditText txtFecha;
    private EditText txtOtraCiudad;
    private EditText txtCalle;
    private EditText txtNumero;
    private EditText txtReferencia;
    private EditText txtPadron;
    private EditText txtNombreUsuario;
    private EditText txtIdentServicio;
    private EditText txtObservacionPrel;
    private EditText txtULLadoIzquiero;
    private EditText txtULLadoDerecho;
    private EditText txtCVTierraLargo;
    private EditText txtCVTierraAncho;
    private EditText txtCVBaldosaLargo;
    private EditText txtCVBaldosaAncho;
    private EditText txtCVHormigonLargo;
    private EditText txtCVHormigonAncho;
    private EditText txtCVCespedLargo;
    private EditText txtCVCespedAncho;
    private EditText txtCVOtro;
    private EditText txtCVOtroLargo;
    private EditText txtCVOtroAncho;
    private EditText txtCnxDomEspPavVer;
    private EditText txtCPCarpetaLargo;
    private EditText txtCPCarpetaAncho;
    private EditText txtCPRiegoLargo;
    private EditText txtCPRiegoAncho;
    private EditText txtCPHormigonPvmtLargo;
    private EditText txtCPHormigonPvmtAncho;
    private EditText txtCPToscaLargo;
    private EditText txtCPToscaAncho;
    private EditText txtCPOtroPvmt;
    private EditText txtCPOtroPvmtLargo;
    private EditText txtCPOtroPvmtAncho;
    private EditText txtCnxDomEspPav;
    private EditText txtProfMaxSifon;
    private EditText txtUbicacionCamara;
    private EditText txtCnxInstIntSanOtros;
    private EditText txtCnxDomTipoSuelo;
    private EditText txtCnxDomOtros;
    private EditText txtOtros;

    private RadioGroup rdGrpTipoConexion;
    private RadioGroup rdGrpCmrPronta;

    private Switch optDcgIndustrial;
    private Switch optCnxPluvial;
    private Switch optCnxPbmNapa;
    private Switch optDcgXBombeo;

    private ControlsUtil<Configuracion> controlsUtil;
    private ConfiguracionDao confDao;

    private Saneamiento saneamiento;

    private PictureEventService pictureEventService;

    private GPSManager gpsManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.form_saneamiento);

        setComponents( findViewById(R.layout.form_saneamiento) , R.id.class );

        lruCacheManager = LruCacheManager.getInstance();

        sp_foto =  getApplicationContext().getSharedPreferences(ConstanteText.NAME_SP_PICTURE, Context.MODE_PRIVATE);
        sp_usuario = getApplicationContext().getSharedPreferences(ConstanteText.NAME_SP_USER, Context.MODE_PRIVATE);
        screenSize = sp_usuario.getInt(ConstanteText.KEY_SCREEN_SIZE, 4);

        initHeaderForm( getApplicationContext() );
        initForm();
    }

    public void initForm(){

        insesion = ConstanteNumeric.CLOSE;
        saneamiento = new Saneamiento();
        pictureTemporalService=new PictureTemporalService( getApplicationContext() );
        pictureEventService = new PictureEventService( getApplicationContext() );

        iniciarServicios();
        loadBySize();
        linkComponentsToForm();
        loadPicture( ConstanteNumeric.UPDATE, null );

    }

    public void linkComponentsToForm(){

        Bundle b = getIntent().getExtras();
        if (b != null) {
            saneamiento = (Saneamiento) b.getSerializable(ConstanteText.KEY_NAME_EVENT);
        }

        lstSaneamiento = getSpinner( "lstSaneamiento" );
        lstDiametroConexion = getSpinner( "lstDiametroConexion" );
        lstMaterialColector = getSpinner( "lstMaterialColector" );
        lstDiametroColector = getSpinner( "lstDiametroColector" );
        lstProfundidadColector = getSpinner( "lstProfundidadColector" );
        lstCnxDomIntereferencia = getSpinner( "lstCnxDomIntereferencia" );
        lstInspSaneamiento = getSpinner( "lstInspSaneamiento" );

        pnlInpsPrev = getLinearLayout( "pnlInpsPrev" );
        pnlInspInstDom= getLinearLayout( "pnlInspInstDom");
        pnlEjecCnxDom = getLinearLayout( "pnlEjecCnxDom" );
        pnlInspSan = getLinearLayout( "pnlInspSan" );
        pnlOtros = getLinearLayout( "pnlOtros" );
        pnlInstPrevSanTipCnx = getLinearLayout( "pnlInstPrevSanTipCnx" );
        pnlEspPavAfectCalle = getLinearLayout( "pnlEspPavAfectCalle" );
        pnlEspPavAfectPav = getLinearLayout( "pnlEspPavAfectPav" );

        txtDiametroColectorOtro = getEditText( "txtDiametroColectorOtro" );
        txtTipoEfluente = getEditText( "txtTipoEfluente" );
        txtInspSaneamiento = getEditText( "txtInspSaneamiento" );
        txtFecha = getEditText("txtFecha");
        txtOtraCiudad = getEditText("txtOtraCiudad");
        txtCalle = getEditText("txtCalle");
        txtNumero = getEditText("txtNumero");
        txtReferencia = getEditText("txtReferencia");
        txtPadron = getEditText("txtPadron");
        txtNombreUsuario = getEditText("txtNombreUsuario");
        txtIdentServicio = getEditText("txtIdentServicio");
        txtObservacionPrel = getEditText("txtObservacionPrel");
        txtULLadoIzquiero = getEditText("txtULLadoIzquiero");
        txtULLadoDerecho = getEditText("txtULLadoDerecho");
        txtCVTierraLargo = getEditText("txtCVTierraLargo");
        txtCVTierraAncho = getEditText("txtCVTierraAncho");
        txtCVBaldosaLargo = getEditText("txtCVBaldosaLargo");
        txtCVBaldosaAncho = getEditText("txtCVBaldosaAncho");
        txtCVHormigonLargo = getEditText("txtCVHormigonLargo");
        txtCVHormigonAncho = getEditText("txtCVHormigonAncho");
        txtCVCespedLargo = getEditText("txtCVCespedLargo");
        txtCVCespedAncho = getEditText("txtCVCespedAncho");
        txtCVOtro = getEditText("txtCVOtro");
        txtCVOtroLargo = getEditText("txtCVOtroLargo");
        txtCVOtroAncho = getEditText("txtCVOtroAncho");
        txtCnxDomEspPavVer = getEditText("txtCnxDomEspPavVer");
        txtCPCarpetaLargo = getEditText("txtCPCarpetaLargo");
        txtCPCarpetaAncho = getEditText("txtCPCarpetaAncho");
        txtCPRiegoLargo = getEditText("txtCPRiegoLargo");
        txtCPRiegoAncho = getEditText("txtCPRiegoAncho");
        txtCPHormigonPvmtLargo = getEditText("txtCPHormigonPvmtLargo");
        txtCPHormigonPvmtAncho = getEditText("txtCPHormigonPvmtAncho");
        txtCPToscaLargo = getEditText("txtCPToscaLargo");
        txtCPToscaAncho = getEditText("txtCPToscaAncho");
        txtCPOtroPvmt = getEditText("txtCPOtroPvmt");
        txtCPOtroPvmtLargo = getEditText("txtCPOtroPvmtLargo");
        txtCPOtroPvmtAncho = getEditText("txtCPOtroPvmtAncho");
        txtCnxDomEspPav = getEditText("txtCnxDomEspPav");
        txtProfMaxSifon = getEditText("txtProfMaxSifon");
        txtUbicacionCamara = getEditText("txtUbicacionCamara");
        txtCnxInstIntSanOtros = getEditText("txtCnxInstIntSanOtros");
        txtCnxDomTipoSuelo = getEditText("txtCnxDomTipoSuelo");
        txtCnxDomOtros = getEditText("txtCnxDomOtros");
        txtOtros = getEditText("txtOtros");

        lstDepartment = getSpinner("lstDepartment");
        lstCiudad = getSpinner("lstCiudad");
        lstSector = getSpinner("lstSector");

        rdGrpTipoConexion = getRadioGroup( "rdGrpTipoConexion" );
        rdGrpCmrPronta = getRadioGroup( "rdGrpCmrPronta" );

        optDcgIndustrial = getSwitch( "optDcgIndustrial" );
        optCnxPluvial = getSwitch( "optCnxPluvial" );
        optCnxPbmNapa = getSwitch( "optCnxPbmNapa" );
        optDcgXBombeo = getSwitch( "optDcgXBombeo" );

        hlv = getHorizontalListView( "hlv" );

        btnCamera =  getImageButton("btnCamera");
        btnGuardarSaneamiento =  getImageButton("btnGuardarSaneamiento");
        btnBack = getImageButton("btn_back_event");

        iniciarLstSaneamiento();
        iniciarLstDiametroConexion();
        iniciarLstMaterialColector();
        iniciarLstDiametroColector();
        iniciarLstProfundidadColector();
        iniciarLstCnxDomIntereferencia();
        iniciarLstInspSaneamiento();
        iniciarSwitchDescargaIndustrial();
        iniciarRadioGrupoTipoConexionAlcantarillado();
        buttonsListener();

        enlazarSaneamiento();
    }

    public void enlazarSaneamiento(){

        if( !StringUtil.isEmpty(saneamiento.getKeyDep())){
            lstDepartment.setSelection( saneamiento.getKeyDep() );
        }

//        if( !StringUtil.isEmpty(saneamiento.getKeyCiudad())){
//            lstDepartment.setSelection( saneamiento.getKeyCiudad() );
//        }
//
//        if( !StringUtil.isEmpty(saneamiento.getKeySector())){
//            lstDepartment.setSelection( saneamiento.getKeySector() );
//        }

        txtOtraCiudad.setText( saneamiento.getNombreOtroSector() );
        txtCalle.setText( saneamiento.getCalle() );
        txtNumero.setText( saneamiento.getNumero() );
        txtReferencia.setText( saneamiento.getReferencia() );

    }

    public void iniciarServicios(){
        confDao = new ConfiguracionDaoImpl( getApplicationContext() );
        controlsUtil = new ControlsUtil<>( getApplicationContext(), this );
    }

    public void iniciarLstSaneamiento(){
        List<Configuracion> saneamientoLst = confDao.listConfiguracion( ConstanteText.GRUPO_SANEAMIENTO );
        controlsUtil.cargarSpinner( lstSaneamiento, saneamientoLst, Configuracion.generateUnselectOption() );
        lstSaneamiento.setOnItemSelectedListener( new SaneamientoOnSelectListener(
                pnlInpsPrev, pnlInspInstDom, pnlEjecCnxDom, pnlInspSan, pnlOtros, pnlEspPavAfectCalle, pnlEspPavAfectPav ) );
    }

    public void iniciarLstDiametroConexion(){
        List<Configuracion> diametros = confDao.listConfiguracion( ConstanteText.GRUPO_DIAMETRO_CNX );
        controlsUtil.cargarSpinner( lstDiametroConexion, diametros, Configuracion.generateUnselectOption() );
    }

    public void iniciarLstMaterialColector(){
        List<Configuracion> materiales = confDao.listConfiguracion( ConstanteText.GRUPO_MATERIAL_CLCTOR );
        controlsUtil.cargarSpinner( lstMaterialColector, materiales, Configuracion.generateUnselectOption() );
    }

    public void iniciarLstProfundidadColector(){
        List<Configuracion> profundidadesColector = confDao.listConfiguracion( ConstanteText.GRUPO_PROFUNDIDAD_CLCT );
        controlsUtil.cargarSpinner( lstProfundidadColector, profundidadesColector, Configuracion.generateUnselectOption() );
    }

    public void iniciarLstCnxDomIntereferencia(){
        List<Configuracion> interferencias = confDao.listConfiguracion( ConstanteText.GRUPO_INTERFERENCIAS );
        controlsUtil.cargarSpinner( lstCnxDomIntereferencia, interferencias, Configuracion.generateUnselectOption() );
    }

    public void iniciarLstInspSaneamiento(){
        List<Configuracion> inspecciones = confDao.listConfiguracion( ConstanteText.GRUPO_INSP_SANEAMIENTO );
        controlsUtil.cargarSpinner( lstInspSaneamiento, inspecciones, Configuracion.generateUnselectOption() );
        lstInspSaneamiento.setOnItemSelectedListener( new InspeccionSaneamientoOnSelectListener( txtInspSaneamiento ));
    }

    public void iniciarLstDiametroColector(){
        List<Configuracion> diametrosColector = confDao.listConfiguracion( ConstanteText.GRUPO_DIAMETRO_CLCTO );
        controlsUtil.cargarSpinner( lstDiametroColector, diametrosColector, Configuracion.generateUnselectOption() );
        lstDiametroColector.setOnItemSelectedListener( new DiametroColectorOnSelectListener( getApplicationContext(), txtDiametroColectorOtro ) );
    }

    public void iniciarRadioGrupoTipoConexionAlcantarillado(){
        ControlsUtil.visibility( View.GONE, pnlInstPrevSanTipCnx );
        rdGrpTipoConexion.setOnCheckedChangeListener( new TpCnxAlcOnCheckedChangeListener( saneamiento, pnlInstPrevSanTipCnx ));
        rdGrpCmrPronta.setOnCheckedChangeListener( new CmrProntaOnCheckedChangeListener( saneamiento ) );
    }

    public void iniciarSwitchDescargaIndustrial(){
        ControlsUtil.visibility( View.GONE, txtTipoEfluente );
        optDcgIndustrial.setOnCheckedChangeListener( new DescargaIndustrialOnCheckedChangeListener( saneamiento, txtTipoEfluente ));
    }

    public void buttonsListener(){
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        btnGuardarSaneamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerSaneamientoFromForm();

                List<PictureTemporal> temporalList = pictureTemporalService.loadPictureList(null);

                SaveSaneamientoDialogAction saveSaneamientoDialogAction = new SaveSaneamientoDialogAction( getApplicationContext(),
                        saneamiento, temporalList,FragmentSaneamiento.this);

                DialogUtil.showConfirm("¿Desea guardar el registro actual?",
                        ConstanteText.NAME_FRAGMENT_DIALOG,
                        getFragmentManager(),
                        saveSaneamientoDialogAction);

            }
        });

        btnBack.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );
    }

    public void obtenerSaneamientoFromForm(){
        saneamiento.setFecha( getTextValueIn( txtFecha ) );
        saneamiento.setKeyDep( getValueIn( lstDepartment ) );
        saneamiento.setKeyCiudad( getValueIn( lstCiudad ) );
        saneamiento.setKeySector( getValueIn( lstSector ) );
        saneamiento.setNombreOtroSector( getTextValueIn( txtOtraCiudad ) );
        saneamiento.setCalle( getTextValueIn( txtCalle ) );
        saneamiento.setNumero( getTextValueIn( txtNumero ) );
        saneamiento.setReferencia( getTextValueIn( txtReferencia ) );
        saneamiento.setPadron( getTextValueIn( txtPadron ) );
        saneamiento.setUsuarioIdentServ( getTextValueIn( txtNombreUsuario ) );
        saneamiento.setIdentServ( getTextValueIn( txtIdentServicio ) );
        saneamiento.setObservacionPre( getTextValueIn( txtObservacionPrel ) );
        saneamiento.setTipoSaneamiento( getValueIn( lstSaneamiento ) );
        saneamiento.setUbicRespLimPropLizq( getTextValueIn( txtULLadoIzquiero ) );
        saneamiento.setUbicRespLimPropLder( getTextValueIn( txtULLadoDerecho ) );
        saneamiento.setCrtVrdTierrLargo( getDoubleValueIn( txtCVTierraLargo ) );
        saneamiento.setCrtVrdTierrAncho( getDoubleValueIn( txtCVTierraAncho ) );
        saneamiento.setCrtVrdBaldLargo( getDoubleValueIn( txtCVBaldosaLargo ) );
        saneamiento.setCrtVrdBaldAncho( getDoubleValueIn( txtCVBaldosaAncho ) );
        saneamiento.setCrtVrdHormLargo( getDoubleValueIn( txtCVHormigonLargo ) );
        saneamiento.setCrtVrdHormAncho( getDoubleValueIn( txtCVHormigonAncho ) );
        saneamiento.setCrtVrdCspdLargo( getDoubleValueIn( txtCVCespedLargo ) );
        saneamiento.setCrtVrdCspdAncho( getDoubleValueIn( txtCVCespedAncho ) );
        saneamiento.setCrtVrdOtro( getTextValueIn( txtCVOtro ) );
        saneamiento.setCrtVrdOtroLargo( getDoubleValueIn( txtCVOtroLargo ) );
        saneamiento.setCrtVrdOtroAncho( getDoubleValueIn( txtCVOtroAncho ) );
        saneamiento.setEspPavAfcVrd( getIntegerValueIn( txtCnxDomEspPavVer ) );
        saneamiento.setCrtPavCptAncho( getDoubleValueIn( txtCPCarpetaAncho ) );
        saneamiento.setCrtPavCptLargo( getDoubleValueIn( txtCPCarpetaLargo ) );
        saneamiento.setCrtPavRiegoAncho( getDoubleValueIn( txtCPCarpetaAncho ) );
        saneamiento.setCrtPavRiegoLargo( getDoubleValueIn( txtCPCarpetaLargo ) );
        saneamiento.setCrtPavHormAncho( getDoubleValueIn( txtCPHormigonPvmtAncho ) );
        saneamiento.setCrtPavHormLargo( getDoubleValueIn( txtCPHormigonPvmtLargo ) );
        saneamiento.setCrtPavToscaAncho( getDoubleValueIn( txtCPToscaAncho ) );
        saneamiento.setCrtPavToscaLargo( getDoubleValueIn( txtCPToscaLargo ) );
        saneamiento.setCrtPavOtro( getTextValueIn( txtCPOtroPvmt ) );
        saneamiento.setCrtPavOtroAncho( getDoubleValueIn( txtCPOtroPvmtAncho ) );
        saneamiento.setCrtPavOtroLargo( getDoubleValueIn( txtCPOtroPvmtLargo ) );
        saneamiento.setEspPavAfcCalle( getIntegerValueIn( txtCnxDomEspPav ) );
        saneamiento.setProfMaxSldSifon( getDoubleValueIn( txtProfMaxSifon ) );
        saneamiento.setUbicCmr1RefPdn( getTextValueIn( txtUbicacionCamara ) );
        saneamiento.setDiametroCnx( getValueIn( lstDiametroConexion ) );
        saneamiento.setMaterialClctor( getValueIn( lstMaterialColector ) );
        saneamiento.setDiametroClctor( getValueIn( lstDiametroColector ) );
        saneamiento.setDiametroClctorOtro( getDoubleValueIn( txtDiametroColectorOtro ) );
        saneamiento.setProfundidadClctor( getValueIn( lstProfundidadColector ) );
        saneamiento.setTipoSuelo( getTextValueIn( txtCnxDomTipoSuelo ) );
        saneamiento.setInterferencias( getValueIn( lstCnxDomIntereferencia ) );
        saneamiento.setOtrosInsp( getTextValueIn( txtCnxInstIntSanOtros ) );
        saneamiento.setConexionPluvial( getBooleanIn( optCnxPluvial ) );
        saneamiento.setAguaNapa( getBooleanIn( optCnxPbmNapa ) );
        saneamiento.setDescargaBombeo( getBooleanIn( optDcgXBombeo ) );
        saneamiento.setDescargaIndustrial( getBooleanIn( optDcgIndustrial ) );
        saneamiento.setOtroInspInterna( getTextValueIn( txtCnxInstIntSanOtros ) );
        saneamiento.setTipoInspSaneamiento( getValueIn( lstInspSaneamiento ) );
        saneamiento.setTipoInspSaneamientoOtro( getTextValueIn( txtInspSaneamiento ) );
        saneamiento.setOtroTipoSaneamiento( getTextValueIn( txtOtros ) );
        saneamiento.setTipoEfluente( getTextValueIn( txtTipoEfluente ) );

        saneamiento.setEstado( ConstanteNumeric.ESTADO_ACTIVO );
        saneamiento.setTimestampRegistro( new Date().getTime() + "" );

        getSharedPreferences(ConstanteText.NAME_SP_USER, MODE_PRIVATE);
        saneamiento.setIdUsuario( sp_usuario.getInt(ConstanteText.KEY_ID_USER, ConstanteNumeric.NO_EXITE) );
    }

    private void loadPicture(int typeProcess, Integer idEvent) {

        /*
        if (typeProcess == ConstanteNumeric.UPDATE) {
            if (insesion == ConstanteNumeric.CLOSE) {

                List<PictureEvent> pictureEventList = pictureEventService.loadPictureList(idEvent, ConstanteNumeric.OPEN);

                if (pictureEventList != null && !pictureEventList.isEmpty()) {
                    for (PictureEvent pictureEvent : pictureEventList) {
                        try {
                            pictureTemporalService.save(PictureTemporal.toBeanFotoTemporal(pictureEvent));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        }*/

        loadViewPagerPicture();

    }

    private void loadViewPagerPicture() {
        List<PictureTemporal> pictureTemporalList = pictureTemporalService.loadPictureList(ConstanteNumeric.OPEN);

        if (pictureTemporalList == null || pictureTemporalList.isEmpty()) {
            pictureTemporalList = new ArrayList<>();
            PictureTemporal pictureTemporal = new PictureTemporal();
            pictureTemporal.setIdPctr(-1);
            pictureTemporalList.add(pictureTemporal);
        } else {
            Iterator<PictureTemporal> iterator = pictureTemporalList.iterator();
            while (iterator.hasNext()) {
                PictureTemporal fto = (PictureTemporal) iterator.next();
                Bitmap bitmap = Archivo.convertBitmap(Archivo.pathStorage() + File.separator + ConstanteText.IMAGEN_THUMBNAIL + fto.getName());
                lruCacheManager.addBitmapToMemoryCache(fto.getName(), bitmap);
            }
        }


        MyHorizontalListViewAdapter adapter = new MyHorizontalListViewAdapter(getApplicationContext(),
                R.layout.item_image,
                pictureTemporalList,
                lruCacheManager,
                FragmentSaneamiento.this,
                iconPictureEmpty);
        hlv.setAdapter(adapter);


    }

    public void deletePicture(int idpicture) {

        pictureTemporalService.update(idpicture, ConstanteNumeric.CLOSE, null);
        loadViewPagerPicture();

    }

    private void openCamera() {

        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        File dir = Archivo.createFileSystem(ConstanteText.IMAGEN_DIRECTORY);
        String timeStamp = new SimpleDateFormat(ConstanteText.FORMATO_PHOTO).format(new Date());
        String imageFileName = ConstanteText.PREFIJO_FOTO + "_" + timeStamp + ConstanteText.EXTENSION_FOTO;
        File image = new File(dir, imageFileName);
        Uri uriSavedImage = Uri.fromFile(image);
        sp_foto.edit().putString(ConstanteText.KEY_NAME_PICTURE, imageFileName).commit();
        i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(i, ConstanteNumeric.CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: Llamando a la foto con " + ConstanteNumeric.CAMERA );

        if (requestCode == ConstanteNumeric.CAMERA) {

            try {
                File imgFile = new File(Archivo.pathStorage() + File.separator + ConstanteText.IMAGEN_DIRECTORY, sp_foto.getString(ConstanteText.KEY_NAME_PICTURE, ""));

                if (imgFile.exists()) {

                    saveImages(imgFile);

                    int idPicture = saveImage();
                    sp_foto.edit().putInt(ConstanteText.KEY_ID_PICTURE, idPicture).commit();
                    Intent intent = new Intent( this, CustomDialogInputDataActivity.class);
                    intent.putExtra("NAME_CLASS", "fragments.FragmentSaneamiento");
                    intent.putExtra("TYPE_SAVE", ConstanteNumeric.NO_EXITE);
                    intent.putExtra("TYPE_FORM", ConstanteNumeric.TYPE_IMAGE);
                    startActivityForResult(intent, ConstanteNumeric.OK_SAVE_PICTURE);


                } else {
                    System.out.println("no extiste foto");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == ConstanteNumeric.OK_SAVE_PICTURE) {
            loadViewPagerPicture();
        }
    }

    private int saveImage() {
        int idPicture = ConstanteNumeric.NO_EXITE;
        try {


            PictureTemporal temp = new PictureTemporal();
            temp.setName(sp_foto.getString(ConstanteText.KEY_NAME_PICTURE, ""));
            temp.setState(ConstanteNumeric.OPEN);
            idPicture = pictureTemporalService.save(temp);


        } catch (Exception e) {

        } finally {
            System.out.println("id picture save=>" + idPicture);
            return idPicture;
        }


    }

    private void saveImages(final File imgFile) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    File file = new File(Archivo.createFileSystem(ConstanteText.IMAGEN_THUMBNAIL), sp_foto.getString(ConstanteText.KEY_NAME_PICTURE, ""));
                    ExifInterface exifInterface = new ExifInterface(imgFile.getAbsolutePath());
                    Archivo.cropImage(imgFile, sp_usuario, exifInterface, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadBySize() {
        if (screenSize > 0 && screenSize < 6) {
            iconPictureEmpty = R.mipmap.icon_empty_picture;
        } else if (screenSize >= 6 && screenSize < 9) {
            iconPictureEmpty = R.mipmap.icon_empty_picture;
        } else if (screenSize >= 9) {
            iconPictureEmpty = R.mipmap.icon_empty_picture;
        }
    }

    @Override
    public void onGPSUpdate(Location location) {
        saneamiento.setLatitud( location.getLatitude() + "" );
        saneamiento.setLongitud( location.getLongitude() + "" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROY Form record ");
        if (gpsManager != null) {
            gpsManager.stopListening();
            gpsManager.setGPSCallback(null);
            gpsManager = null;

        }
        lruCacheManager.clear();

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume Form event ");
        initGps();

    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause Form event ");

        if (gpsManager != null) {
            gpsManager.stopListening();
            gpsManager = null;
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("onBackPressed Form record ");
        backPress();
    }

    private void initGps() {
        if (gpsManager == null) {
            gpsManager = new GPSManager();
            gpsManager.startListening(this);
            gpsManager.setGPSCallback(this);
        }
    }

    private void backPress() {
        String msj = getResources().getString(R.string.msj_out_form_saneamiento);

        CancelarRegistroSaneamientoDialogAction cancelSaneamientoDialogAction = new CancelarRegistroSaneamientoDialogAction( this);

        DialogUtil.showConfirm( msj,
                ConstanteText.NAME_FRAGMENT_DIALOG,
                getFragmentManager(),
                cancelSaneamientoDialogAction);
    }
}