package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.PictureTemporal;
import com.uy.csi.sige.interfaces.PictureTemporalDAO;
import com.uy.csi.sige.tools.ConstanteNumeric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class PictureTemporalDaoImpl implements PictureTemporalDAO {

    private Context context;

    public  PictureTemporalDaoImpl(Context context){
        this.context=context;
    }

    @Override
    public int save(PictureTemporal picture) throws Exception {

        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues valores = new ContentValues();

            valores.put("NAME", picture.getName());
            valores.put("LATITUD", picture.getLatitud());
            valores.put("LONGITUD", picture.getLongitud());
            valores.put("DATE",picture.getDate());
            valores.put("DESCRIPTION", picture.getDescription());
            valores.put("STATE", ConstanteNumeric.OPEN);


            long pos = db.insert(DaoManager.TABLE_PICTURE_TEMPORAL, null, valores);

            if (pos == -1)
                rows = 0;
            else
                rows = (int) pos;
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            rows = -2;
            e.printStackTrace();
            throw new Exception("DHR " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return rows;
    }

    @Override
    public int update(Integer id, Integer state, String description) {
        int rows ;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {

            String where=" 1 = 1";
            List<String> paramsList =new ArrayList<>();
            ContentValues valores = new ContentValues();

            if(id!=null ){
                where+=" AND ID_PCTR = ?";
                paramsList.add(String.valueOf(id));
            }

            String [] params=new String[paramsList.size()];
            params = paramsList.toArray(params);

            if(state!=null  ){
                valores.put("STATE",state);
            }
            if(description!=null  ){
                valores.put("DESCRIPTION",description);
            }

            long pos=db.update(DaoManager.TABLE_PICTURE_TEMPORAL, valores, where, params);
            System.out.println("actualizando " + DaoManager.TABLE_PICTURE_TEMPORAL + "  " + pos);
            if (pos == -1)
                rows = 0;
            else
                rows = (int) pos;
            db.close();
        } catch (Exception e) {
            rows = 0;
            e.printStackTrace();
        }finally {

            db.close();
        }
        return rows;
    }

    @Override
    public int delete(Integer id,Integer state) {
        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        String sentencia = "DELETE FROM "+ DaoManager.TABLE_PICTURE_TEMPORAL+" WHERE 1=1 ";
        try {
            db.beginTransaction();
            if (id != null  ) {
                sentencia += " AND ID_PCTR=" + id ;
            }
            if (state != null  ) {
                sentencia += " AND STATE=" + state ;
            }

            System.err.println("sentencia:"+sentencia);

            db.execSQL(sentencia);
            rows = 1;
            db.setTransactionSuccessful();

        } catch (Exception e) {
            rows = -1;
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return rows;
    }

    @Override
    public List<PictureTemporal> loadPictureList(Integer state) {
        List<PictureTemporal> listaFoto = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {

            StringBuilder consulta=new StringBuilder("SELECT ID_PCTR,NAME,LATITUD ,LONGITUD,DATE,DESCRIPTION,STATE   FROM PICTURE_TEMPORAL WHERE 1=1");

            if(state !=null  ){
                consulta.append(" AND STATE = ").append(state);
            }
            System.out.println("SQL PICTURE TEMPORAL ==>"+consulta.toString());
            Cursor c = db.rawQuery(consulta.toString(), null);


            if (c.moveToFirst()) {
                do {
                    int idFtoe = !c.isNull(0) ? c.getInt(0) : 0;
                    String nombreFtoe = !c.isNull(1) ? c.getString(1) : "";
                    String latitud = !c.isNull(2) ? c.getString(2) : "";
                    String longitud = !c.isNull(3) ? c.getString(3) : "";
                    String fecha = !c.isNull(4) ? c.getString(4) : "";
                    String desc = !c.isNull(5) ? c.getString(5) : " ";
                    int std = !c.isNull(6) ? c.getInt(6) : ConstanteNumeric.OPEN;//close=eliminado

                    PictureTemporal  pt = new PictureTemporal();

                    pt.setIdPctr(idFtoe);
                    pt.setName(nombreFtoe);
                    pt.setLatitud(latitud);
                    pt.setLongitud(longitud);
                    pt.setDate(fecha);
                    pt.setDescription(desc);
                    pt.setState(std);


                    listaFoto.add(pt);
                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
            listaFoto = new ArrayList<>();
            e.printStackTrace();
        }finally {
            db.close();
        }
        return listaFoto;
    }
}
