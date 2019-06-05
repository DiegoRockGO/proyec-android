package com.uy.csi.sige;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uy.csi.sige.services.PictureTemporalService;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class CustomDialogInputDataActivity extends Activity {

    private EditText txtDescripcionFoto;
    private Button btnAceptar;
    private Button btnCancel;
    private SharedPreferences pref_foto;

    private String nameClass;
    private Class<?> classBack;

    private PictureTemporalService pictureTemporalService;


    private int typeSave;
    private int typeForm;//foto u video
    private int idObjct;


    private Integer idEvent;
    private Integer tipoProceso;

    private TextView txtTitle,txtMsg;
    private int typeModal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.custom_dialog_input_data);

        txtDescripcionFoto = (EditText) findViewById(R.id.txt_input_data_dialog);
        txtTitle=(TextView) findViewById(R.id.txt_titulo_input_dialog);
        txtMsg=(TextView) findViewById(R.id.txt_msg_input_dialog);
        btnAceptar = (Button) findViewById(R.id.btn_aceptar_input_data);
        btnCancel = (Button) findViewById(R.id.btn_close_input_data);
        txtTitle.setText(getApplicationContext().getString(R.string.titulo_atencion));
        btnAceptar.setText(getResources().getString(R.string.name_button_yes));
        btnCancel.setText(getResources().getString(R.string.name_button_no));




        Bundle b = getIntent().getExtras();
        if (b != null) {
            nameClass = b.getString("NAME_CLASS", "");
            typeSave = b.getInt("TYPE_SAVE", ConstanteNumeric.NO_EXITE);
            typeForm = b.getInt("TYPE_FORM", ConstanteNumeric.NO_EXITE);
            idObjct = b.getInt("ID_OBJ", ConstanteNumeric.NO_EXITE);
            typeModal=b.getInt("TYPE_MODAL", ConstanteNumeric.ADD);



        }

        initVaribles(typeForm,b);

        try {
            classBack = Class.forName("com.uy.csi.sige." + nameClass);

            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String desc = txtDescripcionFoto.getText() != null ? txtDescripcionFoto.getText().toString() : "";


                    try {

                        if(typeForm == ConstanteNumeric.TYPE_IMAGE){

                            if(typeSave == ConstanteNumeric.NO_EXITE){
                                System.out.println("Actualizando=>" + pref_foto.getInt(ConstanteText.KEY_ID_PICTURE, ConstanteNumeric.NO_EXITE));
                                pictureTemporalService.update(pref_foto.getInt(ConstanteText.KEY_ID_PICTURE, ConstanteNumeric.NO_EXITE), null,desc);

                            }



                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    back();
                }
            });


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back();
                }
            });







        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }

    private void initVaribles(int tipoForm, Bundle bundle){

        if(tipoForm == ConstanteNumeric.TYPE_IMAGE){
            txtMsg.setText(getApplicationContext().getString(R.string.msj_save_picture));
            txtDescripcionFoto.setHint(getApplicationContext().getString(R.string.hint_description_picture));
            pictureTemporalService = new PictureTemporalService(getApplicationContext());
            pref_foto = getSharedPreferences(ConstanteText.NAME_SP_PICTURE, Context.MODE_PRIVATE);

        }

    }

    private void back() {
        Intent intent = new Intent(CustomDialogInputDataActivity.this, classBack);
        setResult(typeForm, intent);
         overridePendingTransition(0, 0);
        CustomDialogInputDataActivity.this.finish();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

}
