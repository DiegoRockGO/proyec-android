package com.uy.csi.sige.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.db.DatabaseHelper;
import com.uy.csi.sige.entity.DataSend;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.services.DataSendService;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dtrinidad on 22/08/2016.
 */
public class CreateDataBase implements Serializable {

    private static final String TAG = "CreateDataBase";

    private List<Event> eventList;

    private DataSendService dataSendService;

    public CreateDataBase(Context context, List<Event> eventList ){

        this.eventList=eventList;

        dataSendService=new DataSendService(context);

    }

    public int cloneDataBase(String ruta, String dbNameCopy, Context context) throws SQLException, IOException {
        DatabaseHelper dh = new DatabaseHelper(context, ruta, DaoManager.DATA_BASE);
        dh.createDataBase( dbNameCopy );
        return 1;
    }

    public int create(String ruta)throws SQLException {
        int result=1;
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(ruta,null);
        try{

            List<String> dropList=tableListDrop();
            List<String> createList=tableListCreate();
            for (String tabla : dropList) {
                //  stat.executeUpdate("drop table if exists " + tabla + ";");
                db.execSQL("drop table if exists " + tabla + ";");
            }
            for (String tabla : createList) {
                // stat.executeUpdate(tabla);
                db.execSQL(tabla);
            }



            loadTableEvents(eventList, db);


            db.close();


        }  catch (SQLException e) {
            result=-2;
            System.out.println("creando tablassss error");
            throw new SQLException("DHR " + e.getMessage());
            //return -2;
        }finally {
            db.close();
        }

        return result;


    }


    private List<String> tableListDrop(){
        List<String> list=new ArrayList<>();

        list.add(DaoManager.TABLE_EVENT);
        list.add(DaoManager.TABLE_PICTURE_EVENT);




        return list;
    }

    private List<String> tableListCreate(){

        List<String> list=new ArrayList<>();
        list.add(DaoManager.SQL_EVENT);
        list.add(DaoManager.SQL_PICTURE_EVENT);



        return list;
    }




    public int loadTableEvents(List<Event> lista,SQLiteDatabase db) throws SQLException {
        int rows = 0;

        try {
            if(lista!=null && !lista.isEmpty()){

                for (Event ev : lista) {

                    db.execSQL( Event.createInsertSQLQuery(ev) );
                    rows++;

                    if( !StringUtil.isEmpty(ev.getPictureEventList())){
                        loadTablePicture(ev.getPictureEventList(), db,ev.getIdUser());
                    }

                }

            }


        } catch (android.database.SQLException e) {
            rows = -2;
            e.printStackTrace();
            db.close();
            throw new SQLException("DHR " + e.getMessage());

        }
        return rows;
    }

   public int loadTablePicture(List<PictureEvent> lista,SQLiteDatabase db,Integer idUser) throws SQLException {


        int rows = 0;

        try {
            if(lista!=null && !lista.isEmpty()) {
                for (PictureEvent picture : lista) {

                    StringBuilder sql = new StringBuilder("INSERT INTO PICTURE_EVENT (");
                    sql.append(" ID_PCTR,NAME,LATITUD,LONGITUD,DATE,DESCRIPTION,ID_EVENT ,STATE  ) VALUES( ")


                            .append(picture.getIdPctr() + ",")

                            .append((picture.getName().length() > 0 ? "\"" + picture.getName() + "\"" : "\"\"") + ",")
                            .append((picture.getLatitud().length() > 0 ? "\"" + picture.getLatitud() + "\"" : "\"\"") + ",")
                            .append((picture.getLongitud().length() > 0 ? "\"" + picture.getLongitud() + "\"" : "\"\"") + ",")
                            .append((picture.getDate().length() > 0 ? "\"" + picture.getDate() + "\"" : "\"\"") + ",")
                            .append((picture.getDescription().length() > 0 ? "\"" + picture.getDescription() + "\"" : "\"\"") + ",")
                           // .append((picture.getDescription().length() > 0 ? "\"" + Archivo.replaceCharacterSpecial(f.getDescription()) + "\"" : "\"\"") + ",")
                            .append(picture.getIdEvent() + ",")
                            .append(picture.getState() + ")");


                    db.execSQL(sql.toString());
                    rows++;
                      dataSendService.save(DataSend.toBean(new DataSend(idUser), picture));

                }

            }


        } catch (android.database.SQLException e) {
            rows = -2;
            e.printStackTrace();
            db.close();
            System.out.println("aquiiii!!!=)");
            throw new SQLException("DHR " + e.getMessage());

        }
        return rows;
    }


}
