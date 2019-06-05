package com.uy.csi.sige.dao;

import android.content.Context;
import android.util.Log;

import com.uy.csi.sige.entity.Secuencia;
import com.uy.csi.sige.tools.Propiedades;

import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

public class SecuenciaDaoImpl extends GenericDao<Secuencia> implements  SecuenciaDao{

    private static String TAG = "SecuenciaDaoImpl";

    public SecuenciaDaoImpl(Context context) {
        super(Secuencia.class, context, "Secuencia");
    }

    @Override
    public Secuencia getSecuencia(String tabla) {

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("tabla", tabla, "=");

        List<Secuencia> secuenciaList = buscarPorPropiedades( p );

        if( !isEmpty( secuenciaList ) ){
            return secuenciaList.get(0);
        }

        return null;

    }


    @Override
    public void actualizar(Secuencia secuencia) {

        try {
            Propiedades p = new Propiedades();
            p.agregarPropiedadAnd("id", secuencia.getId(), "=");
            updateRows(secuencia, p);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.e(TAG, "actualizar: ", e);
        }

    }
}
