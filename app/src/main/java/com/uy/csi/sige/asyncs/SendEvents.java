package com.uy.csi.sige.asyncs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.uy.csi.sige.NotificationActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.dao.SaneamientoDao;
import com.uy.csi.sige.dao.SaneamientoDaoImpl;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.uy.csi.sige.tools.JSONCommons.*;

/**
 * Created by dtrinidad on 18/08/2016.
 */
public class SendEvents extends AsyncTask<String, String, String> {

    private static final String TAG = "SendEvents";

    private TextView lblProgress;
    private TextView lblMssgProgress;
    private ProgressBar progressBar;

    private Handler progressHandler;
    private Context context;
    private SharedPreferences sp_progress;
    private Integer valueProgress;
    private String msjProgress;
    private Integer idusuario;

    private EventService eventService;
    private SaneamientoDao saneamientoDao;
    private PictureEventService pictureEventService;
    private DataSendService dataSendService;

    private Integer numberPictureBySending;
    private Integer numberPictureSendig;
    private List<Event> eventList;
    private String messageSend;

    private String databaseName;

    public SendEvents(Context context, TextView lblProgress, ProgressBar progressBar,TextView lblMssgProgress, Integer idusuario,Integer numberPictureBySending, String databaseName){

        this.databaseName = databaseName;
        this.lblProgress = lblProgress;
        this.lblMssgProgress = lblMssgProgress;
        this.progressBar = progressBar;
        this.context = context;
        this.idusuario = idusuario;
        this.numberPictureBySending=numberPictureBySending;

        initServices(context);
        initVariable();
        startUpdateProgress();

    }

    public SendEvents(Context context, TextView lblProgress, ProgressBar progressBar,TextView lblMssgProgress, Integer idusuario,Integer numberPictureBySending,List<Event> eventList){
        this.lblProgress = lblProgress;
        this.lblMssgProgress = lblMssgProgress;
        this.progressBar = progressBar;
         this.context = context;
        this.idusuario = idusuario;
        this.numberPictureBySending=numberPictureBySending;
        this.eventList=eventList;

        initServices(context);
        initVariable();
        startUpdateProgress();

    }

    private void initServices(Context context){
        eventService=new EventService(context);
        pictureEventService=new PictureEventService(context);
        dataSendService=new DataSendService(context);
        saneamientoDao = new SaneamientoDaoImpl( context );

        sp_progress = context.getSharedPreferences(ConstanteText.NAME_SP_PROGRESS, context.MODE_PRIVATE);
        sp_progress.edit().putBoolean("DISABLE_BUTTON", true).commit();

    }

    private void initVariable(){
        valueProgress = 1;
        msjProgress = context.getResources().getString(R.string.msj_init_envio);
        numberPictureSendig=0;
    }

    @Override
    protected void onPreExecute() {
        updateProgress(valueProgress, msjProgress);

    }

    public void updateProgress(int value, String msj) {

        valueProgress = value;
        msjProgress = msj;
        if (progressHandler != null && !progressHandler.hasMessages(0)) {
            progressHandler.sendEmptyMessage(0);
        }

    }

