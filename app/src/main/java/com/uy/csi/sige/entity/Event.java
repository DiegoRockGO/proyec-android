package com.uy.csi.sige.entity;

import com.uy.csi.sige.dto.EventSQL;
import com.uy.csi.sige.tools.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class Event extends EventSQL implements Serializable {

    private int idEvent;
    private String date;
    private String latitud;
    private String longitud;
    private int state;
    private int typeEvent;
    private int idUser;

    private int idDepartament;
    private String codDepartament;
    private String nameDepartament;

    private int idCitie;
    private String codCitie;
    private String nameCitie;

    private int idSector;
    private String codSector;
    private String nameSector;

    private int idInspector;
    private String codInspector;
    private String nameInspector;
    private String otherInspector;

    private String stree;
    private String number;
    private String referencia;

    private String observation;
    private int numberPicture;

    //DETECCION
    private int idSpinnerDtc1;
    private String codSpinnerDtc1;
    private String nameSpinnerDtc1;
    private String otherSpinnerDtc1;

    private int idSpinnerDtc2;
    private String codSpinnerDtc2;
    private String nameSpinnerDtc2;
    private String otherSpinnerDtc2;

    private int idSpinnerDtc3;
    private String codSpinnerDtc3;
    private String nameSpinnerDtc3;
    private String otherSpinnerDtc3;

    private int idSpinnerDtc4;
    private String codSpinnerDtc4;
    private String nameSpinnerDtc4;
    private String otherSpinnerDet4;

    //OBRA
    private String dateFin;
    private String obsPreliminar;
    private int idSpinnerObr1;
    private String codSpinnerObr1;
    private String nameSpinnerObr1;
    private String otherSpinnerObr1;

    private int idSpinnerObr2;
    private String codSpinnerObr2;
    private String nameSpinnerObr2;
    private String otherSpinnerObr2;

    private int idSpinnerObr3;
    private String codSpinnerObr3;
    private String nameSpinnerObr3;
    private String otherSpinnerObr3;

    private String orderService;
    private String numberOs;
    private String location;

    private int idWeb;
    private String dateInitObra;

    private String otraUbicacion;

    private String medidor;

    private List<PictureEvent> pictureEventList;

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(int idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getCodDepartament() {
        return codDepartament;
    }

    public void setCodDepartament(String codDepartament) {
        this.codDepartament = codDepartament;
    }

    public String getNameDepartament() {
        return nameDepartament;
    }

    public void setNameDepartament(String nameDepartament) {
        this.nameDepartament = nameDepartament;
    }

    public int getIdCitie() {
        return idCitie;
    }

    public void setIdCitie(int idCitie) {
        this.idCitie = idCitie;
    }

    public String getCodCitie() {
        return codCitie;
    }

    public void setCodCitie(String codCitie) {
        this.codCitie = codCitie;
    }

    public String getNameCitie() {
        return nameCitie;
    }

    public void setNameCitie(String nameCitie) {
        this.nameCitie = nameCitie;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public String getNameSector() {
        return nameSector;
    }

    public void setNameSector(String nameSector) {
        this.nameSector = nameSector;
    }

    public int getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(int idInspector) {
        this.idInspector = idInspector;
    }

    public String getCodInspector() {
        return codInspector;
    }

    public void setCodInspector(String codInspector) {
        this.codInspector = codInspector;
    }

    public String getNameInspector() {
        return nameInspector;
    }

    public void setNameInspector(String nameInspector) {
        this.nameInspector = nameInspector;
    }

    public String getOtherInspector() {
        return otherInspector;
    }

    public void setOtherInspector(String otherInspector) {
        this.otherInspector = otherInspector;
    }

    public String getStree() {
        return stree;
    }

    public void setStree(String stree) {
        this.stree = stree;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getNumberPicture() {
        return numberPicture;
    }

    public void setNumberPicture(int numberPicture) {
        this.numberPicture = numberPicture;
    }

    public int getIdSpinnerDtc1() {
        return idSpinnerDtc1;
    }

    public void setIdSpinnerDtc1(int idSpinnerDtc1) {
        this.idSpinnerDtc1 = idSpinnerDtc1;
    }

    public String getCodSpinnerDtc1() {
        return codSpinnerDtc1;
    }

    public void setCodSpinnerDtc1(String codSpinnerDtc1) {
        this.codSpinnerDtc1 = codSpinnerDtc1;
    }

    public String getNameSpinnerDtc1() {
        return nameSpinnerDtc1;
    }

    public void setNameSpinnerDtc1(String nameSpinnerDtc1) {
        this.nameSpinnerDtc1 = nameSpinnerDtc1;
    }

    public String getOtherSpinnerDtc1() {
        return otherSpinnerDtc1;
    }

    public void setOtherSpinnerDtc1(String otherSpinnerDtc1) {
        this.otherSpinnerDtc1 = otherSpinnerDtc1;
    }

    public int getIdSpinnerDtc2() {
        return idSpinnerDtc2;
    }

    public void setIdSpinnerDtc2(int idSpinnerDtc2) {
        this.idSpinnerDtc2 = idSpinnerDtc2;
    }

    public String getCodSpinnerDtc2() {
        return codSpinnerDtc2;
    }

    public void setCodSpinnerDtc2(String codSpinnerDtc2) {
        this.codSpinnerDtc2 = codSpinnerDtc2;
    }

    public String getNameSpinnerDtc2() {
        return nameSpinnerDtc2;
    }

    public void setNameSpinnerDtc2(String nameSpinnerDtc2) {
        this.nameSpinnerDtc2 = nameSpinnerDtc2;
    }

    public int getIdSpinnerDtc3() {
        return idSpinnerDtc3;
    }

    public void setIdSpinnerDtc3(int idSpinnerDtc3) {
        this.idSpinnerDtc3 = idSpinnerDtc3;
    }

    public String getCodSpinnerDtc3() {
        return codSpinnerDtc3;
    }

    public void setCodSpinnerDtc3(String codSpinnerDtc3) {
        this.codSpinnerDtc3 = codSpinnerDtc3;
    }

    public String getNameSpinnerDtc3() {
        return nameSpinnerDtc3;
    }

    public void setNameSpinnerDtc3(String nameSpinnerDtc3) {
        this.nameSpinnerDtc3 = nameSpinnerDtc3;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getObsPreliminar() {
        return obsPreliminar;
    }

    public void setObsPreliminar(String obsPreliminar) {
        this.obsPreliminar = obsPreliminar;
    }

    public int getIdSpinnerObr1() {
        return idSpinnerObr1;
    }

    public void setIdSpinnerObr1(int idSpinnerObr1) {
        this.idSpinnerObr1 = idSpinnerObr1;
    }

    public String getCodSpinnerObr1() {
        return codSpinnerObr1;
    }

    public void setCodSpinnerObr1(String codSpinnerObr1) {
        this.codSpinnerObr1 = codSpinnerObr1;
    }

    public String getNameSpinnerObr1() {
        return nameSpinnerObr1;
    }

    public void setNameSpinnerObr1(String nameSpinnerObr1) {
        this.nameSpinnerObr1 = nameSpinnerObr1;
    }

    public String getOtherSpinnerObr1() {
        return otherSpinnerObr1;
    }

    public void setOtherSpinnerObr1(String otherSpinnerObr1) {
        this.otherSpinnerObr1 = otherSpinnerObr1;
    }

    public int getIdSpinnerObr2() {
        return idSpinnerObr2;
    }

    public void setIdSpinnerObr2(int idSpinnerObr2) {
        this.idSpinnerObr2 = idSpinnerObr2;
    }

    public String getCodSpinnerObr2() {
        return codSpinnerObr2;
    }

    public void setCodSpinnerObr2(String codSpinnerObr2) {
        this.codSpinnerObr2 = codSpinnerObr2;
    }

    public String getNameSpinnerObr2() {
        return nameSpinnerObr2;
    }

    public void setNameSpinnerObr2(String nameSpinnerObr2) {
        this.nameSpinnerObr2 = nameSpinnerObr2;
    }

    public String getOtherSpinnerObr2() {
        return otherSpinnerObr2;
    }

    public void setOtherSpinnerObr2(String otherSpinnerObr2) {
        this.otherSpinnerObr2 = otherSpinnerObr2;
    }

    public int getIdSpinnerObr3() {
        return idSpinnerObr3;
    }

    public void setIdSpinnerObr3(int idSpinnerObr3) {
        this.idSpinnerObr3 = idSpinnerObr3;
    }

    public String getCodSpinnerObr3() {
        return codSpinnerObr3;
    }

    public void setCodSpinnerObr3(String codSpinnerObr3) {
        this.codSpinnerObr3 = codSpinnerObr3;
    }

    public String getNameSpinnerObr3() {
        return nameSpinnerObr3;
    }

    public void setNameSpinnerObr3(String nameSpinnerObr3) {
        this.nameSpinnerObr3 = nameSpinnerObr3;
    }

    public String getOtherSpinnerObr3() {
        return otherSpinnerObr3;
    }

    public void setOtherSpinnerObr3(String otherSpinnerObr3) {
        this.otherSpinnerObr3 = otherSpinnerObr3;
    }

    public String getOrderService() {
        return orderService;
    }

    public void setOrderService(String orderService) {
        this.orderService = orderService;
    }

    public String getNumberOs() {
        return numberOs;
    }

    public void setNumberOs(String numberOs) {
        this.numberOs = numberOs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOtherSpinnerDtc2() {
        return otherSpinnerDtc2;
    }

    public void setOtherSpinnerDtc2(String otherSpinnerDtc2) {
        this.otherSpinnerDtc2 = otherSpinnerDtc2;
    }

    public String getOtherSpinnerDtc3() {
        return otherSpinnerDtc3;
    }

    public void setOtherSpinnerDtc3(String otherSpinnerDtc3) {
        this.otherSpinnerDtc3 = otherSpinnerDtc3;
    }

    public int getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(int idWeb) {
        this.idWeb = idWeb;
    }

    public String getDateInitObra() {
        return dateInitObra;
    }

    public void setDateInitObra(String dateInitObra) {
        this.dateInitObra = dateInitObra;
    }

    public List<PictureEvent> getPictureEventList() {
        return pictureEventList;
    }

    public void setPictureEventList(List<PictureEvent> pictureEventList) {
        this.pictureEventList = pictureEventList;
    }

    public String getOtraUbicacion() {
        return otraUbicacion;
    }

    public void setOtraUbicacion(String otraUbicacion) {
        this.otraUbicacion = otraUbicacion;
    }

    public String getOtherSpinnerDet4() {
        return otherSpinnerDet4;
    }

    public void setOtherSpinnerDet4(String otherSpinnerDet4) {
        this.otherSpinnerDet4 = otherSpinnerDet4;
    }

    public int getIdSpinnerDtc4() {
        return idSpinnerDtc4;
    }

    public void setIdSpinnerDtc4(int idSpinnerDtc4) {
        this.idSpinnerDtc4 = idSpinnerDtc4;
    }

    public String getCodSpinnerDtc4() {
        return codSpinnerDtc4;
    }

    public void setCodSpinnerDtc4(String codSpinnerDtc4) {
        this.codSpinnerDtc4 = codSpinnerDtc4;
    }

    public String getNameSpinnerDtc4() {
        return nameSpinnerDtc4;
    }

    public void setNameSpinnerDtc4(String nameSpinnerDtc4) {
        this.nameSpinnerDtc4 = nameSpinnerDtc4;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public class COL_NAME{
        public static final String OTRO_SECTOR = "OTRO_SECTOR";
        public static final String OTHER_SPINNER_DET_4 = "OTHER_SPINNER_DET_4";

        public static final String ID_SPINNER_DTC4 = "ID_SPINNER_DTC4";
        public static final String COD_SPINNER_DTC4 = "COD_SPINNER_DTC4";
        public static final String NAME_SPINNER_DTC4 = "NAME_SPINNER_DTC4";

        public static final String MEDIDOR = "MEDIDOR";
    }
}
