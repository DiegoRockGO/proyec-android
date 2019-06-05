package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.Padron;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DbTools;

import java.util.ArrayList;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

public class PadronDaoImpl extends DbTools {

    private Context context;

    public PadronDaoImpl(Context context){
        this.context=context;
    }

    public List<Padron> list( ) throws Exception{

        List<Padron> pList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();

        try {
            Cursor c = db.rawQuery( DaoManager.QUERY_PADRON , null);
            if (c.moveToFirst()) {
                do{
                    pList.add( toPadron( c ) );
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

    public void save(Padron p) throws Exception {

        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues valores = toContentValues( p );

            Long id = db.insert( Padron.TABLA, null, valores );
            p.setId( id );

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            throw e;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void update( Padron p ) throws Exception {

        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        try {
            db.update( Padron.TABLA, toContentValues( p ), "id=?", new String[]{ toStr( p.getId() ) } );
            db.close();
        } catch (Exception e) {
            throw e;
        }finally {
            db.close();
        }

    }

    public void delete( Integer idPadron, String where ){

    }

    private Padron toPadron( Cursor c ){

        Padron p = new Padron();

        p.setId( _long( c, 0 ) );
        p.setPadron( _int( c, 1 ) );
        p.setNroReferencia( _str( c, 2 ) );
        p.setRegimen( _str( c, 3 ) );
        p.setAreaTerreno( _double( c, 4 ) );
        p.setDepartamento( _int( c, 5 ) );
        p.setDepartamentoStr( _str( c, 6 ) );
        p.setAreaEdificada( _double( c, 7 ) );
        p.setLocalidad( _str( c, 8 ) );
        p.setUnidad( _str( c, 9 ) );
        p.setDireccion( _str( c, 10 ) );
        p.setEsquina( _str( c, 11 ) );
        p.setVisitaInteriorInmueble( _int( c, 12 ) );
        p.setCategoria( _int( c, 13 ) );
        p.setCategoriaStr( _str( c, 14 ) );
        p.setDescripcionCategoria( _str( c, 15 ) );
        p.setEstado( _int( c, 16 ) );
        p.setEstadoStr( _str( c, 17 ) );
        p.setDescripcionEstado( _str( c, 18 ) );
        p.setReformas( _int( c, 19 ) );
        p.setAreaReforma( _str( c, 20 ) );
        p.setPatologia( _int( c, 21 ) );
        p.setHumedades( _int( c, 22 ) );
        p.setDescripcionHumedades( _str( c, 23 ) );
        p.setGrietas( _int( c, 24 ) );
        p.setDescripcionGrietas( _str( c, 25 ) );
        p.setInstSanitaria( _int( c, 26 ) );
        p.setInstElectrica( _int( c, 27 ) );
        p.setObservacion( _str( c, 28 ) );
        p.setUsuario( _long( c, 29 ) );
        p.setUsuarioStr( _str( c, 30 ) );
        p.setFechaRegistro( _long( c, 31 ) );
        p.setFechaModificacion( _long( c, 32 ) );
        p.setEstadoRegistro( _int( c, 33 ) );

        return p;

    }

    private ContentValues toContentValues( Padron p ){

        ContentValues cv = new ContentValues();

        if( isEmpty( p.getId() ) ){
            cv.put("id", p.getId());
        }

        cv.put("padron", p.getPadron() );
        cv.put("nroReferencia", p.getNroReferencia() );
        cv.put("regimen", p.getRegimen() );
        cv.put("area_terreno", p.getAreaTerreno() );
        cv.put("departamento", p.getDepartamento() );
        cv.put("departamento_str", p.getDepartamentoStr() );
        cv.put("area_edificada", p.getAreaEdificada() );
        cv.put("localidad", p.getLocalidad() );
        cv.put("unidad", p.getUnidad() );
        cv.put("direccion", p.getUnidad() );
        cv.put("esquina", p.getEsquina() );
        cv.put("vis_int_inm", p.getVisitaInteriorInmueble() );
        cv.put("categoria", p.getCategoria() );
        cv.put("categoria_str", p.getCategoriaStr() );
        cv.put("desc_categoria", p.getDescripcionCategoria() );
        cv.put("estado", p.getEstado() );
        cv.put("estado_str", p.getEstadoStr() );
        cv.put("desc_estado", p.getDescripcionEstado() );
        cv.put("reformas", p.getReformas() );
        cv.put("area_reforma", p.getAreaReforma() );
        cv.put("patologia", p.getPatologia() );
        cv.put("humedades", p.getHumedades() );
        cv.put("desc_humedades", p.getDescripcionHumedades() );
        cv.put("grietas", p.getGrietas() );
        cv.put("desc_grietas", p.getDescripcionGrietas() );
        cv.put("inst_sanitaria", p.getInstSanitaria() );
        cv.put("inst_electrica", p.getInstElectrica() );
        cv.put("observacion", p.getObservacion() );
        cv.put("usuario", p.getUsuario() );
        cv.put("usuario_str", p.getUsuarioStr() );
        cv.put("fecha_registro", p.getFechaRegistro() );
        cv.put("fecha_modificacion", p.getFechaModificacion() );
        cv.put("estado_registro", p.getEstadoRegistro() );

        return cv;

    }

}
