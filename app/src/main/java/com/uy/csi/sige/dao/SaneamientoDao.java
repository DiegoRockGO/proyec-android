package com.uy.csi.sige.dao;

import com.uy.csi.sige.entity.Saneamiento;

import java.util.List;

public interface SaneamientoDao {

    Saneamiento insertar(Saneamiento saneamiento);

    List<Saneamiento> list();

    void updateEstados();

    void deshabilitarRegistros();
}
