package com.uy.csi.sige.entity;

import java.io.Serializable;

/**
 * Created by USUARIO on 21/08/2016.
 */
public class DataSend implements Serializable {

    private int idData;
    private String nombre;
    private int state;
    private String fecha;
    private int idUser;

    public DataSend( ){

    }

    public DataSend(Integer idUser){
        this.idUser=idUser;
    }

    public static DataSend toBean(DataSend data, PictureEvent picture){
        data.setIdData(picture.getIdPctr());
        data.setNombre(picture.getName());
        data.setState(picture.getState());


        return data;
    }

    public int getIdData() {
        return idData;
    }

    public void setIdData(int idData) {
        this.idData = idData;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
