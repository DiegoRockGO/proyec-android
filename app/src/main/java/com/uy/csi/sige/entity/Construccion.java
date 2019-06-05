package com.uy.csi.sige.entity;

public class Construccion {

    public static final String TABLA = "construccion";

    private Long id;
    private Long idPadron;
    private String destino;
    private Integer nivel;
    private Integer habitaciones;
    private String servicios;
    private String categoriaStr;
    private Integer categoria;
    private Integer estado;
    private String estadoStr;
    private Double areaEdificada;
    private Integer anio;
    private String cubierta;
    private Long usuario;
    private String usuarioStr;
    private Long fechaRegistro;
    private Long fechaModificacion;
    private Long estadoRegistro;

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioStr() {
        return usuarioStr;
    }

    public void setUsuarioStr(String usuarioStr) {
        this.usuarioStr = usuarioStr;
    }

    public Long getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Long fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Long getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Long fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Long getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Long estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Construccion() {
    }

    public Construccion(String destino, String estadoStr) {
        this.destino = destino;
        this.estadoStr = estadoStr;
    }

    public Long getIdPadron() {
        return idPadron;
    }

    public void setIdPadron(Long idPadron) {
        this.idPadron = idPadron;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public String getCategoriaStr() {
        return categoriaStr;
    }

    public void setCategoriaStr(String categoriaStr) {
        this.categoriaStr = categoriaStr;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getEstadoStr() {
        return estadoStr;
    }

    public void setEstadoStr(String estadoStr) {
        this.estadoStr = estadoStr;
    }

    public Double getAreaEdificada() {
        return areaEdificada;
    }

    public void setAreaEdificada(Double areaEdificada) {
        this.areaEdificada = areaEdificada;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getCubierta() {
        return cubierta;
    }

    public void setCubierta(String cubierta) {
        this.cubierta = cubierta;
    }
}