    private void startUpdateProgress() {
        progressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                lblProgress.setText(valueProgress + "%");
                progressBar.setProgress(valueProgress);

                    sp_progress.edit().putInt("VALUE_PROGRESS_SEND", valueProgress).commit();
                    sp_progress.edit().putString("MSSG_PROGRESS_SEND", msjProgress).commit();


                lblMssgProgress.setText(msjProgress);

            }
        };
    }


    private String loadMessage(int result){
        String mssg="Error al enviar Base de datos";
        if (result >0 ) {
            mssg= context.getResources().getString(R.string.msj_send_ok);

        }else if (result == ConstanteNumeric.EXCEPTION_SERVIDOR_OFF) {
            mssg=   context.getResources().getString(R.string.msj_server_off);
        }else if (result == ConstanteNumeric.EXCEPTION_JSON_LOCAL) {
            mssg= context.getResources().getString(R.string.msj_error_json_local);
        } else if (result == ConstanteNumeric.EXCEPTION_TIME_OUT) {
            mssg= context.getResources().getString(R.string.msj_conection_time_out);
        }else if (result == ConstanteNumeric.EXCEPTION_RETRY) {
            mssg=  context.getResources().getString(R.string.msj_retry);
        }

        return mssg;
    }


    @Override
    protected String doInBackground(String... params) {
        //String messageProcess = null;
        try {


               updateProgress(10, "");

               String nameDataBase = Archivo.nameRamdon(ConstanteNumeric.LENGHT_NAME_RANDOM);
               File fileDataBase = Archivo.createFile(ConstanteText.DATA_BASE_DIRECTORY,nameDataBase);
              // messageProcess = sendProcess(fileDataBase, nameDataBase);
               messageSend = sendProcess(fileDataBase, nameDataBase);




        } catch (IOException e) {
            e.printStackTrace();
            messageSend = "Error al crear archivo comprimido de datos";

        } catch (Exception e) {
            e.printStackTrace();
            messageSend = "Error !! ";
        }

        return messageSend;
    }

    @Override
    protected void onPostExecute(final String message) {

        if (message != null && message.length() > 0) {
            Intent intent = new Intent(context, NotificationActivity.class);
            intent.putExtra("title", context.getResources().getString(R.string.titulo_confirmacion));
            intent.putExtra("name_positive", context.getResources().getString(R.string.name_button_aceptar));
            intent.putExtra("name_negative", "");
            intent.putExtra("show_positive_button", true);
            intent.putExtra("show_negative_button", false);



            intent.putExtra("message", message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {
            System.out.println("******************name zip on Postexecute vacio");
            // showMessage(getResources().getString(R.string.titulo_error),"", getResources().getString(R.string.name_button_aceptar),"",true,false, ConstanteNumeric.CONFIRM,null,null);


        }


        sp_progress.edit().putInt("VALUE_PROGRESS_SEND", 0).commit();
        sp_progress.edit().putString("MSSG_PROGRESS_SEND", "").commit();
        sp_progress.edit().putBoolean("DISABLE_BUTTON", false).commit();


        // Eliminar eventos
        Archivo.eliminarArchivo(Archivo.pathStorage() + File.separator + ConstanteText.DATA_BASE_DIRECTORY);


    }


    private String sendProcess(File fileDataBase, String nameDataBase) {
        //String messageProcess = context.getResources().getString(R.string.msj_send_ok);
        messageSend = context.getResources().getString(R.string.msj_send_ok);
        try {

            updateProgress(10, context.getResources().getString(R.string.msj_send_check_data));
            updateProgress(20, context.getResources().getString(R.string.msj_load_data));


            updateProgress(30, context.getResources().getString(R.string.msj_sending_data));

            if( StringUtil.isEmpty(databaseName) ){

                CreateDataBase createDataBase = new CreateDataBase(context,eventList);
                int resultCreate = createDataBase.create(fileDataBase.getAbsolutePath());
                System.out.println("Result create dataBase=>" + resultCreate);
                if (resultCreate > 0) {

                    List<DataSend> dataSendList = dataSendService.listDataSend(idusuario, null);
                    System.out.println("Data List=>" + dataSendList.size());
                    sendDataBase(fileDataBase, nameDataBase, dataSendList);

                }else{
                    messageSend = "Ocurrio un error al generar data base";
                }

            }else{
                File file = new File( Environment.getExternalStorageDirectory(), StringUtil.join( DaoManager.DATA_BASE_DIRECTORY, databaseName) );
                sendDataBase(file, databaseName, null);
            }



        } catch (SQLException e) {
            messageSend = "Ocurrio un error al generar data base ";

        } catch (Exception e) {
            System.out.println("Exception=>" + e.toString());
            messageSend = e.getMessage();
        }
        System.out.println("Mensage sendProcess=>" + messageSend);

        return messageSend;
    }

    private void sendDataBase(File fileBD, String nameDataBase, final List<DataSend> dataSendList) throws Exception {
        try {

            Log.i(TAG, "sendDataBase: " + fileBD.getAbsolutePath()+ " - " + fileBD.exists() );

            InputStream fileSend = new FileInputStream(fileBD);


            AsyncHttpClient client = new SyncHttpClient();
            client.setConnectTimeout(25*1000);

            String url = ConstanteText.URL_REST + "/evento/loadDataBase/";

            RequestParams params = new RequestParams();
            params.put("file", fileSend, nameDataBase);
            params.setForceMultipartEntityContentType(true);


            client.put(context, url, params, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    Integer result = ConstanteNumeric.EXCEPTION_SERVIDOR_OFF;
                    try {
                        if (responseBody != null && responseBody.length > 0) {
                            JSONArray jsonArray = toJsonArray( responseBody );

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                result = getInt(object, "result");
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onSuccess: ", e);
                    }

                     processFiles(result, dataSendList);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("******************onFailure sendDataBase*******");
                     processFiles(ConstanteNumeric.EXCEPTION_SERVIDOR_OFF, null);

                }

                @Override
                public void onStart() {
                    System.out.println("******************onStart sendDataBase*******");
                }


                @Override
                public void onRetry(int retryNo) {
                    System.out.println("******************onRetry sendDataBase*******");
                    processFiles(ConstanteNumeric.EXCEPTION_RETRY, null);
                }


            });


        } catch (Exception e) {
            throw new Exception("Ocurrio un error al enviar la base de datos", e);
        }


    }

    private String processFiles(int send_data_base, List<DataSend> dataSendList ) {
      //  String messageProcess = "Error al enviar Base de datos";
        messageSend = loadMessage(send_data_base);
        if (send_data_base > 0) {
            System.out.println("Enviando imagenes y videos");

            eventService.updateState(null,ConstanteNumeric.DATA_SENDING); // actualizando los eventos enviados
            saneamientoDao.updateEstados();

            updateProgress(50, context.getResources().getString(R.string.msj_sending_data));

            if (dataSendList != null && !dataSendList.isEmpty()) {
                int totalNumberPicture = dataSendList.size();

                Log.i(TAG, "IMGENES SENDING IN LIST: " + dataSendList.isEmpty());

                Double progressInit = valueProgress.doubleValue();
                Double progressByItem = Archivo.calculatePorcentajeByItem((100 - progressInit), dataSendList.size());

                int numbersItemsPicture = 0;

                for (DataSend pSend : dataSendList) {
                  progressInit = Archivo.calculateProgress(progressInit, progressByItem);
                  numbersItemsPicture++;
                  updateProgress(progressInit.intValue(), (context.getResources().getString(R.string.msj_sending) + " " + numbersItemsPicture + " " + context.getResources().getString(R.string.msj_compress_picture) + " " + totalNumberPicture));
                  File file = new File(Archivo.pathStorage() + File.separator + ConstanteText.IMAGEN_DIRECTORY + pSend.getNombre());
                  sendImage(file, pSend, ConstanteText.URL_REST_SAVE_IMAGE);
                }
            }else{
                Log.i(TAG, "IMGENES SENDING: " + Archivo.pathStorage() + ConstanteText.IMAGEN_DIRECTORY.replace("//","/"));
                File file = new File(Archivo.pathStorage() + ConstanteText.IMAGEN_DIRECTORY.replace("//","/"));
                File[] images = file.listFiles();

                for(File f: images){
                    Log.i(TAG, "processFiles: SENDING IMAGE - " + f.getName());
                    DataSend ds = new DataSend();
                    ds.setNombre(f.getName());
                    sendImage(f, ds, ConstanteText.URL_REST_SAVE_IMAGE);
                }
            }

            if(numberPictureBySending == numberPictureSendig){
                messageSend = context.getResources().getString(R.string.msj_send_ok);
            }else{
                messageSend = numberPictureSendig+" "+context.getResources().getString(R.string.msj_images_not_send);
            }


        }
        updateProgress(100, messageSend);
        return messageSend;
    }


    private void sendImage(final File file, final DataSend data, String nameRest) {

        try {

            InputStream fileSend = new FileInputStream(file);
            System.out.println(data.getNombre());


            AsyncHttpClient client = new SyncHttpClient();

            String url = ConstanteText.URL_REST + nameRest.trim();

            RequestParams params = new RequestParams();
            params.put("file", fileSend, data.getNombre());
            params.setForceMultipartEntityContentType(true);


            client.put(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    System.out.println("******onSuccess sendFile=>"  );

                    Integer result = ConstanteNumeric.EXCEPTION_SERVIDOR_OFF;
                    try {
                        if (responseBody != null && responseBody.length > 0) {
                            JSONArray jsonArray = toJsonArray( responseBody );

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                result = getInt(object, "result");
                            }


                        }
                        if (result > 0) {
                            //dataSendService.delete(data.getIdData(), data.getId());
                           Archivo.eliminarArchivo(file.getAbsolutePath());
                            //pictureEventService.update(data.getIdData(),ConstanteNumeric.CLOSE);
                            numberPictureSendig++;

                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onSuccess: ", e);
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("******onFailure sendFile=>" + statusCode + "/" + responseBody + "/" + error);


                }

                @Override
                public void onStart() {
                    System.out.println("******************onStart sendFile*******");
                }


                @Override
                public void onRetry(int retryNo) {
                    System.out.println("******************onRetry sendFile*******");

                }
            });


        } catch (Exception e) {
            System.out.println("******************eXCEPTION sendImage 2*******");
        }

    }
}
