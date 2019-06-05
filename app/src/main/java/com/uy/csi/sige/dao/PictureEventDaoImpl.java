package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.interfaces.PictureEventDAO;
import com.uy.csi.sige.tools.ConstanteNumeric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */

public class PictureEventDaoImpl implements PictureEventDAO {

    private Context context;

    public PictureEventDaoImpl(Context context){
        this.context=context;
    }


    @Override
    public int save(PictureEvent picture) throws Exception {
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
            valores.put("STATE", ConstanteNumeric.OPEN);//1=no enviado 0=enviado
            valores.put("ID_EVENT",picture.getIdEvent());

            long pos = db.insert(DaoManager.TABLE_PICTURE_EVENT, null, valores);
            System.out.println("REGISTRADO TABLE_PICTURE_EVENT " + pos);
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
    public int delete(Integer id, Integer idevent,Integer state) {

        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        String sentencia = "DELETE FROM "+ DaoManager.TABLE_PICTURE_EVENT+" WHERE 1=1 ";
        try {
            db.beginTransaction();
            if (id != null  ) {
                sentencia += " AND ID_PCTR=" + id ;
            }
            if (idevent != null  ) {
                sentencia += " AND ID_EVENT=" + idevent ;
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
    public List<PictureEvent> loadPictureList(Integer idevent, Integer state) {
        List<PictureEvent> pictureEventList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {
            StringBuilder consulta=new StringBuilder("SELECT ID_PCTR ,NAME ,LATITUD, LONGITUD ,DATE , DESCRIPTION,ID_EVENT,STATE  FROM PICTURE_EVENT WHERE 1 = 1 ");
            if(idevent !=null  && idevent>ConstanteNumeric.NO_EXITE  ){
                consulta.append(" AND ID_EVENT = ").append(idevent);

            }  if(state !=null  && state>ConstanteNumeric.NO_EXITE){
                consulta.append(" AND STATE = ").append(state);

            }
            System.out.println("SQL FOTO event ==>"+consulta.toString());
            Cursor c = db.rawQuery(consulta.toString(), null);


            if (c.moveToFirst()) {
                do {
                    int idFtoe = !c.isNull(0) ? c.getInt(0) : 0;
                    String nombreFtoe = !c.isNull(1) ? c.getString(1) : "";
                    String latitud = !c.isNull(2) ? c.getString(2) : "";
                    String longitud = !c.isNull(3) ? c.getString(3) : "";
                    String fecha = !c.isNull(4) ? c.getString(4) : "";
                    String desc = !c.isNull(5) ? c.getString(5) : " ";
                    int idev = !c.isNull(6) ? c.getInt(6) : ConstanteNumeric.NO_EXITE;
                    int std = !c.isNull(7) ? c.getInt(7) : ConstanteNumeric.OPEN;




                    PictureEvent  pt = new PictureEvent();

                    pt.setIdPctr(idFtoe);
                    pt.setName(nombreFtoe);
                    pt.setLatitud(latitud);
                    pt.setLongitud(longitud);
                    pt.setDate(fecha);
                    pt.setDescription(desc);
                    pt.setIdEvent(idev);
                    pt.setState(std);

                    pictureEventList.add(pt);
                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            db.close();
        }
        return pictureEventList;
    }

    @Override
    public int update(Integer id, Integer state) {
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


            long pos=db.update(DaoManager.TABLE_PICTURE_EVENT, valores, where, params);
            System.out.println("actualizando estado picture sincro" + DaoManager.TABLE_PICTURE_EVENT + "  " + pos);
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

   /* @Override
    public int countPicture(Integer idevent, Integer state ) {
        int count=0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try{
            StringBuilder sql=new StringBuilder("SELECT COUNT(ID_PCTR) FROM PICTURE_EVENT WHERE 1 = 1 ");
            sql.append(" AND ID_EVENT IN (SELECT ID_EVENT FROM EVENT WHERE STATE NOT IN(3) AND TYPE_EVENT = ").append(ConstanteNumeric.TYPE_DETECCION) ;
            sql.append(" OR (TYPE_EVENT=").append(ConstanteNumeric.TYPE_OBRA).append(" AND DATE_FIN IS NOT NULL AND ifnull(length(DATE_FIN), 0) > 0 ) ) ");
            System.out.println("caount picture="+sql.toString());
            Cursor mCount= db.rawQuery(sql.toString(), null);
            mCount.moveToFirst();
            count= mCount.getInt(0);
            mCount.close();
         }catch (Exception e){


        }finally {
            System.out.println("cerrando countEvents=>"+count);
            db.close();
        }

        return count;
    }*/

    @Override
    public int countPictureDeteccion( ) {
        int count=0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try{
            StringBuilder sql=new StringBuilder("SELECT COUNT(ID_PCTR) FROM PICTURE_EVENT WHERE 1 = 1 ");
            sql.append(" AND ID_EVENT IN (SELECT ID_EVENT FROM EVENT WHERE STATE NOT IN(").append(ConstanteNumeric.DATA_SENDING);
            sql.append(") AND TYPE_EVENT = ").append(ConstanteNumeric.TYPE_DETECCION).append(")") ;

            System.out.println("caount picture="+sql.toString());
            Cursor mCount= db.rawQuery(sql.toString(), null);
            mCount.moveToFirst();
            count= mCount.getInt(0);
            mCount.close();
        }catch (Exception e){


        }finally {
            System.out.println("cerrando countEvents deteccion=>"+count);
            db.close();
        }

        return count;
    }
    @Override
    public int countPictureObra( ) {
        int count=0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try{
            StringBuilder sql=new StringBuilder("SELECT COUNT(ID_PCTR) FROM PICTURE_EVENT WHERE 1 = 1 ");
            sql.append(" AND ID_EVENT IN (SELECT ID_EVENT FROM EVENT WHERE STATE NOT IN(").append(ConstanteNumeric.DATA_SENDING);
            sql.append(" ) AND TYPE_EVENT = ").append(ConstanteNumeric.TYPE_OBRA)  ;
            sql.append(" AND DATE_FIN IS NOT NULL AND ifnull(length(DATE_FIN), 0) > 0  ") .append(")") ;

            System.out.println("caount picture obra="+sql.toString());
            Cursor mCount= db.rawQuery(sql.toString(), null);
            mCount.moveToFirst();
            count= mCount.getInt(0);
            mCount.close();
        }catch (Exception e){


        }finally {
            System.out.println("cerrando countEvents obra=>"+count);
            db.close();
        }

        return count;
    }
}
