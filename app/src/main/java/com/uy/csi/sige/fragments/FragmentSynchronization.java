package com.uy.csi.sige.fragments;


import android.app.DialogFragment;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uy.csi.sige.R;
import com.uy.csi.sige.asyncs.SendEvents;
import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.DataSend;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.services.DataSendService;
import com.uy.csi.sige.services.EventService;
import com.uy.csi.sige.services.PictureEventService;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.CreateDataBase;
import com.uy.csi.sige.tools.StringUtil;
import com.uy.csi.sige.widget.CustomDialogMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by USUARIO on 18/08/2016.
 */
public class FragmentSynchronization extends Fragment {


    private Integer idusuario;
    private TextView lblMsgSend;
    private TextView lblProgress;
    private TextView lblNumberObra;
    private TextView lblNumberDeteccion;
    private TextView lblNumberImages;
    private ProgressBar progressBarSincro;
    private ImageButton btnSend;
    private ImageButton btnCancel;


    private Integer valueProgress;
    private String msjProgress;
    private boolean disableButton;
    private SharedPreferences sp_progress;
    private Integer numberImageBySending;
    private List<Event> eventListSend;

    private SendEvents taskEnvio = null;

    private Context context;

    private Button btnDescargarDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        idusuario = getArguments().getInt("id_usuario");

        final View view = inflater.inflate(R.layout.fragment_synchronization, container, false);

        lblMsgSend = (TextView) view.findViewById(R.id.lbl_msg_send);
        lblProgress = (TextView) view.findViewById(R.id.lbl_progress_send);
        progressBarSincro = (ProgressBar) view.findViewById(R.id.progress_bar_sincro);
        btnSend = (ImageButton) view.findViewById(R.id.button_send);
        btnCancel = (ImageButton) view.findViewById(R.id.button_cancel);
        lblNumberDeteccion = (TextView) view.findViewById(R.id.number_deteccion);
        lblNumberObra = (TextView) view.findViewById(R.id.number_obra);
        lblNumberImages = (TextView) view.findViewById(R.id.number_images);


        context = view.getContext();

        btnDescargarDB = (Button) view.findViewById(R.id.btnDescargar);

        btnDescargarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDB(v);
            }
        });

        initVariables(view.getContext());

        return view;
    }


    private void initVariables(Context context) {
        sp_progress = context.getSharedPreferences("SP_PROGRESS", context.MODE_PRIVATE);

        valueProgress = sp_progress.getInt("VALUE_PROGRESS_SEND", 0);
        msjProgress = sp_progress.getString("MSSG_PROGRESS_SEND", "");
        if (valueProgress > 0) {
            disableButton = true;
        } else {
            disableButton = false;
        }
        sp_progress.edit().putBoolean("DISABLE_BUTTON", disableButton).commit();
        lblProgress.setText(valueProgress + "%");
        lblMsgSend.setText(msjProgress);
        progressBarSincro.setProgress(valueProgress);
        eventListSend = new ArrayList<>();


        onClickButton(context, disableButton);
        loadNumberElements(context);
    }

    private void loadNumberElements(Context context) {
        EventService eventService = new EventService(context);
        PictureEventService pictureEventService = new PictureEventService(context);
        DataSendService dataSendService = new DataSendService(context);

        List<Event> deteccionList = eventService.loadEventList(idusuario, null, ConstanteNumeric.TYPE_DETECCION, ConstanteNumeric.DATA_SENDING);
        List<Event> obraList = eventService.loadEventList(idusuario, null, ConstanteNumeric.TYPE_OBRA, ConstanteNumeric.DATA_SENDING);
        // List<DataSend> dataList=dataSendService.listDataSend(idusuario,null);
        int numberDataSend = dataSendService.countDataSend(idusuario, ConstanteNumeric.DATA_SENDING);
        int numberDeteccion = pictureEventService.countPictureDeteccion();
        int numberObra = pictureEventService.countPictureObra();
        //pictureEventService.countPicture(null,ConstanteNumeric.DATA_SENDING)
        numberImageBySending = numberDeteccion + numberObra + numberDataSend;

        eventListSend.addAll(deteccionList);
        eventListSend.addAll(obraList);


        System.out.println("Numeros obtenidos=>" + deteccionList.size() + "/" + obraList.size() + "/" + numberImageBySending);

        lblNumberObra.setText(obraList.size() + "");
        lblNumberDeteccion.setText(deteccionList.size() + "");
        lblNumberImages.setText(numberImageBySending + "");


    }

    private void onClickButton(final Context context, boolean disable) {
        System.out.println("Boton habilitadi=>" + disable);
        if (!disable) {
            btnSend.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        // String nameDataBase = Archivo.nameRamdon(ConstanteNumeric.LENGHT_NAME_RANDOM);
                        //  File fileDataBase = Archivo.copyDataBase(nameDataBase,ConstanteText.DATA_BASE_DIRECTORY);
                        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyhhmmss");
                        String name = df.format(new Date());
                        cloneDataBase(name);

                        taskEnvio = new SendEvents(context, lblProgress, progressBarSincro, lblMsgSend, idusuario, numberImageBySending, name);
                        taskEnvio.execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } else {
            showToast(context, context.getResources().getString(R.string.msj_disable_button_send));
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showMessage("Alerta", "¿Desea cancelar el envío actual?",
                        "Cancelar envío", "", true, false,
                        ConstanteNumeric.DLG_CANCEL, false, "", ConstanteText.NAME_FRAGMENT_DIALOG,
                        context);
            }
        });

    }

    private void showToast(Context context, String msj) {
        Toast.makeText(context, msj, Toast.LENGTH_LONG).show();
    }


    public void showMessage(String titulo, String msg, String btnPositive, String btnNegative, boolean showPositive, boolean showNegative, final Integer typeMessage,
                            boolean showCentral, String btnCentral, String nameFragmentModal, final Context context) {
        final CustomDialogMessage dialog = new CustomDialogMessage() {//
            @Override
            public void confirm() {
                dismiss();
                if (typeMessage == ConstanteNumeric.DLG_CANCEL) {

                    System.out.println("Cancelando envio... " + taskEnvio);

                    if (taskEnvio != null) {
                        taskEnvio.cancel(true);
                    } else {
                        taskEnvio = new SendEvents(context, lblProgress, progressBarSincro, lblMsgSend, idusuario, numberImageBySending, eventListSend);
                    }

                    sp_progress.edit().putInt("VALUE_PROGRESS_SEND", 0).commit();
                    sp_progress.edit().putString("MSSG_PROGRESS_SEND", "").commit();
                    sp_progress.edit().putBoolean("DISABLE_BUTTON", false).commit();

                    disableButton = false;
                    valueProgress = sp_progress.getInt("VALUE_PROGRESS_SEND", 0);
                    progressBarSincro.setProgress(valueProgress);

                    lblProgress.setText("");
                    lblMsgSend.setText("");

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
        showDialogFragment(dialog, nameFragmentModal);
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


    public void downloadDB(View v) {
        showToast(context, "Generando Base Datos");

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyhhmmss");
        String name = df.format(new Date());
        cloneDataBase(name);
    }

    private void cloneDataBase(String name) {
        try {
            createFileDB(DaoManager.DATA_BASE_DIRECTORY, name);
            showToast(context, "Base datos generada en " + StringUtil.join(DaoManager.DATA_BASE_DIRECTORY, name));

            EventService ev = new EventService(context);
            ev.updateState(null, ConstanteNumeric.DATA_SENDING);

        } catch (IOException e) {
            showToast(context, "Problema al generar la Base de datos: " + e.getMessage());
        } catch (SQLException e) {
            showToast(context, "Problema al generar la Base de datos: " + e.getMessage());
        } catch (Exception e) {
            showToast(context, "Problema al generar la Base de datos: " + e.getMessage());
        }
    }


    private String createFileDB(String path, String name) throws IOException, Exception {

        File dbFile = new File(Environment.getDataDirectory() + DaoManager.DIRECTORY_DATA_BASE_APP + DaoManager.DATA_BASE);
        System.out.println("FIle " + dbFile.toString());


        File exportDir = new File(Environment.getExternalStorageDirectory(), path);
        System.out.println("FIle " + exportDir.toString());

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        System.out.println("Name Tipo Envio al crear File :" + name);

        File file = new File(exportDir, name);

        file.createNewFile();
        this.copyFile(dbFile, file);
        return name;

    }

    @SuppressWarnings("resource")
    void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

}
