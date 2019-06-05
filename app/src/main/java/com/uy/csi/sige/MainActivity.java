package com.uy.csi.sige;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mongodb.client.model.Filters;
import com.uy.csi.sige.asyncs.Authentication;
import com.uy.csi.sige.asyncs.ScriptTask;
import com.uy.csi.sige.asyncs.ServicesTask;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.entity.User;
import com.uy.csi.sige.json.ResponseHttpClient;
import com.uy.csi.sige.json.handler.ScriptMovilResponseHandler;
import com.uy.csi.sige.services.EventService;
import com.uy.csi.sige.services.ItemSpinnerService;
import com.uy.csi.sige.services.UserService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConnectionDetector;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogUtil;
import com.uy.csi.sige.tools.Encrypta;
import com.uy.csi.sige.tools.MongoConnection;
import com.uy.csi.sige.tools.RawRead;

import java.util.List;
import static com.uy.csi.sige.tools.StringUtil.*;

public class MainActivity extends BaseActivity {

    private EditText txtUsuario, txtClave;
    private Button btnIngresar;

    private UserService usuarioService;
    private ItemSpinnerService itemSpinnerService;
    private EventService eventService;
    private ConnectionDetector connectionDetector;

    private String usua;
    private String pswd;
    private SharedPreferences spUsuario;

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        configureScreen();
        linkComponents();
        startServices();
        checkSesion();
        onclickButtons();

    }

    private void configureScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void linkComponents(){
        txtUsuario = _getEditText( R.id.txt_usuario );
        txtClave = _getEditText( R.id.txt_clave );
        btnIngresar = _getButton( R.id.btn_ingresar );
    }

    private void startServices(){
        usuarioService = new UserService(getApplicationContext());
        itemSpinnerService = new ItemSpinnerService(getApplicationContext());
        connectionDetector = new ConnectionDetector(getApplicationContext());
        eventService = new EventService(getApplicationContext());

        spUsuario = getSharedPreferences(ConstanteText.NAME_SP_USER, MODE_PRIVATE);

        // Validando scrips de la aplicación
        new ScriptTask( getApplicationContext(),
                ConstanteNumeric.WS_AUTENTICATION,
                this).execute();

        // Validando cambios en la aplicación
        new ServicesTask( this ).execute();

    }

    private void checkSesion() {
        User usuaInSesion = usuarioService.userLoggedIn();
        if ( !isEmpty(usuaInSesion) && usuaInSesion.getId() > ConstanteNumeric.NO_EXITE) {
            loadMainMenu(usuaInSesion);
        }
    }

    private void onclickButtons() {

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usua = toBlank( txtUsuario.getText() );
                pswd = toBlank( txtClave.getText() );

                User user = new User( usua, pswd );

                if ( !(usua.length() > 0 && pswd.length() > 0) ) {
                    DialogUtil.showDialog( getResources().getString(R.string.msj_error_usuario_sin_dato),
                            ConstanteText.NAME_FRAGMENT_DIALOG,
                            getFragmentManager());
                    return;
                }

                if (connectionDetector.isConnected()) {

                    new Authentication(getApplicationContext(),
                            user,
                            ConstanteNumeric.WS_AUTENTICATION,
                            MainActivity.this).execute();
                    return ;
                }

                user = usuarioService.loadUser(usua, Encrypta.encriptar(pswd, Encrypta.ENCRIPTA_SHA1), null, null);
                String message = "";

                if( !isEmpty(user) && user.getId() == ConstanteNumeric.NO_EXITE ){
                    message = getResources().getString(R.string.msj_error_user);
                }

                if( !isEmpty(user) && user.getId() == ConstanteNumeric.SIN_PERMISO){
                    message = "No tiene permisos para acceder por móvil";
                }

                if( isEmpty(message) ){
                    loadMainMenu( user );
                    return;
                }

                DialogUtil.showDialog(message,
                        ConstanteText.NAME_FRAGMENT_DIALOG,
                        getFragmentManager());
            }

        });


    }

    public void loadMainMenu(User usuario) {

        try {
            cleanSharedPreferences();

            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                return;
            }

            Log.i(TAG, "SIGE: loadMainMenu: " + usuario.toString() );

            spUsuario.edit().putString(ConstanteText.KEY_NAME_USER, usuario.getNombre()).commit();
            spUsuario.edit().putString(ConstanteText.KEY_LAST_NAME_USER, usuario.getApellidos()).commit();
            spUsuario.edit().putInt(ConstanteText.KEY_ID_USER, usuario.getId()).commit();
            spUsuario.edit().putString(ConstanteText.KEY_ID_DEVICE, tm.getDeviceId()).commit();
            spUsuario.edit().putInt(ConstanteText.KEY_SCREEN_SIZE, Archivo.getScreenSize(getWindowManager(), spUsuario).intValue()).commit();

            List<ItemSpinner> ubicationList= RawRead.getItemsSpinner(getApplicationContext(), R.raw.ubicacion, ConstanteNumeric.TYPE_LOCATION,itemSpinnerService);
            List<ItemSpinner> deteccionList= RawRead.getItemsSpinner(getApplicationContext(), R.raw.deteccion, ConstanteNumeric.TYPE_DETECCION,itemSpinnerService);
            List<ItemSpinner> obraList= RawRead.getItemsSpinner(getApplicationContext(), R.raw.obra, ConstanteNumeric.TYPE_OBRA,itemSpinnerService);
            List<ItemSpinner> inspectorList= RawRead.getItemsSpinner(getApplicationContext(), R.raw.inspector, ConstanteNumeric.TYPE_INSPECTOR,itemSpinnerService);

            Intent intent = new Intent(MainActivity.this, MenuMainActivity.class);
            intent.putExtra(ConstanteText.KEY_ID_MENU, ConstanteNumeric.OPC_EVENT);

            startActivity(intent);
            overridePendingTransition(0, 0);
            MainActivity.this.finish();

        }catch (Exception e){

        }
    }

    private void cleanSharedPreferences(){
        spUsuario.edit().clear().commit();
    }

    private void loadPrueba(){
        try{
           Event e=new Event();
            List<Event> eventList=eventService.loadEventList(null,null,null,null);
            if(eventList!=null && !eventList.isEmpty()){
                System.out.println("No esta registrando obras");
            }else{
                e.setIdUser(1);
                e.setDate("16/08/2016 15:15");
                e.setOrderService("OS");
                e.setNumberOs("0001");
                e.setNameInspector("Manuel Osorio");
                e.setNameDepartament("Departamento");
                e.setNameCitie("Ciudad");
                e.setNameSector("Sector");
                e.setStree("Arnold Aguirre ");
                e.setNumber("170");
                e.setReferencia("Ovalo higereta cdra 35");
                e.setLocation("Ciudad / Sector / Arnold Aguirre 170 / Ovalo Higereta cdra 35");
                e.setObsPreliminar("Alguna observacion de prueba");
                e.setTypeEvent(ConstanteNumeric.TYPE_OBRA);
                e.setState(ConstanteNumeric.ADD);
                e.setIdWeb(1);
                eventService.save(e);
            }

            User user=new User();
            user.setId(1);
            user.setNombre("Operador");
            user.setApellidos("Operador");
            user.setNickname("operador");
            user.setPassword(Encrypta.encriptar("operador", Encrypta.ENCRIPTA_SHA1));
            user.setEstado(ConstanteNumeric.CLOSE);

            usuarioService.save(user);


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
