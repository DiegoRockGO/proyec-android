package com.uy.csi.sige;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uy.csi.sige.adapters.MyDrawerAdapter;
import com.uy.csi.sige.dao.EventDaoImpl;
import com.uy.csi.sige.dao.SaneamientoDao;
import com.uy.csi.sige.dao.SaneamientoDaoImpl;
import com.uy.csi.sige.entity.DrawerItem;
import com.uy.csi.sige.fragments.FragmentEvents;
import com.uy.csi.sige.fragments.FragmentSaneamiento;
import com.uy.csi.sige.fragments.FragmentSaneamientoList;
import com.uy.csi.sige.fragments.FragmentSynchronization;
import com.uy.csi.sige.interfaces.EventDAO;
import com.uy.csi.sige.services.UserService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConnectionDetector;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.widget.CustomDialogMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denisse on 03/08/2016.
 */
public class MenuMainActivity extends FragmentActivity implements View.OnClickListener {

    private static String TAG = "MenuMainActivity";

    private TextView txtTitulo;
    private ImageButton btnMenu;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mSlideState = false;


    private SharedPreferences sp_usuario;
    private int idusuario;
    private String nameusuario;
    private int screenSize;

    private int idPositionItemDrawer;
    private int lastPosition;
    private int positionItemDrawer;


