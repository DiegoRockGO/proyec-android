package com.uy.csi.sige;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uy.csi.sige.adapters.MyHorizontalListViewAdapter;
import com.uy.csi.sige.dao.PadronDaoImpl;
import com.uy.csi.sige.dto.FormDeteccion;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.entity.Padron;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.entity.PictureTemporal;
import com.uy.csi.sige.image.LruCacheManager;
import com.uy.csi.sige.interfaces.GPSCallback;
import com.uy.csi.sige.services.EventService;
import com.uy.csi.sige.services.ItemSpinnerService;
import com.uy.csi.sige.services.PictureEventService;
import com.uy.csi.sige.services.PictureTemporalService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConnectionDetector;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DateFormat;
import com.uy.csi.sige.tools.GPSManager;
import com.uy.csi.sige.widget.CustomDialogMessage;
import com.uy.csi.sige.widget.HorizontalListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.uy.csi.sige.tools.ResourceUtil;

/**
 * Created by dtrinidad on 04/08/2016.
 */

public class FormEventActivity extends FragmentActivity implements GPSCallback {

    private ImageButton btnSave, btnBack, btnCamera;
    private TextView lblTitle;

    /* DETECCION */
    private FormDeteccion deteccionForm;
    private ResourceUtil shrPreference;

    /*OBRA*/
    private TextView textViewOs, textViewNumberOs, textVieweDateInit, textViewInspector;
    private TextView textViewDpto, textViewLocation, textViewObsPreliminar;
    private Spinner spinnerObr1, spinnerObr2, spinnerObr3;
    private EditText editTextObr1, editTextObr2, editTextObr3, editTextDateFin, editTextDateInitObra;


    /*general*/
    private EditText editTextObs;
    private HorizontalListView horizontalListView;
    private LinearLayout linearLayoutDeteccion, linearLayoutObra;


    private SharedPreferences sp_usuario;
    private SharedPreferences sp_event;
    private SharedPreferences sp_foto;
    private SharedPreferences sp_sesion;

    private PictureTemporalService pictureTemporalService;
    private ConnectionDetector connectionDetector;
    private PictureEventService pictureEventService;
    private ItemSpinnerService itemSpinnerService;
    private EventService eventService;
    private PadronDaoImpl pDao;

    private int idusuario;
    // private Integer idEvent;
    private Integer tipoProceso;
    private Integer typeForm;
    private int insesion;

    private int screenSize;
    private int positionOptionMenu;
    private int idItemDrawer;

    private Integer iconPictureEmpty;
    private LruCacheManager lruCacheManager;

    private GPSManager gpsManager = null;
    private double latitud = 0;
    private double longitud = 0;

    private Event event;

