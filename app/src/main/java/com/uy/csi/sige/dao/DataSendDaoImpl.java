package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.DataSend;
import com.uy.csi.sige.interfaces.DataSendDAO;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by USUARIO on 21/08/2016.
 */
public class DataSendDaoImpl implements DataSendDAO {

    private Context context;

    public DataSendDaoImpl (Context context){
        this.context=context;
    }

    @Override
    public Integer save(DataSend data) throws SQLException {
        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues valores = new ContentValues();
            valores.put("ID_DATA", data.getIdData());
            valores.put("NOMBRE", data.getNombre());
            valores.put("FECHA", DateFormat.toString( DateFormat.FORMATO_DD_MM_AA_HHMMSS,new Date()));
            valores.put("STATE", ConstanteNumeric.OPEN);
            valores.put("ID_USER",data.getIdUser());


            long pos = db.insert(DaoManager.TABLE_DATA_SEND, null, valores);
            System.out.println("REGISTRADO  TABLE_DATA_SEND " + pos);
            if (pos == -1)
                rows = 0;
            else
                rows = (int) pos;
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            rows = -2;
            e.printStackTrace();
            throw new SQLException("DHR " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return rows;
    }

    @Override
    public Integer delete(Integer id, Integer user) {
        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        StringBuilder sentencia = new StringBuilder("DELETE FROM DATA_SEND WHERE 1=1 ");
        try {
            db.beginTransaction();
            if (id != null) {
                sentencia.append(" AND ID_DATA=" + id);
            }
            if (id != null) {
                sentencia.append(" AND ID_USER=" + user);
            }

            db.execSQL(sentencia.toString());
            rows = 1;
            db.setTransactionSuccessful();

        } catch (Exception e) {
            rows = -1;
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        System.out.println("row de delete::"+rows);
        return rows;
    }

    @Override
    public List<DataSend> listDataSend(Integer user, Integer state) {
        List<DataSend> listaData = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {


            Cursor c;
            StringBuilder sql= new StringBuilder("SELECT ID_DATA,NOMBRE,FECHA,STATE,ID_USER FROM DATA_SEND WHERE 1=1 ");
            if(user!=null  ){
                sql.append(" AND ID_USER = "+user);
            } if(state!=null  ){
                sql.append(" AND STATE = "+state);
            }

            c = db.rawQuery(sql.toString(), null);
            DataSend data;


            if (c.moveToFirst()) {
                do {
                    int iddata = !c.isNull(0) ? c.getInt(0) : -1;
                    String nmbr = !c.isNull(1) ? c.getString(1) : "";
                    String fch = !c.isNull(2) ? c.getString(2) : "";

                    int stt = !c.isNull(3) ? c.getInt(3) : ConstanteNumeric.NO_EXITE;
                    int usr = !c.isNull(4) ? c.getInt(4) : ConstanteNumeric.NO_EXITE;



                    data=new DataSend();
                    data.setIdData(iddata);
                    data.setNombre(nmbr);
                    data.setFecha(fch);

                    data.setState(stt);
                    data.setIdUser(usr);


                    listaData.add(data);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
        } catch (Exception e) {
            listaData = new ArrayList<>();
            e.printStackTrace();
        }finally {

            db.close();
        }

        System.out.println("****retornando Lista"+listaData.size());
        return listaData;
    }

    @Override
    public Integer countDataSend(Integer user,Integer type) {
        int count = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try{

            StringBuilder sql=new StringBuilder("SELECT COUNT(*) FROM DATA_SEND WHERE 1=1 ");

            if(user!=null){
                sql.append(" AND ID_USER = ").append(user);
            }
            if(type!=null && type==ConstanteNumeric.DATA_SENDING){//LISTE TODOS LOS Q NO ESTAN EN LA TABLA PICTURE
                sql.append(" AND NOMBRE NOT IN (SELECT NAME FROM PICTURE_EVENT )");
            }

            Cursor mCount= db.rawQuery(sql.toString(), null);
            mCount.moveToFirst();
              count= mCount.getInt(0);
            mCount.close();

        }catch (Exception e){
           e.printStackTrace();
        }finally {
            db.close();
        }
        return count;
    }

}
