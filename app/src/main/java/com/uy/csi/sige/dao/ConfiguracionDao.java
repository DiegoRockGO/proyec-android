package com.uy.csi.sige.dao;

import com.uy.csi.sige.entity.Configuracion;

import java.util.List;

public interface ConfiguracionDao {

    List<Configuracion> listConfiguracion(String grupo);

    List<Configuracion> listConfiguracionChild(String grupoPadre, Integer keyPadre);

    Configuracion getConfiguracion(Integer idConfiguracion);

    void actualizar(Configuracion configuracion);

    void insertar(Configuracion configuracion);

}
