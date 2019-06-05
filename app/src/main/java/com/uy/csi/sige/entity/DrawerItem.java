package com.uy.csi.sige.entity;

import java.io.Serializable;

/**
 * Created by Denisse on 04/08/2016.
 */
public class DrawerItem implements Serializable {

    private int idOpcion;
    private String nameOpcion;
    private int imgResId;
    private boolean selected;
    private int position;


    public DrawerItem (int idOpcion,String nameOpcion,int imgResId,boolean selected,int position){
        super();
        this.idOpcion=idOpcion;
        this.nameOpcion=nameOpcion;
        this.imgResId=imgResId;
        this.selected=selected;
        this.position=position;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNameOpcion() {
        return nameOpcion;
    }

    public void setNameOpcion(String nameOpcion) {
        this.nameOpcion = nameOpcion;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
