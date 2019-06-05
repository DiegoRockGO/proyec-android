package com.uy.csi.sige.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.entity.Secuencia;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.Propiedades;
import com.uy.csi.sige.tools.StringUtil;

import java.util.List;

import static com.uy.csi.sige.tools.ConstanteText.*;

public class SaneamientoDaoImpl extends GenericDao<Saneamiento> implements SaneamientoDao{

    private static String TAG = "SaneamientoDaoImpl";

    private SecuenciaDao seqDao;

    public SaneamientoDaoImpl( Context context ){
        super( Saneamiento.class, context, "Saneamiento" );
        seqDao = new SecuenciaDaoImpl( context );
    }

    @Override
    public List<Saneamiento> list(){
        Propiedades propiedades = new Propiedades();
        return buscarPorPropiedades( propiedades );
    }

    @Override
    public Saneamiento insertar( Saneamiento saneamiento ){
        Secuencia seq = seqDao.getSecuencia( TABLA_SANEAMIENTO );
        seq.setVal( seq.getVal() + 1 );
        saneamiento.setId( seq.getVal() );

        try {
            insert( saneamiento );
            seqDao.actualizar( seq );
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.e(TAG, "insertar: ", e);
        }

        return saneamiento;
    }

    @Override
    public void updateEstados(){

        Saneamiento saneamiento = new Saneamiento();
        saneamiento.setEstado( ConstanteNumeric.ESTADO_ENVIADO );

        Propiedades p = new Propiedades();

        try {
            updateRows( saneamiento, p );
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "updateEstados: ", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "updateEstados: ", e);
        }

    }

    @Override
    public void deshabilitarRegistros(){

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("estado", ConstanteNumeric.ESTADO_ENVIADO, "=");
        List<Saneamiento> sanList = buscarPorPropiedades( p );

        if( !StringUtil.isEmpty(sanList) ){

            for( Saneamiento s : sanList ){
                execSQL( String.format("DELETE FROM PICTURE_EVENT WHERE ID_EVENT=%d", s.getId()) );
                execSQL( String.format("DELETE FROM SANEAMIENTO WHERE ID=%d", s.getId()));
            }

        }

    }

}