    private Padron padron;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.form_event);

            pDao = new PadronDaoImpl( this );

            // ESTABLECIENDO VALORES AL FORMULARIO DE DETECCIÓN
            deteccionForm = new FormDeteccion( getApplicationContext(), getResources(), findViewById( R.layout.form_deteccion ), this);

            /*OBRA*/
            linearLayoutObra = (LinearLayout) findViewById(R.id.layout_obra);
            textViewOs = (TextView) findViewById(R.id.text_view_os);
            textViewNumberOs = (TextView) findViewById(R.id.text_view_number_os);
            textVieweDateInit = (TextView) findViewById(R.id.text_view_date);
            textViewInspector = (TextView) findViewById(R.id.text_view_inspector);
            textViewDpto = (TextView) findViewById(R.id.text_view_dpto);
            textViewLocation = (TextView) findViewById(R.id.text_view_snr);
            textViewObsPreliminar = (TextView) findViewById(R.id.text_view_obs_preliminar);
            editTextDateInitObra = (EditText) findViewById(R.id.edit_text_date_init_obra);

            spinnerObr1 = (Spinner) findViewById(R.id.spinner_obr_1);
            spinnerObr2 = (Spinner) findViewById(R.id.spinner_obr_2);
            spinnerObr3 = (Spinner) findViewById(R.id.spinner_obr_3);
            editTextObr1 = (EditText) findViewById(R.id.edit_text_obr_1);
            editTextObr2 = (EditText) findViewById(R.id.edit_text_obr_2);
            editTextObr3 = (EditText) findViewById(R.id.edit_text_obr_3);
            editTextDateFin = (EditText) findViewById(R.id.edit_text_date_close);

            btnCamera = (ImageButton) findViewById(R.id.button_add_picture);
            horizontalListView = (HorizontalListView) findViewById(R.id.hlv);

            btnSave = (ImageButton) findViewById(R.id.button_save_event);
            btnBack = (ImageButton) findViewById(R.id.btn_back_event);


            pictureTemporalService = new PictureTemporalService(getApplicationContext());
            connectionDetector = new ConnectionDetector(getApplicationContext());

            pictureEventService = new PictureEventService(getApplicationContext());
            itemSpinnerService = new ItemSpinnerService(getApplicationContext());
            eventService = new EventService(getApplicationContext());


            tipoProceso = ConstanteNumeric.ADD;
            typeForm = ConstanteNumeric.TYPE_DETECCION;

            Bundle b = getIntent().getExtras();
            if (b != null) {

                event = (Event) b.getSerializable( ConstanteText.KEY_NAME_EVENT );
                padron = new Padron();

                tipoProceso = b.getInt(ConstanteText.KEY_TYPE_PROCESS, ConstanteNumeric.ADD);
                typeForm = b.getInt(ConstanteText.KEY_TYPE_EVENT, ConstanteNumeric.TYPE_DETECCION);

            }

            if (typeForm == ConstanteNumeric.TYPE_OBRA) {
                linearLayoutObra.setVisibility(View.VISIBLE);
            } else {
                linearLayoutObra.setVisibility(View.GONE);

            }
            checkConfigurationGps(tipoProceso);
            lruCacheManager = LruCacheManager.getInstance();
            initVariables(tipoProceso, typeForm);
            loadSharedPreferences(event, tipoProceso);
            loadForm(tipoProceso, typeForm);

            onClickButtons();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void eventEditTextDate(final EditText editText, final Context context) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                SimpleDateFormat dateFormatter = new SimpleDateFormat(DateFormat.FORMATO_DD_MM_AA_HHMMSS);
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(year, monthOfYear, dayOfMonth);
                                editText.setText(dateFormatter.format(newDate.getTime()));
                                // loadSharedCampo(sp_campos, campo, editText);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    private void loadSharedPreferences(Event event, int typeProcess) {
        sp_event.edit().putInt(ConstanteText.KEY_ID_EVENT, event.getIdEvent()).commit();
        if (typeProcess == ConstanteNumeric.UPDATE) {
            sp_event.edit().putString(ConstanteText.KEY_DATE, event.getDate()).commit();
        } else {
            sp_event.edit().putString(ConstanteText.KEY_DATE, DateFormat.toString(DateFormat.FORMATO_DD_MM_AA_HHMMSS, new Date())).commit();
        }

        sp_event.edit().putString(ConstanteText.KEY_DATE_INIT, event.getDateInitObra()).commit();
        sp_event.edit().putString(ConstanteText.KEY_DATE_FIN, event.getDateFin()).commit();
        sp_event.edit().putString(ConstanteText.KEY_LATITUD, event.getLatitud()).commit();
        sp_event.edit().putString(ConstanteText.KEY_LONGITUD, event.getLongitud()).commit();
        sp_event.edit().putInt(ConstanteText.KEY_STATE, event.getState()).commit();
        sp_event.edit().putInt(ConstanteText.KEY_ID_USER, event.getIdUser()).commit();

        sp_event.edit().putInt(ConstanteText.KEY_ID_DEPARTAMENT, event.getIdDepartament()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_DEPARTAMENT, event.getCodDepartament()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_DEPARTAMENT, event.getNameDepartament()).commit();

        sp_event.edit().putInt(ConstanteText.KEY_ID_CITIE, event.getIdCitie()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_CITIE, event.getCodCitie()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_CITIE, event.getNameCitie()).commit();


        sp_event.edit().putInt(ConstanteText.KEY_ID_SECTOR, event.getIdSector()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SECTOR, event.getCodSector()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SECTOR, event.getNameSector()).commit();

        sp_event.edit().putString(ConstanteText.KEY_STREET, event.getStree()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NUMBER, event.getNumber()).commit();
        sp_event.edit().putString(ConstanteText.KEY_REFERENCIA, event.getReferencia()).commit();


        sp_event.edit().putInt(ConstanteText.KEY_ID_INSPECTOR, event.getIdInspector()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_INSPECTOR, event.getCodInspector()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_INSPECTOR, event.getNameInspector()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_INSPECTOR, event.getOtherInspector()).commit();


        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_DETECCION_1, event.getIdSpinnerDtc1()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_DETECCION_1, event.getCodSpinnerDtc1()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_DETECCION_1, event.getNameSpinnerDtc1()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_DETECCION_1, event.getOtherSpinnerDtc1()).commit();

        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_DETECCION_2, event.getIdSpinnerDtc2()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_DETECCION_2, event.getCodSpinnerDtc2()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_DETECCION_2, event.getNameSpinnerDtc2()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_DETECCION_2, event.getOtherSpinnerDtc2()).commit();


        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_DETECCION_3, event.getIdSpinnerDtc3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_DETECCION_3, event.getCodSpinnerDtc3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_DETECCION_3, event.getNameSpinnerDtc3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_DETECCION_3, event.getOtherSpinnerDtc3()).commit();

        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_OBRA_1, event.getIdSpinnerObr1()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_OBRA_1, event.getCodSpinnerObr1()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_OBRA_1, event.getNameSpinnerObr1()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_OBRA_1, event.getOtherSpinnerObr1()).commit();

        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_OBRA_2, event.getIdSpinnerObr2()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_OBRA_2, event.getCodSpinnerObr2()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_OBRA_2, event.getNameSpinnerObr2()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_OBRA_2, event.getOtherSpinnerObr2()).commit();

        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_OBRA_3, event.getIdSpinnerObr3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_OBRA_3, event.getCodSpinnerObr3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_OBRA_3, event.getNameSpinnerObr3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_OBRA_3, event.getOtherSpinnerObr3()).commit();

        sp_event.edit().putString(ConstanteText.KEY_DATE_FIN, event.getDateFin()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OBSERVATION_PRE, event.getObsPreliminar()).commit();
        sp_event.edit().putString(ConstanteText.KEY_ORDER_SERVICE, event.getOrderService()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NUMBER_OS, event.getNumberOs()).commit();
        sp_event.edit().putString(ConstanteText.KEY_LOCATION, event.getLocation()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OBSERVATION, event.getObservation()).commit();

        sp_event.edit().putString(ConstanteText.KEY_OTRA_UBICACION, event.getOtraUbicacion()).commit();


        sp_event.edit().putInt(ConstanteText.KEY_ID_SPINNER_DETECCION_4, event.getIdSpinnerDtc3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_COD_SPINNER_DETECCION_4, event.getCodSpinnerDtc3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_NAME_SPINNER_DETECCION_4, event.getNameSpinnerDtc3()).commit();
        sp_event.edit().putString(ConstanteText.KEY_OTHER_SPINNER_DETECCION_4, event.getOtherSpinnerDet4()).commit();

        shrPreference.putInt(ConstanteText.KEY_ID_SPINNER_DETECCION_4, event.getIdSpinnerDtc4());
        shrPreference.putStr(ConstanteText.KEY_COD_SPINNER_DETECCION_4, event.getCodSpinnerDtc4());
        shrPreference.putStr(ConstanteText.KEY_NAME_SPINNER_DETECCION_4, event.getNameSpinnerDtc4());

        shrPreference.putStr(ConstanteText.TXT_NAME_DETECCION_4, event.getOtherSpinnerDet4());
        shrPreference.putStr(ConstanteText.TXT_NRO_MEDIDOR, event.getMedidor());
    }

    private void loadForm(int typeProcess, int typeForm) {

        loadPicture(typeProcess, event.getIdEvent());

        if (typeForm == ConstanteNumeric.TYPE_DETECCION) {

            deteccionForm.setValues(sp_event, typeProcess);

        } else if (typeForm == ConstanteNumeric.TYPE_OBRA) {
            textViewOs.setText(sp_event.getString(ConstanteText.KEY_ORDER_SERVICE, ""));
            textViewNumberOs.setText(sp_event.getString(ConstanteText.KEY_NUMBER_OS, ""));
            editTextDateFin.setText(sp_event.getString(ConstanteText.KEY_DATE_FIN, ""));
            editTextDateInitObra.setText(sp_event.getString(ConstanteText.KEY_DATE_INIT, ""));
            textVieweDateInit.setText(sp_event.getString(ConstanteText.KEY_DATE, ""));
            textViewInspector.setText(sp_event.getString(ConstanteText.KEY_NAME_INSPECTOR, ""));
            textViewDpto.setText(sp_event.getString(ConstanteText.KEY_NAME_DEPARTAMENT, ""));
            textViewLocation.setText(sp_event.getString(ConstanteText.KEY_LOCATION, ""));
            textViewObsPreliminar.setText(sp_event.getString(ConstanteText.KEY_OBSERVATION_PRE, ""));

            editTextObr1.setText(sp_event.getString(ConstanteText.KEY_OTHER_SPINNER_OBRA_1, ""));
            editTextObr2.setText(sp_event.getString(ConstanteText.KEY_OTHER_SPINNER_OBRA_2, ""));
            editTextObr3.setText(sp_event.getString(ConstanteText.KEY_OTHER_SPINNER_OBRA_3, ""));

//            loadSpinner(itemSpinnerService.loadItemSpinnerList(ConstanteNumeric.OUT_FATHER, ConstanteNumeric.TYPE_OBRA, getResources().getString(R.string.name_type_obra)), spinnerObr1, sp_event.getInt(ConstanteText.KEY_ID_SPINNER_OBRA_1, ConstanteNumeric.NO_EXITE));
            eventOnItemSelectedSpinnerObra();

            eventEditTextDate(editTextDateFin, editTextDateFin.getContext());
            eventEditTextDate(editTextDateInitObra, editTextDateInitObra.getContext());
        }


    }

    private void eventOnItemSelectedSpinnerObra() {

        spinnerObr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerObr2.setVisibility(View.GONE);
                spinnerObr3.setVisibility(View.GONE);
                editTextObr2.setVisibility(View.GONE);
                editTextObr3.setVisibility(View.GONE);
                ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);
                if (item.getIdItem() == ConstanteNumeric.ID_OTHER_TYPE_OBRA_1) {
                    editTextObr1.setVisibility(View.VISIBLE);
                } else {
                    if (item.getIdItem() > 0) {
                        String hint = item.getIdItem() > 0 ? getResources().getString(R.string.name_type_of) + " " + item.getDescription() : "--Seleccione--";
                        List<ItemSpinner> itemList = itemSpinnerService.loadItemSpinnerList(item.getIdItem(), ConstanteNumeric.TYPE_OBRA, hint);
                        if (itemList != null && itemList.size() > 1) {
                            spinnerObr2.setVisibility(View.VISIBLE);
//                           loadSpinner(itemList, spinnerObr2, sp_event.getInt(ConstanteText.KEY_ID_SPINNER_OBRA_2, ConstanteNumeric.NO_EXITE));
                        }
                    }

                    editTextObr1.setVisibility(View.GONE);
                    editTextObr1.setText("");
                    sp_event.edit().putString(ConstanteText.NAME_OTHER + ConstanteText.NAME_SPINNER_OBRA_1, "").commit();
                }

                sp_event.edit().putInt(ConstanteText.NAME_ID + ConstanteText.NAME_SPINNER_OBRA_1, item.getIdItem()).commit();
                sp_event.edit().putString(ConstanteText.NAME_COD + ConstanteText.NAME_SPINNER_OBRA_1, item.getCodigo()).commit();
                sp_event.edit().putString(ConstanteText.NAME_NAME + ConstanteText.NAME_SPINNER_OBRA_1, item.getDescription()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerObr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerObr3.setVisibility(View.GONE);
                editTextObr3.setVisibility(View.GONE);
                ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);
                if (item.getIdItem() == ConstanteNumeric.ID_OTHER_TYPE_OBRA_2 || item.getIdItem() == ConstanteNumeric.ID_OTHER_ELMT_RD_OBRA_2) {
                    editTextObr2.setVisibility(View.VISIBLE);
                } else {
                    if (item.getIdItem() > 0) {
                        String hint = item.getIdItem() > 0 ? getResources().getString(R.string.name_type_of) + " " + item.getDescription() : "--Seleccione--";
                        List<ItemSpinner> itemList = itemSpinnerService.loadItemSpinnerList(item.getIdItem(), ConstanteNumeric.TYPE_OBRA, hint);
                        if (itemList != null && itemList.size() > 1) {
                            spinnerObr3.setVisibility(View.VISIBLE);
//                           loadSpinner(itemList, spinnerObr3, sp_event.getInt(ConstanteText.KEY_ID_SPINNER_OBRA_3, ConstanteNumeric.NO_EXITE));
                        }
                    }

                    editTextObr2.setVisibility(View.GONE);
                    editTextObr2.setText("");
                    sp_event.edit().putString(ConstanteText.NAME_OTHER + ConstanteText.NAME_SPINNER_OBRA_2, "").commit();
                }

                sp_event.edit().putInt(ConstanteText.NAME_ID + ConstanteText.NAME_SPINNER_OBRA_2, item.getIdItem()).commit();
                sp_event.edit().putString(ConstanteText.NAME_COD + ConstanteText.NAME_SPINNER_OBRA_2, item.getCodigo()).commit();
                sp_event.edit().putString(ConstanteText.NAME_NAME + ConstanteText.NAME_SPINNER_OBRA_2, item.getDescription()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerObr3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);
                if (item.getIdItem() == ConstanteNumeric.ID_OTHER_TYPE_OBRA_3) {
                    editTextObr3.setVisibility(View.VISIBLE);
                } else {

                    editTextObr3.setVisibility(View.GONE);
                    editTextObr3.setText("");
                    sp_event.edit().putString(ConstanteText.NAME_OTHER + ConstanteText.NAME_SPINNER_OBRA_3, "").commit();
                }

                sp_event.edit().putInt(ConstanteText.NAME_ID + ConstanteText.NAME_SPINNER_OBRA_3, item.getIdItem()).commit();
                sp_event.edit().putString(ConstanteText.NAME_COD + ConstanteText.NAME_SPINNER_OBRA_3, item.getCodigo()).commit();
                sp_event.edit().putString(ConstanteText.NAME_NAME + ConstanteText.NAME_SPINNER_OBRA_3, item.getDescription()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initVariables(int typeProcess, int typeForm) {
        sp_usuario = getSharedPreferences(ConstanteText.NAME_SP_USER, MODE_PRIVATE);
        sp_event = getSharedPreferences(ConstanteText.NAME_SP_EVENT, MODE_PRIVATE);
        sp_foto = getSharedPreferences(ConstanteText.NAME_SP_PICTURE, MODE_PRIVATE);
        sp_sesion = getSharedPreferences(ConstanteText.NAME_SP_SESSION, MODE_PRIVATE);

        shrPreference = new ResourceUtil(sp_event);

        insesion = sp_sesion.getInt(ConstanteText.KEY_SESION, ConstanteNumeric.CLOSE);
        idusuario = sp_usuario.getInt(ConstanteText.KEY_ID_USER, ConstanteNumeric.NO_EXITE);
        screenSize = sp_usuario.getInt(ConstanteText.KEY_SCREEN_SIZE, 4);
        positionOptionMenu = sp_usuario.getInt(ConstanteText.KEY_POSITION_MENU, ConstanteNumeric.OPC_EVENT);
        idItemDrawer = sp_usuario.getInt(ConstanteText.KEY_ID_MENU, ConstanteNumeric.OPC_EVENT);
        loadBySize();
        if (typeProcess == ConstanteNumeric.ADD) {
            event.setIdEvent(ConstanteNumeric.NO_EXITE);
            event.setTypeEvent(typeForm);
        }

    }

    private String loadTitle(int typeForm, int typeProcess) {
        String title = getResources().getString(R.string.titulo_new_event) + " " + getResources().getString(R.string.name_deteccion_title);
        if (typeProcess == ConstanteNumeric.UPDATE) {
            title = getResources().getString(R.string.titulo_update_event) + " " + (typeForm == ConstanteNumeric.TYPE_OBRA ? getResources().getString(R.string.name_obra_title) : getResources().getString(R.string.name_deteccion_title));
        }

        return title;
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

    private void onClickButtons() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPress();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean savedata = false;
                if (tipoProceso == ConstanteNumeric.ADD) {
                    if (latitud != 0 && longitud != 0) {
                        savedata = true;

                    } else {
                        showMessage(getResources().getString(R.string.titulo_confirmacion), getResources().getString(R.string.msj_save_sin_coordenadas), getResources().getString(R.string.name_button_save_gps), getResources().getString(R.string.name_button_wait), true, true, ConstanteNumeric.DLG_CONFIRM, false, "", ConstanteText.NAME_FRAGMENT_DIALOG, null);

                    }
                } else {
                    savedata = true;
                }


                if (savedata) {
                    showMessage(getResources().getString(R.string.titulo_confirmacion), getResources().getString(R.string.msj_save), getResources().getString(R.string.name_button_yes), getResources().getString(R.string.name_button_no), true, true, ConstanteNumeric.DLG_CONFIRM, false, "", ConstanteText.NAME_FRAGMENT_DIALOG, null);
                }


            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });


    }


    private void loadPicture(int typeProcess, Integer idEvent) {

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

        }

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


        MyHorizontalListViewAdapter adapter = new MyHorizontalListViewAdapter(this, R.layout.item_image, pictureTemporalList, lruCacheManager, FormEventActivity.this, iconPictureEmpty);
        horizontalListView.setAdapter(adapter);
        horizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               /* Intent intent = new Intent(FormRecordActivity.this, ViewPictureActivity.class);
                intent.putExtra("ID_INFO", idinfo);
                intent.putExtra("PROCESO", typeProcess);
                intent.putExtra("POSITION_PICTURE", position);
                intent.putExtra("ID_PICTURE_SELECTED", (int) id);
                intent.putExtra("ID_FIELD", idfield);
                intent.putExtra("ID_RESOURCE", horizontalListView.getId());
                startActivityForResult(intent, ConstanteNumeric.VIEW);
                overridePendingTransition(0, 0);*/


            }
        });


    }

    public void deletePicture(int idpicture) {

        showMessage(getResources().getString(R.string.titulo_confirmacion), getResources().getString(R.string.msj_delete_picture), getResources().getString(R.string.name_button_yes), getResources().getString(R.string.name_button_no), true, true, ConstanteNumeric.DLG_DELETE_PICTURE, false, "", ConstanteText.NAME_FRAGMENT_DIALOG, idpicture);

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

    private int saveImage() {
        int idPicture = ConstanteNumeric.NO_EXITE;
        try {


            PictureTemporal temp = new PictureTemporal();
            temp.setName(sp_foto.getString(ConstanteText.KEY_NAME_PICTURE, ""));
            temp.setLatitud(latitud + "");
            temp.setLongitud(longitud + "");
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

    private void loadMainMenu() {
        Intent intent = new Intent(FormEventActivity.this, MenuMainActivity.class);
        intent.putExtra(ConstanteText.KEY_ID_MENU, idItemDrawer);
        intent.putExtra(ConstanteText.KEY_POSITION_MENU, positionOptionMenu);
        startActivity(intent);
        FormEventActivity.this.finish();

        overridePendingTransition(0, 0);

    }


    private void saveEvento() throws Exception {

        if (tipoProceso == ConstanteNumeric.ADD) {
            loadEventData();
            pDao.save( padron );
        } else {
            loadEventData();
            pDao.update( padron );
        }

        if (event.getIdEvent() > 0) {
            pictureEventService.delete(null, event.getIdEvent(), null);
            List<PictureTemporal> temporalList = pictureTemporalService.loadPictureList(null);
            if (temporalList != null && !temporalList.isEmpty()) {
                for (PictureTemporal temporal : temporalList) {
                    if (temporal.getState() == ConstanteNumeric.OPEN) {
                        pictureEventService.save(PictureTemporal.toBeanFoto(temporal, padron.getId().intValue() ));
                    } else {
                        // File imgFile = new File(Archivo.pathStorage()+ File.separator + ConstanteText.IMAGEN_DIRECTORY , temporal.getName() );
                        Archivo.eliminarArchivo(Archivo.pathStorage() + File.separator + ConstanteText.IMAGEN_DIRECTORY + File.separator + temporal.getName());
                    }
                }
            }

        }

    }

    private void loadEventData() {
        deteccionForm.getEventObject( padron , tipoProceso, idusuario, sp_event);
    }

    public void showMessage(String titulo, String msg, String btnPositive, String btnNegative, boolean showPositive, boolean showNegative, final Integer typeMessage,
                            boolean showCentral, String btnCentral, String nameFragment, final Integer idpicture) {


        final CustomDialogMessage dialog = new CustomDialogMessage() {
            @Override
            public void confirm() {
                if (typeMessage == ConstanteNumeric.DLG_CONFIRM) {
                    try {
                        saveEvento();
                        loadMainMenu();
                    } catch (Exception e) {
                        showToast(e.toString());
                        e.printStackTrace();
                    }

                } else if (typeMessage == ConstanteNumeric.DLG_BACK) {
                    loadMainMenu();

                } else if (typeMessage == ConstanteNumeric.DLG_DELETE_PICTURE) {

                    pictureTemporalService.update(idpicture, ConstanteNumeric.CLOSE, null);

                    loadViewPagerPicture();
                } else if (typeMessage == ConstanteNumeric.DLG_OPEN_SETTINGS) {
                    launchGPSOptions();

                }
                dismiss();
            }

            @Override
            public void cancel() {

                dismiss();
            }

            @Override
            public void central() {
                dismiss();
            }
        };

        dialog.setArgs(titulo, msg, btnPositive, btnNegative, showPositive, showNegative, showCentral, btnCentral);
        showDialogFragment(dialog, nameFragment);
    }


    public void showDialogFragment(DialogFragment newFragment, String nameFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag(nameFragment);
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(nameFragment);
        newFragment.show(ft, nameFragment);
    }

    private void backPress() {
        String msj = getResources().getString(R.string.msj_out_form_event_new);
        if (tipoProceso == ConstanteNumeric.UPDATE) {
            msj = getResources().getString(R.string.msj_out_form_event_update);
        }

        showMessage(getResources().getString(R.string.titulo_atencion), msj,
                getResources().getString(R.string.name_button_yes), getResources().getString(R.string.name_button_no), true, true, ConstanteNumeric.DLG_BACK, false, "", ConstanteText.NAME_FRAGMENT_DIALOG, null);

    }

    private void checkConfigurationGps(int tipoProceso) {


        if (tipoProceso == ConstanteNumeric.ADD) {

            boolean isEnabled = isGPSenabled();

            if (!isEnabled) {
                showMessage(getResources().getString(R.string.titulo_atencion), getResources().getString(R.string.msj_sin_gps),
                        getResources().getString(R.string.name_button_aceptar), "", true, false, ConstanteNumeric.DLG_OPEN_SETTINGS, false, "", ConstanteText.NAME_FRAGMENT_DIALOG, null);

            }


        }

    }

    private void showToast(String msj) {
        Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_SHORT).show();
    }

    private void launchGPSOptions() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, ConstanteNumeric.GPS);
    }

    private boolean isGPSenabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        }//else if(manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
        //   return true;
        //}
        return false;


    }

    private void initGps() {
        //if (proceso == ConstanteNumeric.ADD) {
            if (gpsManager == null) {


                gpsManager = new GPSManager();
                gpsManager.startListening(this);
                gpsManager.setGPSCallback(this);
            }

        //}

    }

    @Override
    public void onGPSUpdate(Location location) {

//        if (tipoProceso == ConstanteNumeric.ADD || typeForm == ConstanteNumeric.TYPE_OBRA) {

            sp_event.edit().putString(ConstanteText.KEY_LATITUD, location.getLatitude() + "").commit();
            sp_event.edit().putString(ConstanteText.KEY_LONGITUD, location.getLongitude() + "").commit();

            latitud = location.getLatitude();
            longitud = location.getLongitude();

//        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstanteNumeric.CAMERA) {

            try {
                File imgFile = new File(Archivo.pathStorage() + File.separator + ConstanteText.IMAGEN_DIRECTORY, sp_foto.getString(ConstanteText.KEY_NAME_PICTURE, ""));

                if (imgFile.exists()) {


                    saveImages(imgFile);

                    int idPicture = saveImage();
                    sp_foto.edit().putInt(ConstanteText.KEY_ID_PICTURE, idPicture).commit();
                    Intent intent = new Intent(FormEventActivity.this, CustomDialogInputDataActivity.class);
                    intent.putExtra("NAME_CLASS", "FormEventActivity");
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

        }/*else if(requestCode == ConstanteNumeric.VIEW){
            HorizontalListView horizontalListView=  (HorizontalListView) findViewById(resultCode);
            List<PictureTemporal> pictureTemporalList=pictureTemporalService.loadListPicture(idInfo, sp_foto.getInt("ID_FIELD", ConstanteNumeric.NO_EXITE),ConstanteNumeric.OPEN);
            loadViewPagerPicture(pictureTemporalList,horizontalListView,idInfo,tipoProceso, sp_foto.getInt("ID_FIELD", ConstanteNumeric.NO_EXITE));
         } */
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
        //initGps(tipoProceso);
        initGps();

    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause Form event ");
        sp_sesion.edit().putInt(ConstanteText.KEY_SESION, ConstanteNumeric.OPEN).commit();

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


}
