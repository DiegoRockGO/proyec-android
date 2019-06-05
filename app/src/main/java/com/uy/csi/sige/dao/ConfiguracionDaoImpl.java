package com.uy.csi.sige.dao;

import android.content.Context;
import android.util.Log;

import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.tools.Propiedades;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

public class ConfiguracionDaoImpl extends GenericDao<Configuracion> implements ConfiguracionDao {

    private static String TAG = "ConfiguracionDaoImpl";

    public ConfiguracionDaoImpl( Context context){
        super(Configuracion.class, context, "Configuracion");
    }

    @Override
    public Configuracion getConfiguracion(Integer idConfiguracion){

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("id", idConfiguracion, "=");

        List<Configuracion> cfgs = buscarPorPropiedades(p);
        if( !isEmpty(cfgs) ){
            return cfgs.get(0);
        }

        return null;
    }

    @Override
    public void actualizar(Configuracion configuracion){

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("id", configuracion.getId(), "=");

        try {
            updateRows(configuracion, p);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "actualizar: ", e);
        }
    }

    @Override
    public void insertar(Configuracion configuracion){
        try {
            insert(configuracion);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "actualizar: ", e);
        }
    }

    @Override
    public List<Configuracion> listConfiguracion(String grupo){

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("grupo", grupo, "=");
        return buscarPorPropiedades(p);

    }

    @Override
    public List<Configuracion> listConfiguracionChild(String grupoPadre, Integer keyPadre){

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("grupoPadre", grupoPadre, "=");
        p.agregarPropiedadAnd("keyPadre", keyPadre, "=");
        return buscarPorPropiedades( p );

    }

}
