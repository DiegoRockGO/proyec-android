package com.uy.csi.sige.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.uy.csi.sige.MenuMainActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.adapters.MySanemianentoAdapter;
import com.uy.csi.sige.dao.SaneamientoDao;
import com.uy.csi.sige.dao.SaneamientoDaoImpl;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.services.EventService;
import com.uy.csi.sige.services.PictureEventService;
import com.uy.csi.sige.services.PictureTemporalService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.widget.CustomDialogMessage;
import com.uy.csi.sige.widget.CustomProgress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.isEmpty;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class FragmentSaneamientoList extends Fragment {

    public static final String TAG = "FgmntSnmntoLst";

    private ListView listView;
    private TextView txtEmpty;
    private int screenSize;

    private EventService eventService;
    private SaneamientoDao saneamientoDao;
    private MenuMainActivity menuMainActivity;

    private int idusuario;

    private PictureTemporalService pictureTemporalService;
    private PictureEventService pictureEventService;

    private List<Saneamiento> saneamientoList;

    private ImageButton btnNewEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {


        screenSize = getArguments().getInt("screen_size");
        idusuario = getArguments().getInt("id_usuario");


        menuMainActivity=(MenuMainActivity) getActivity();

        final View view = inflater.inflate(R.layout.form_saneamiento_list, container, false);

        txtEmpty = (TextView) view.findViewById(R.id.lbl_empty_list_event);
        listView = (ListView) view.findViewById(R.id.lw_events);
        btnNewEvent = (ImageButton) view.findViewById(R.id.btnNewSaneamiento);

        saneamientoDao = new SaneamientoDaoImpl( view.getContext() );
        eventService=new EventService(view.getContext());
        pictureEventService=new PictureEventService(view.getContext());
        pictureTemporalService=new PictureTemporalService(view.getContext());

        loadTable(view.getContext(), txtEmpty);

        btnNewEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            if( isEmpty( saneamientoList ) ){
                loadFormEvent( view.getContext() , ConstanteNumeric.ADD, ConstanteNumeric.TYPE_DETECCION, new Saneamiento());
            }else{
                loadFormEvent( view.getContext() , ConstanteNumeric.ADD, ConstanteNumeric.TYPE_DETECCION, saneamientoList.get(0) );
            }


            }
        });

        return view;
    }

    public void initEvent(Event source, Event target){

        target.setCodDepartament(source.getCodDepartament());
        target.setCodCitie(source.getCodCitie());
        target.setCodSector(source.getCodSector());

        target.setIdDepartament(source.getIdDepartament());
        target.setIdCitie(source.getIdCitie());
        target.setIdSector(source.getIdSector());

        target.setNameDepartament(source.getNameDepartament());
        target.setNameCitie(source.getNameCitie());
        target.setNameSector(source.getNameSector());

        target.setOtraUbicacion(source.getOtraUbicacion());
        target.setStree(source.getStree());
        target.setNumber(source.getNumber());
        target.setReferencia(source.getReferencia());

    }

    public void loadTable(Context context,TextView view) {
        try {

            //saneamientoList=eventService.loadEventList(idusuario,null,null,null);
            saneamientoList = saneamientoDao.list();
            if( isEmpty(saneamientoList) ){
                saneamientoList = new ArrayList<>();
            }

            Log.i(TAG, "loadTable: " + saneamientoList.size() );
            
            listView.setAdapter(new MySanemianentoAdapter(context, R.layout.item_event, saneamientoList,listView, FragmentSaneamientoList.this));

            if (saneamientoList == null || saneamientoList.isEmpty()) {
                view.setVisibility(View.VISIBLE);
            }else{
                view.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cleanData(Context context){
        SharedPreferences sp_event = context.getSharedPreferences(ConstanteText.NAME_SP_EVENT,context.MODE_PRIVATE);
        SharedPreferences sp_foto = context.getSharedPreferences(ConstanteText.NAME_SP_PICTURE,context.MODE_PRIVATE);
        SharedPreferences sp_sesion = context.getSharedPreferences(ConstanteText.NAME_SP_SESSION,context.MODE_PRIVATE);

        sp_event.edit().clear().commit();
        sp_foto.edit().clear().commit();
        sp_sesion.edit().clear().commit();

        pictureTemporalService.delete(null,null);

    }

    public void loadFormEvent(Context context,Integer accion,Integer typeForm,Saneamiento saneamiento){
        cleanData(context);

        new LoadFormSaneamiento(context,accion,typeForm,saneamiento).execute();

//        FragmentManager frgManager = getFragmentManager();
//        frgManager.beginTransaction().replace(R.id.content_frame, new FragmentSaneamiento()).commit();
    }

//    public void showMessage(String titulo,String msg,String btnPositive,String btnNegative,boolean showPositive,boolean showNegative, final Integer typeMessage,final Integer idevent,final Context context,boolean showCentral,String btnCentral,String nameDialog){
//        final CustomDialogMessage dialog = new CustomDialogMessage() {
//            @Override
//            public void confirm() {
//                if(typeMessage== ConstanteNumeric.DLG_CONFIRM){
//
//                    List<PictureEvent> pictureEventList=pictureEventService.loadPictureList(idevent,null);
//                    if(pictureEventList!=null && !pictureEventList.isEmpty()){
//                        for(PictureEvent pictureField:pictureEventList){
//                            Archivo.eliminarArchivo(Archivo.pathStorage() + File.separator + ConstanteText.IMAGEN_DIRECTORY + pictureField.getName());
//                        }
//                    }
//                    eventService.delete(idevent,null);
//                    loadTable(context, txtEmpty);
//
//                }
//                dismiss();
//            }
//
//            @Override
//            public void cancel() {
//                dismiss();
//            }
//
//            @Override
//            public void central() {
//
//            }
//        };
//        dialog.setArgs(titulo, msg, btnPositive, btnNegative, showPositive, showNegative, showCentral, btnCentral);
//        showDialogFragment(dialog,nameDialog);
//    }

    public void showDialogFragment(DialogFragment newFragment,String nameDialog) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(nameDialog);
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(nameDialog);
        newFragment.show(ft, nameDialog);
    }

    class LoadFormSaneamiento extends AsyncTask<String, String, String>{
        private Context context;
        private Integer accion;
        private Integer typeForm;
        private Saneamiento saneamiento;
        private CustomProgress progressDialog;
        private ProgressDialog dialog;

        public LoadFormSaneamiento(Context context,Integer accion,Integer typeForm,Saneamiento saneamiento){
            this.context=context;
            this.accion=accion;
            this.typeForm=typeForm;
            this.saneamiento=saneamiento;

        }

        @Override
        protected void onPreExecute() {

            progressDialog = new CustomProgress();
            progressDialog.setArgs("Cargando formulario ...", 0, 0, 0, true);
            showDialogFragment(progressDialog, ConstanteText.NAME_FRAGMENT_PROGRESS);
            System.out.println("Mostrando progreso");

            /*dialog = new ProgressDialog(context);
            dialog.setMessage("Loading....");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {
            try {


                Intent i = new Intent(context, FragmentSaneamiento.class);
                i.putExtra(ConstanteText.KEY_TYPE_PROCESS, accion);

                i.putExtra(ConstanteText.KEY_NAME_EVENT,saneamiento);
                i.putExtra(ConstanteText.KEY_TYPE_EVENT, typeForm);
                startActivity(i);

            } catch (Exception e) {
                e.printStackTrace();

                return ("Error : " + e.getMessage());
            }
            return "";
        }


        @Override
        protected void onPostExecute(final String errMsg) {
            System.out.println("progressDialog=>"+progressDialog);
            progressDialog.dismiss();
            //dialog.dismiss();

        }
    }

}
