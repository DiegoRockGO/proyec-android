package com.uy.csi.sige.entity;

import java.io.Serializable;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class User implements Serializable {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String password;
    private String nickname;
    private Integer estado;

    public User(){}

    public User(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return "com.uy.csi.sige.entity.User[id: " + id +
                ", estado:" + estado +
                ", nombre:" + nombre +
                ", apellidos:" + apellidos +"]";
    }
}
