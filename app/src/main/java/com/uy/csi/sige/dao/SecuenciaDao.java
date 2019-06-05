package com.uy.csi.sige.dao;

import com.uy.csi.sige.entity.Secuencia;

public interface SecuenciaDao {

    Secuencia getSecuencia(String tabla);

    void actualizar(Secuencia secuencia) throws NoSuchFieldException, IllegalAccessException;

}
