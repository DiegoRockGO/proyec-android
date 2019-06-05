package com.uy.csi.sige.entity;

import java.io.Serializable;

/**
 * Created by dtrinidad on 05/08/2016.
 */
public class ItemSpinner implements Serializable {

    private int idItem;
    private String codigo;
    private String description;
    private int idFather;
    private int type;
    private int state;
    private int groupCol;

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdFather() {
        return idFather;
    }

    public void setIdFather(int idFather) {
        this.idFather = idFather;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public int getGroupCol() {
        return groupCol;
    }

    public void setGroupCol(int groupCol) {
        this.groupCol = groupCol;
    }
}
