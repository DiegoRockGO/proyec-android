package com.uy.csi.sige;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uy.csi.sige.tools.ConstanteNumeric;

/**
 * Created by USUARIO on 18/08/2016.
 */
public class NotificationActivity extends Activity {

    private String title;
    private String message ;
    private String namePositive ;
    private String nameNegative ;
    private Boolean showPositiveButton ;
    private Boolean showNegativeButton ;
    private Integer idItemDrawerSelected;
    private SharedPreferences sp_usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.custom_dialog_message);

        TextView txtTitulo= (TextView) findViewById(R.id.txt_titulo_dialog);
        TextView txtMsj= (TextView) findViewById(R.id.txt_msj_dialog);
        Button buttonPositive= (Button) findViewById(R.id.btn_aceptar_pop_up);
        Button buttonNegative= (Button) findViewById(R.id.btn_close_pop_up);

        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.ly_mssg_dialog);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linLayoutParam);
        linearLayout.setPadding(0, 10, 0, 10);
        txtTitulo.setPadding(10, 10, 0, 5);
        txtMsj.setPadding(10,10,0,10);

        sp_usuario=getSharedPreferences("SP_USUARIO",MODE_PRIVATE);
        idItemDrawerSelected=sp_usuario.getInt("ID_MENU", ConstanteNumeric.OPC_EVENT);


        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            title = extra.getString("title",getResources().getString(R.string.titulo_atencion));
            message = extra.getString("message", getResources().getString(R.string.titulo_atencion));
            namePositive = extra.getString("name_positive", getResources().getString(R.string.titulo_atencion));
            nameNegative = extra.getString("name_negative", getResources().getString(R.string.titulo_atencion));
            showPositiveButton = extra.getBoolean("show_positive_button", true);
            showNegativeButton = extra.getBoolean("show_negative_button", false);
        }

        System.out.println("********************ITEM SELECTD NOTIFICACION=>"+idItemDrawerSelected);
        txtTitulo.setText(title);
        txtMsj.setText(message);
        buttonPositive.setText(namePositive);
        buttonNegative.setText(nameNegative);

        if(showPositiveButton){
            buttonPositive.setVisibility(View.VISIBLE);
            buttonPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(idItemDrawerSelected == ConstanteNumeric.OPC_EVENT){
                        /*Para agregar*/
                        // Intent intent = new Intent( NotificationActivity.this,MenuPrincipalActivity.class);
                        //   intent.putExtra("ID_MENU", ConstanteNumeric.OPC_PROYECTS);
                        //  intent.putExtra("POSITION_MENU",idItemDrawerSelected);
                        //   startActivity(intent);
                        //   overridePendingTransition(0, 0);

                    }

                    NotificationActivity.this.finish();
                    finish();

                }
            });
        }else{
            buttonPositive.setVisibility(View.GONE);
        }

        if(showNegativeButton){
            buttonNegative.setVisibility(View.VISIBLE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });
        }else {
            buttonNegative.setVisibility(View.GONE);
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
