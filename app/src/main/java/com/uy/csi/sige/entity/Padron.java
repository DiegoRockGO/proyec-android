package com.uy.csi.sige.entity;

import java.util.List;

public class Padron {

    public static final String TABLA = "padron";

    private Long id;
    private Integer padron;
    private String nroReferencia;
    private String regimen;
    private Double areaTerreno;
    private Integer departamento;
    private String departamentoStr;
    private Double areaEdificada;
    private String localidad;
    private String unidad;
    private String direccion;
    private String esquina;
    private Integer visitaInteriorInmueble;
    private Integer categoria;
    private String categoriaStr;
    private String descripcionCategoria;
    private Integer estado;
    private String estadoStr;
    private String descripcionEstado;
    private Integer reformas;
    private String areaReforma;
    private Integer patologia;
    private Integer humedades;
    private String descripcionHumedades;
    private Integer grietas;
    private String descripcionGrietas;
    private Integer instSanitaria;
    private Integer instElectrica;
    private String observacion;
    private Long usuario;
    private String usuarioStr;
    private Long fechaRegistro;
    private Long fechaModificacion;
    private Integer estadoRegistro;

    private List<Construccion> construccionList;

    public List<Construccion> getConstruccionList() {
        return construccionList;
    }

    public void setConstruccionList(List<Construccion> construccionList) {
        this.construccionList = construccionList;
    }

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

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPadron() {
        return padron;
    }

    public void setPadron(Integer padron) {
        this.padron = padron;
    }

    public String getNroReferencia() {
        return nroReferencia;
    }

    public void setNroReferencia(String nroReferencia) {
        this.nroReferencia = nroReferencia;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public Double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(Double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public Integer getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Integer departamento) {
        this.departamento = departamento;
    }

    public String getDepartamentoStr() {
        return departamentoStr;
    }

    public void setDepartamentoStr(String departamentoStr) {
        this.departamentoStr = departamentoStr;
    }

    public Double getAreaEdificada() {
        return areaEdificada;
    }

    public void setAreaEdificada(Double areaEdificada) {
        this.areaEdificada = areaEdificada;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEsquina() {
        return esquina;
    }

    public void setEsquina(String esquina) {
        this.esquina = esquina;
    }

    public Integer getVisitaInteriorInmueble() {
        return visitaInteriorInmueble;
    }

    public void setVisitaInteriorInmueble(Integer visitaInteriorInmueble) {
        this.visitaInteriorInmueble = visitaInteriorInmueble;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public String getCategoriaStr() {
        return categoriaStr;
    }

    public void setCategoriaStr(String categoriaStr) {
        this.categoriaStr = categoriaStr;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
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

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public Integer getReformas() {
        return reformas;
    }

    public void setReformas(Integer reformas) {
        this.reformas = reformas;
    }

    public String getAreaReforma() {
        return areaReforma;
    }

    public void setAreaReforma(String areaReforma) {
        this.areaReforma = areaReforma;
    }

    public Integer getPatologia() {
        return patologia;
    }

    public void setPatologia(Integer patologia) {
        this.patologia = patologia;
    }

    public Integer getHumedades() {
        return humedades;
    }

    public void setHumedades(Integer humedades) {
        this.humedades = humedades;
    }

    public String getDescripcionHumedades() {
        return descripcionHumedades;
    }

    public void setDescripcionHumedades(String descripcionHumedades) {
        this.descripcionHumedades = descripcionHumedades;
    }

    public Integer getGrietas() {
        return grietas;
    }

    public void setGrietas(Integer grietas) {
        this.grietas = grietas;
    }

    public String getDescripcionGrietas() {
        return descripcionGrietas;
    }

    public void setDescripcionGrietas(String descripcionGrietas) {
        this.descripcionGrietas = descripcionGrietas;
    }

    public Integer getInstSanitaria() {
        return instSanitaria;
    }

    public void setInstSanitaria(Integer instSanitaria) {
        this.instSanitaria = instSanitaria;
    }

    public Integer getInstElectrica() {
        return instElectrica;
    }

    public void setInstElectrica(Integer instElectrica) {
        this.instElectrica = instElectrica;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
