package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.Construccion;
import com.uy.csi.sige.tools.DbTools;

import java.util.ArrayList;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.isEmpty;
import static com.uy.csi.sige.tools.StringUtil.toStr;

public class ConstruccionDaoImpl extends DbTools {

    private Context context;

    public ConstruccionDaoImpl(Context context){
        this.context=context;
    }

    public List<Construccion> list( Long idPadron ) throws Exception{

        List<Construccion> pList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        try {

            StringBuffer sb = new StringBuffer( DaoManager.QUERY_CONSTRUCCION );
            sb.append( " WHERE id_padron=" ).append( idPadron );

            Cursor c = db.rawQuery( sb.toString(), null);
            if (c.moveToFirst()) {
                do{
                    pList.add( toConstruccion( c ) );
                }while( c.moveToNext() );
            }
            c.close();
        }catch( Exception e ){
            throw e;
        } finally {
            db.close();
        }

        return pList;
    }

    public void save(Construccion c) throws Exception {

        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues valores = toContentValues( c );

            Long id = db.insert( Construccion.TABLA, null, valores );
            c.setId( id );

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            throw e;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void update( Construccion p ) throws Exception {

        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        try {
            db.update( Construccion.TABLA, toContentValues( p ), "id=?", new String[]{ toStr( p.getId() ) } );
            db.close();
        } catch (Exception e) {
            throw e;
        }finally {
            db.close();
        }

    }

    private Construccion toConstruccion( Cursor c ){

        Construccion p = new Construccion();

        p.setId( _long( c, 0 ) );
        p.setIdPadron( _long( c, 1 ) );
        p.setDestino( _str( c, 2 ) );
        p.setNivel( _int( c, 3 ) );
        p.setHabitaciones( _int( c, 4 ) );
        p.setServicios( _str( c, 5 ) );
        p.setCategoria( _int( c, 6 ) );
        p.setCategoriaStr( _str( c, 7 ) );
        p.setEstado( _int( c, 8 ) );
        p.setEstadoStr( _str( c, 9 ) );
        p.setAreaEdificada( _double( c, 10 ) );
        p.setAnio( _int( c, 11 ) );
        p.setCubierta( _str( c, 12 ) );
        p.setEstadoRegistro( _long( c, 13 ) );
        p.setFechaRegistro( _long( c, 14 ) );
        p.setFechaModificacion( _long( c, 15 ) );

        return p;

    }

    private ContentValues toContentValues( Construccion p ){

        ContentValues cv = new ContentValues();

        if( isEmpty( p.getId() ) ){
            cv.put("id", p.getId());
        }

        cv.put("id_padron", p.getIdPadron() );
        cv.put("destino", p.getDestino() );
        cv.put("nivel", p.getNivel() );
        cv.put("habitaciones", p.getHabitaciones() );
        cv.put("servicios", p.getServicios() );
        cv.put("categoria", p.getCategoria() );
        cv.put("categoria_str", p.getCategoriaStr() );
        cv.put("estado", p.getEstado() );
        cv.put("estado_str", p.getEstadoStr() );
        cv.put("area_edif", p.getAreaEdificada() );
        cv.put("anio", p.getAnio() );
        cv.put("cubierta", p.getCubierta() );
        cv.put("estado_registro", p.getEstadoRegistro() );
        cv.put("fecha_registro", p.getFechaRegistro() );
        cv.put("fecha_modificacion", p.getFechaModificacion() );

        return cv;

    }
}
