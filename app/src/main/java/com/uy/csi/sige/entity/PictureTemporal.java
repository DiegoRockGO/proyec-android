package com.uy.csi.sige.entity;

import java.io.Serializable;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class PictureTemporal implements Serializable {

    private int idPctr;
    private String name;
    private String latitud;
    private String longitud;
    private String date;
    private String description;
    private int state;

    public static PictureEvent toBeanFoto(PictureTemporal temp,Integer idEvent){
        PictureEvent pctr=new PictureEvent();
        pctr.setIdPctr(temp.getIdPctr());

        pctr.setName(temp.getName());
        pctr.setDescription(temp.getDescription());
        pctr.setLatitud(temp.getLatitud());
        pctr.setLongitud(temp.getLongitud());
        pctr.setDate(temp.getDate());
        pctr.setIdEvent(idEvent);

        return pctr;

    }

    public static PictureTemporal toBeanFotoTemporal(PictureEvent pctr){
        PictureTemporal temp=new PictureTemporal();
        temp.setIdPctr(pctr.getIdPctr());
        temp.setName(pctr.getName());
        temp.setDescription(pctr.getDescription());
        temp.setLatitud(pctr.getLatitud());
        temp.setLongitud(pctr.getLongitud());
        temp.setDate(pctr.getDate());

        return temp;
    }

    public int getIdPctr() {
        return idPctr;
    }

    public void setIdPctr(int idPctr) {
        this.idPctr = idPctr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
