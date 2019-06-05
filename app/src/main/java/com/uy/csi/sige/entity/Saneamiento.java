package com.uy.csi.sige.entity;

import java.io.Serializable;

public class Saneamiento implements Serializable {

    private Integer id;
    private String aguaNapa;
    private String ubicRespLimPropLizq;
    private String ubicRespLimPropLder;
    private String calle;
    private String camaraUnoPronta;
    private String conexionPluvial;
    private Double crtPavCptAncho;
    private Double crtPavCptLargo;
    private Double crtPavHormAncho;
    private Double crtPavHormLargo;
    private Double crtPavOtroAncho;
    private Double crtPavOtroLargo;
    private Double crtPavRiegoAncho;
    private Double crtPavRiegoLargo;
    private Double crtPavToscaAncho;
    private Double crtPavToscaLargo;
    private Double crtVrdBaldAncho;
    private Double crtVrdBaldLargo;
    private Double crtVrdCspdAncho;
    private Double crtVrdCspdLargo;
    private Double crtVrdHormAncho;
    private Double crtVrdHormLargo;
    private Double crtVrdOtroAncho;
    private Double crtVrdOtroLargo;
    private Double crtVrdTierrAncho;
    private Double crtVrdTierrLargo;
    private String descargaBombeo;
    private String descargaIndustrial;
    private Integer diametroClctor;
    private Double diametroClctorOtro;
    private Integer diametroCnx;
    private Integer espPavAfcCalle;
    private Integer espPavAfcVrd;
    private Integer estado;
    private String fecha;
    private Integer idUsuario;
    private String identServ;
    private Integer interferencias;
    private Integer keyCiudad;
    private Integer keyDep;
    private Integer keySector;
    private String latitud;
    private String longitud;
    private Integer materialClctor;
    private String nombreOtroSector;
    private String numero;
    private String observacionPre;
    private String otroInspInterna;
    private String otroTipoSaneamiento;
    private String otrosInsp;
    private String padron;
    private Double profMaxSldSifon;
    private Integer profundidadClctor;
    private String referencia;
    private String timestampRegistro;
    private Integer tipCnxAlc;
    private String tipoEfluente;
    private Integer tipoInspSaneamiento;
    private String tipoInspSaneamientoOtro;
    private Integer tipoSaneamiento;
    private String tipoSuelo;
    private String ubicCmr1RefPdn;
    private String usuarioIdentServ;
    private String crtVrdOtro;
    private String crtPavOtro;

    public String getCrtVrdOtro() {
        return crtVrdOtro;
    }

    public void setCrtVrdOtro(String crtVrdOtro) {
        this.crtVrdOtro = crtVrdOtro;
    }

    public String getCrtPavOtro() {
        return crtPavOtro;
    }

    public void setCrtPavOtro(String crtPavOtro) {
        this.crtPavOtro = crtPavOtro;
    }

    public String getAguaNapa() {
        return aguaNapa;
    }

