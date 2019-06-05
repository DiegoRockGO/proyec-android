package com.uy.csi.sige.entity;

import com.uy.csi.sige.tools.ConstanteText;

import java.io.Serializable;

import static com.uy.csi.sige.tools.StringUtil.*;

public class Configuracion implements Serializable {

    private Integer id;
    private Integer estado;
    private String grupo;
    private String nombre;
    private Integer key;
    private String valorTexto;
    private Double valorNumero;
    private String grupoPadre;
    private Integer keyPadre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValorTexto() {
        return valorTexto;
    }

    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    public Double getValorNumero() {
        return valorNumero;
    }

    public void setValorNumero(Double valorNumero) {
        this.valorNumero = valorNumero;
    }

    public String getGrupoPadre() {
        return grupoPadre;
    }

    public void setGrupoPadre(String grupoPadre) {
        this.grupoPadre = grupoPadre;
    }

    public Integer getKeyPadre() {
        return keyPadre;
    }

    public void setKeyPadre(Integer keyPadre) {
        this.keyPadre = keyPadre;
    }

    @Override
    public String toString(){
        return nombre;
    }


    public boolean equals(Configuracion configuracion ){

        if( isEmpty( configuracion ) ){
            return false;
        }

        if( equiv( id, configuracion.id ) ){
            return true;
        }

        return false;
    }

    public static final Configuracion generateUnselectOption(){
        Configuracion conf = new Configuracion();
        conf.setNombre( ConstanteText.NAME_ITEM_SELECCIONE );
        return conf;
    }
}
