package com.uy.csi.sige.entity;

import java.io.Serializable;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class PictureEvent implements Serializable {

    private int idPctr;
    private String name;
    private String latitud;
    private String longitud;
    private String date;
    private String description;
    private int idEvent;
    private int state;//open 1=no enviado close 0=enviado

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

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