    public void setAguaNapa(String aguaNapa) {
        this.aguaNapa = aguaNapa;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCamaraUnoPronta() {
        return camaraUnoPronta;
    }

    public void setCamaraUnoPronta(String camaraUnoPronta) {
        this.camaraUnoPronta = camaraUnoPronta;
    }

    public String getConexionPluvial() {
        return conexionPluvial;
    }

    public void setConexionPluvial(String conexionPluvial) {
        this.conexionPluvial = conexionPluvial;
    }

    public String getDescargaBombeo() {
        return descargaBombeo;
    }

    public void setDescargaBombeo(String descargaBombeo) {
        this.descargaBombeo = descargaBombeo;
    }

    public String getDescargaIndustrial() {
        return descargaIndustrial;
    }

    public void setDescargaIndustrial(String descargaIndustrial) {
        this.descargaIndustrial = descargaIndustrial;
    }

    public Integer getDiametroClctor() {
        return diametroClctor;
    }

    public void setDiametroClctor(Integer diametroClctor) {
        this.diametroClctor = diametroClctor;
    }

    public Double getDiametroClctorOtro() {
        return diametroClctorOtro;
    }

    public void setDiametroClctorOtro(Double diametroClctorOtro) {
        this.diametroClctorOtro = diametroClctorOtro;
    }

    public Integer getDiametroCnx() {
        return diametroCnx;
    }

    public void setDiametroCnx(Integer diametroCnx) {
        this.diametroCnx = diametroCnx;
    }

    public Integer getEspPavAfcCalle() {
        return espPavAfcCalle;
    }

    public void setEspPavAfcCalle(Integer espPavAfcCalle) {
        this.espPavAfcCalle = espPavAfcCalle;
    }

    public Integer getEspPavAfcVrd() {
        return espPavAfcVrd;
    }

    public void setEspPavAfcVrd(Integer espPavAfcVrd) {
        this.espPavAfcVrd = espPavAfcVrd;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdentServ() {
        return identServ;
    }

    public void setIdentServ(String identServ) {
        this.identServ = identServ;
    }

    public Integer getInterferencias() {
        return interferencias;
    }

    public void setInterferencias(Integer interferencias) {
        this.interferencias = interferencias;
    }

    public Integer getKeyCiudad() {
        return keyCiudad;
    }

    public void setKeyCiudad(Integer keyCiudad) {
        this.keyCiudad = keyCiudad;
    }

    public Integer getKeyDep() {
        return keyDep;
    }

    public void setKeyDep(Integer keyDep) {
        this.keyDep = keyDep;
    }

    public Integer getKeySector() {
        return keySector;
    }

    public void setKeySector(Integer keySector) {
        this.keySector = keySector;
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

    public Integer getMaterialClctor() {
        return materialClctor;
    }

    public void setMaterialClctor(Integer materialClctor) {
        this.materialClctor = materialClctor;
    }

    public String getNombreOtroSector() {
        return nombreOtroSector;
    }

    public void setNombreOtroSector(String nombreOtroSector) {
        this.nombreOtroSector = nombreOtroSector;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObservacionPre() {
        return observacionPre;
    }

    public void setObservacionPre(String observacionPre) {
        this.observacionPre = observacionPre;
    }

    public String getOtroInspInterna() {
        return otroInspInterna;
    }

    public void setOtroInspInterna(String otroInspInterna) {
        this.otroInspInterna = otroInspInterna;
    }

    public String getOtroTipoSaneamiento() {
        return otroTipoSaneamiento;
    }

    public void setOtroTipoSaneamiento(String otroTipoSaneamiento) {
        this.otroTipoSaneamiento = otroTipoSaneamiento;
    }

    public String getOtrosInsp() {
        return otrosInsp;
    }

    public void setOtrosInsp(String otrosInsp) {
        this.otrosInsp = otrosInsp;
    }

    public String getPadron() {
        return padron;
    }

    public void setPadron(String padron) {
        this.padron = padron;
    }

    public Double getProfMaxSldSifon() {
        return profMaxSldSifon;
    }

    public void setProfMaxSldSifon(Double profMaxSldSifon) {
        this.profMaxSldSifon = profMaxSldSifon;
    }

    public Integer getProfundidadClctor() {
        return profundidadClctor;
    }

    public void setProfundidadClctor(Integer profundidadClctor) {
        this.profundidadClctor = profundidadClctor;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTimestampRegistro() {
        return timestampRegistro;
    }

    public void setTimestampRegistro(String timestampRegistro) {
        this.timestampRegistro = timestampRegistro;
    }

    public Integer getTipCnxAlc() {
        return tipCnxAlc;
    }

    public void setTipCnxAlc(Integer tipCnxAlc) {
        this.tipCnxAlc = tipCnxAlc;
    }

    public String getTipoEfluente() {
        return tipoEfluente;
    }

    public void setTipoEfluente(String tipoEfluente) {
        this.tipoEfluente = tipoEfluente;
    }

    public Integer getTipoInspSaneamiento() {
        return tipoInspSaneamiento;
    }

    public void setTipoInspSaneamiento(Integer tipoInspSaneamiento) {
        this.tipoInspSaneamiento = tipoInspSaneamiento;
    }

    public String getTipoInspSaneamientoOtro() {
        return tipoInspSaneamientoOtro;
    }

    public void setTipoInspSaneamientoOtro(String tipoInspSaneamientoOtro) {
        this.tipoInspSaneamientoOtro = tipoInspSaneamientoOtro;
    }

    public Integer getTipoSaneamiento() {
        return tipoSaneamiento;
    }

    public void setTipoSaneamiento(Integer tipoSaneamiento) {
        this.tipoSaneamiento = tipoSaneamiento;
    }

    public String getTipoSuelo() {
        return tipoSuelo;
    }

    public void setTipoSuelo(String tipoSuelo) {
        this.tipoSuelo = tipoSuelo;
    }

    public String getUbicCmr1RefPdn() {
        return ubicCmr1RefPdn;
    }

    public void setUbicCmr1RefPdn(String ubicCmr1RefPdn) {
        this.ubicCmr1RefPdn = ubicCmr1RefPdn;
    }

    public String getUsuarioIdentServ() {
        return usuarioIdentServ;
    }

    public void setUsuarioIdentServ(String usuarioIdentServ) {
        this.usuarioIdentServ = usuarioIdentServ;
    }

    public String getUbicRespLimPropLizq() {
        return ubicRespLimPropLizq;
    }

    public void setUbicRespLimPropLizq(String ubicRespLimPropLizq) {
        this.ubicRespLimPropLizq = ubicRespLimPropLizq;
    }

    public String getUbicRespLimPropLder() {
        return ubicRespLimPropLder;
    }

    public void setUbicRespLimPropLder(String ubicRespLimPropLder) {
        this.ubicRespLimPropLder = ubicRespLimPropLder;
    }

    public Double getCrtPavCptAncho() {
        return crtPavCptAncho;
    }

    public void setCrtPavCptAncho(Double crtPavCptAncho) {
        this.crtPavCptAncho = crtPavCptAncho;
    }

    public Double getCrtPavCptLargo() {
        return crtPavCptLargo;
    }

    public void setCrtPavCptLargo(Double crtPavCptLargo) {
        this.crtPavCptLargo = crtPavCptLargo;
    }

    public Double getCrtPavHormAncho() {
        return crtPavHormAncho;
    }

    public void setCrtPavHormAncho(Double crtPavHormAncho) {
        this.crtPavHormAncho = crtPavHormAncho;
    }

    public Double getCrtPavHormLargo() {
        return crtPavHormLargo;
    }

    public void setCrtPavHormLargo(Double crtPavHormLargo) {
        this.crtPavHormLargo = crtPavHormLargo;
    }

    public Double getCrtPavOtroAncho() {
        return crtPavOtroAncho;
    }

    public void setCrtPavOtroAncho(Double crtPavOtroAncho) {
        this.crtPavOtroAncho = crtPavOtroAncho;
    }

    public Double getCrtPavOtroLargo() {
        return crtPavOtroLargo;
    }

    public void setCrtPavOtroLargo(Double crtPavOtroLargo) {
        this.crtPavOtroLargo = crtPavOtroLargo;
    }

    public Double getCrtPavRiegoAncho() {
        return crtPavRiegoAncho;
    }

    public void setCrtPavRiegoAncho(Double crtPavRiegoAncho) {
        this.crtPavRiegoAncho = crtPavRiegoAncho;
    }

    public Double getCrtPavRiegoLargo() {
        return crtPavRiegoLargo;
    }

    public void setCrtPavRiegoLargo(Double crtPavRiegoLargo) {
        this.crtPavRiegoLargo = crtPavRiegoLargo;
    }

    public Double getCrtPavToscaAncho() {
        return crtPavToscaAncho;
    }

    public void setCrtPavToscaAncho(Double crtPavToscaAncho) {
        this.crtPavToscaAncho = crtPavToscaAncho;
    }

    public Double getCrtPavToscaLargo() {
        return crtPavToscaLargo;
    }

    public void setCrtPavToscaLargo(Double crtPavToscaLargo) {
        this.crtPavToscaLargo = crtPavToscaLargo;
    }

    public Double getCrtVrdBaldAncho() {
        return crtVrdBaldAncho;
    }

    public void setCrtVrdBaldAncho(Double crtVrdBaldAncho) {
        this.crtVrdBaldAncho = crtVrdBaldAncho;
    }

    public Double getCrtVrdBaldLargo() {
        return crtVrdBaldLargo;
    }

    public void setCrtVrdBaldLargo(Double crtVrdBaldLargo) {
        this.crtVrdBaldLargo = crtVrdBaldLargo;
    }

    public Double getCrtVrdCspdAncho() {
        return crtVrdCspdAncho;
    }

    public void setCrtVrdCspdAncho(Double crtVrdCspdAncho) {
        this.crtVrdCspdAncho = crtVrdCspdAncho;
    }

    public Double getCrtVrdCspdLargo() {
        return crtVrdCspdLargo;
    }

    public void setCrtVrdCspdLargo(Double crtVrdCspdLargo) {
        this.crtVrdCspdLargo = crtVrdCspdLargo;
    }

    public Double getCrtVrdHormAncho() {
        return crtVrdHormAncho;
    }

    public void setCrtVrdHormAncho(Double crtVrdHormAncho) {
        this.crtVrdHormAncho = crtVrdHormAncho;
    }

    public Double getCrtVrdHormLargo() {
        return crtVrdHormLargo;
    }

    public void setCrtVrdHormLargo(Double crtVrdHormLargo) {
        this.crtVrdHormLargo = crtVrdHormLargo;
    }

    public Double getCrtVrdOtroAncho() {
        return crtVrdOtroAncho;
    }

    public void setCrtVrdOtroAncho(Double crtVrdOtroAncho) {
        this.crtVrdOtroAncho = crtVrdOtroAncho;
    }

    public Double getCrtVrdOtroLargo() {
        return crtVrdOtroLargo;
    }

    public void setCrtVrdOtroLargo(Double crtVrdOtroLargo) {
        this.crtVrdOtroLargo = crtVrdOtroLargo;
    }

    public Double getCrtVrdTierrAncho() {
        return crtVrdTierrAncho;
    }

    public void setCrtVrdTierrAncho(Double crtVrdTierrAncho) {
        this.crtVrdTierrAncho = crtVrdTierrAncho;
    }

    public Double getCrtVrdTierrLargo() {
        return crtVrdTierrLargo;
    }

    public void setCrtVrdTierrLargo(Double crtVrdTierrLargo) {
        this.crtVrdTierrLargo = crtVrdTierrLargo;
    }
}