    private ConnectionDetector connectionDetector;
    private UserService usuarioService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.menu_main);

            sp_usuario = getSharedPreferences(ConstanteText.NAME_SP_USER, MODE_PRIVATE);

            txtTitulo = (TextView) findViewById(R.id.lbl_titulo_menu);

            btnMenu = (ImageButton) findViewById(R.id.btn_menu_context);
            btnMenu.setOnClickListener(this);

            initField();

            connectionDetector = new ConnectionDetector(getApplicationContext());
            usuarioService = new UserService(getApplicationContext());


            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                idPositionItemDrawer = extra.getInt(ConstanteText.KEY_ID_MENU, ConstanteNumeric.OPC_EVENT);
                positionItemDrawer = extra.getInt(ConstanteText.KEY_POSITION_MENU, ConstanteNumeric.OPC_EVENT);
            }

            initNavigationDrawer();
            selectItem(idPositionItemDrawer, positionItemDrawer);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void initField() {
        idusuario = sp_usuario.getInt(ConstanteText.KEY_ID_USER, ConstanteNumeric.NO_EXITE);
        nameusuario = sp_usuario.getString(ConstanteText.KEY_NAME_USER, "") + " " + sp_usuario.getString(ConstanteText.KEY_LAST_NAME_USER, "");
        screenSize = sp_usuario.getInt(ConstanteText.KEY_SCREEN_SIZE, 4);

    }

    public void initNavigationDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_left_drawer);


        View header = getLayoutInflater().inflate(R.layout.header_nav_drawer, null);
        TextView txtNameUsuario = (TextView) header.findViewById(R.id.txt_name_user);
        txtNameUsuario.setText(nameusuario);
        mDrawerList.addHeaderView(header);

        mDrawerList.setAdapter(new MyDrawerAdapter(getApplicationContext(), R.layout.item_drawer, loadListDrawer()));


        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(this, mDrawerLayout, R.mipmap.icon_header_drawer, 0, 0) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mSlideState = false;

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSlideState = true;

            }
        };

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private List<DrawerItem> loadListDrawer() {
        List<DrawerItem> dataList = new ArrayList<>();

        int imgEvent = 0;
        int imgClose = 0;
        int imgSend = 0;
        int imgEliminarData = 0;

        if (screenSize < 0) {
            screenSize = Archivo.getScreenSize(getWindowManager(), sp_usuario).intValue();
        }


        if (screenSize > 0 && screenSize < 6) {

            imgEvent = R.mipmap.icon_registro_menu;
            imgClose = R.mipmap.icon_out;
            imgSend = R.mipmap.icon_portable;
            imgEliminarData = R.mipmap.icon_settings;

        } else if (screenSize >= 6 && screenSize < 9) {

            imgEvent = R.mipmap.icon_registro_menu_600;
            imgClose = R.mipmap.icon_out_600;
            imgSend = R.mipmap.icon_portable_600;
            imgEliminarData = R.mipmap.icon_settings_600;

        }

        int position = 1;
        dataList.add( new DrawerItem(ConstanteNumeric.OPC_EVENT, getApplicationContext().getString(R.string.name_my_events), imgEvent, idPositionItemDrawer == ConstanteNumeric.OPC_EVENT, position++) );
        dataList.add(new DrawerItem(ConstanteNumeric.OPC_SEND, getApplicationContext().getString(R.string.name_send), imgSend, idPositionItemDrawer == ConstanteNumeric.OPC_SEND, position++));
        dataList.add(new DrawerItem(ConstanteNumeric.OPC_CLEAN_DATA_UPLOADED, getApplicationContext().getString(R.string.name_delete_data_uploaded), imgEliminarData, idPositionItemDrawer == ConstanteNumeric.OPC_CLEAN_DATA_UPLOADED, position++));
        // dataList.add(new DrawerItem(ConstanteNumeric.OPC_SANEAMIENTO, getApplicationContext().getString(R.string.name_saneamiento), imgClose, idPositionItemDrawer == ConstanteNumeric.OPC_SANEAMIENTO, position++));
        dataList.add(new DrawerItem(ConstanteNumeric.OPC_CLOSE_SESION, getApplicationContext().getString(R.string.name_close_sesion), imgClose, idPositionItemDrawer == ConstanteNumeric.OPC_CLOSE_SESION, position++));

        return dataList;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try {
                if (position > 0) {
                    DrawerItem lastItem = (DrawerItem) parent.getItemAtPosition(lastPosition);
                    lastItem.setSelected(false);
                    DrawerItem item = (DrawerItem) parent.getItemAtPosition(position);
                    item.setSelected(true);
                    positionItemDrawer = item.getPosition();

                    Log.i(TAG, "onItemClick: " + positionItemDrawer);

                    selectItem((int) id, positionItemDrawer);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void selectItem(int idOptionSelected, int position) {

        try {
            Fragment fragment;
            Bundle args = new Bundle();

            switch (idOptionSelected) {
                case 0:
                    break;
                case ConstanteNumeric.OPC_EVENT:
                    fragment = new FragmentEvents();
                    loadFragment(position, fragment, args, getResources().getString(R.string.name_my_events), idOptionSelected);
                    break;

                case ConstanteNumeric.OPC_SEND:
                    fragment = new FragmentSynchronization();
                    loadFragment(position, fragment, args, getResources().getString(R.string.name_send), idOptionSelected);
                    break;

                case ConstanteNumeric.OPC_CLEAN_DATA_UPLOADED:
                    showMessage(getResources().getString(R.string.titulo_confirmacion), getResources().getString(R.string.msj_eliminar_data_enviada),
                            getResources().getString(R.string.name_button_yes), getResources().getString(R.string.name_button_no), true, true, ConstanteNumeric.DLG_ELIMINAR_DATA_SINCRONIZADA, getApplicationContext(), false, "", ConstanteText.NAME_FRAGMENT_DIALOG);
                    break;

                case ConstanteNumeric.OPC_CLOSE_SESION:
                    showMessage(getResources().getString(R.string.titulo_confirmacion), getResources().getString(R.string.msj_close_sesion),
                            getResources().getString(R.string.name_button_yes), getResources().getString(R.string.name_button_no), true, true, ConstanteNumeric.DLG_CLOSE_SESION, getApplicationContext(), false, "", ConstanteText.NAME_FRAGMENT_DIALOG);
                    break;

                case ConstanteNumeric.OPC_SANEAMIENTO:
                    fragment = new FragmentSaneamientoList();
                    loadFragment(position, fragment, args, getResources().getString(R.string.name_saneamiento), idOptionSelected);
                    break;

                default:
                    fragment = new FragmentEvents();
                    loadFragment(position, fragment, args, getResources().getString(R.string.name_my_events), idOptionSelected);
                    break;
            }


        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    private void loadFragment(int position, Fragment fragment, Bundle args, String titulo, Integer idPosition) throws Exception {


        sp_usuario.edit().putInt(ConstanteText.KEY_POSITION_MENU, position).commit();
        sp_usuario.edit().putInt(ConstanteText.KEY_ID_MENU, idPosition).commit();
        args.putInt("screen_size", screenSize);
        args.putInt("id_usuario", idusuario);


        fragment.setArguments(args);
        txtTitulo.setText(titulo);


        idPositionItemDrawer = idPosition;
        lastPosition = position;


        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);


    }

    private void closeSesion() {
        System.out.println("close sesion");
        clearSharedAndData();
        usuarioService.update(idusuario, ConstanteNumeric.CLOSE);

        Intent intent = new Intent(MenuMainActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        MenuMainActivity.this.finish();
    }

    private void clearSharedAndData() {

        SharedPreferences sp_sesion = getSharedPreferences(ConstanteText.NAME_SP_SESSION, MODE_PRIVATE);
        sp_usuario.edit().clear().commit();
        sp_sesion.edit().clear().commit();


    }

    public void showMessage(String titulo, String msg, String btnPositive, String btnNegative, boolean showPositive, boolean showNegative, final Integer typeMessage, final Context context, boolean showCentral, String btnCentral, String nameFragmentDialog) {
        final CustomDialogMessage dialog = new CustomDialogMessage() {//
            @Override
            public void confirm() {
                dismiss();
                if (typeMessage == ConstanteNumeric.DLG_CLOSE_SESION) {
                    closeSesion();
                } else if (typeMessage == ConstanteNumeric.DLG_ELIMINAR_DATA_SINCRONIZADA) {
                    EventDAO eventDao = new EventDaoImpl(context);
                    eventDao.deleteDataUploaded();

                    SaneamientoDao saneamientoDao = new SaneamientoDaoImpl( context );
                    saneamientoDao.deshabilitarRegistros();

                } else if (typeMessage == ConstanteNumeric.DLG_OUT_APP) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void cancel() {
                if (typeMessage == ConstanteNumeric.DLG_OUT_APP) {
                    dismiss();
                } else {
                    // configuracionService.update(cInternet.getIdCnfg(), cInternet.getCodigo(), ConstanteNumeric.CLOSE);
                    dismiss();
                }

            }

            @Override
            public void central() {

            }
        };
        dialog.setArgs(titulo, msg, btnPositive, btnNegative, showPositive, showNegative, showCentral, btnCentral);
        showDialogFragment(dialog, nameFragmentDialog);
    }

    public void showDialogFragment(DialogFragment newFragment, String nameFragmentDialog) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(nameFragmentDialog);
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(nameFragmentDialog);
        newFragment.show(ft, nameFragmentDialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROY Menu princiapl ");

    }

    @Override
    public void onResume() {
        super.onResume();

        System.gc();
        initField();

        System.out.println("ON resumeee MENU PINCIAPL ");
        selectItem(idPositionItemDrawer, positionItemDrawer);


    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("ON pause MENU PINCIAPL ");


    }

    @Override
    public void onBackPressed() {
        if (mSlideState) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mSlideState = false;
        } else {
            showMessage(getResources().getString(R.string.titulo_atencion), getResources().getString(R.string.msj_out_app),
                    getResources().getString(R.string.name_button_yes), getResources().getString(R.string.name_button_no), true, true, ConstanteNumeric.DLG_OUT_APP, this, false, "", ConstanteText.NAME_FRAGMENT_DIALOG);


        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu_context: {

                if (mSlideState) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {

                    mDrawerLayout.openDrawer(GravityCompat.START);
                }

            }
            break;


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        selectItem(requestCode, lastPosition);
    }
}
