package com.uy.csi.sige.dto;

import com.uy.csi.sige.entity.Event;
import static com.uy.csi.sige.tools.StringUtil.*;
import static com.uy.csi.sige.tools.DbTools.*;

public class EventSQL {

    public static final String INSERT = join("INSERT INTO EVENT (ID_EVENT, \n",
            "\tDATE, ID_USER, LATITUD, LONGITUD, STATE, TYPE_EVENT, \n",
            "\tID_DEPARTAMENT, COD_DEPARTAMENT, NAME_DEPARTAMENT , ID_CITIE, COD_CITIE, NAME_CITIE, \n",
            "\tID_SECTOR, COD_SECTOR, NAME_SECTOR, OTRO_SECTOR, \n",
            "\tID_INSPECTOR, COD_INSPECTOR, NAME_INSPECTOR, OTHER_INSPECTOR, \n",
            "\tSTREE, NUMBER, REFERENCIA, OBSERVATION, NUMBER_PICTURE, \n",
            "\tID_SPINNER_DTC1, COD_SPINNER_DTC1, NAME_SPINNER_DTC1, OTHER_SPINNER_DTC1, \n",
            "\tID_SPINNER_DTC2, COD_SPINNER_DTC2, NAME_SPINNER_DTC2, OTHER_SPINNER_DTC2, \n",
            "\tID_SPINNER_DTC3, COD_SPINNER_DTC3, NAME_SPINNER_DTC3, OTHER_SPINNER_DTC3, \n",
            "\tID_SPINNER_DTC4, COD_SPINNER_DTC4, NAME_SPINNER_DTC4, OTHER_SPINNER_DET_4, \n",
            "\tORDER_SERVICE, NUMBER_OS, DATE_FIN, OBS_PRELIMINAR, \n",
            "\tID_SPINNER_OBR1, COD_SPINNER_OBR1, NAME_SPINNER_OBR1, OTHER_SPINNER_OBR1, \n",
            "\tID_SPINNER_OBR2, COD_SPINNER_OBR2, NAME_SPINNER_OBR2, OTHER_SPINNER_OBR2, \n",
            "\tID_SPINNER_OBR3, COD_SPINNER_OBR3, NAME_SPINNER_OBR3, OTHER_SPINNER_OBR3, \n",
            "\tID_WEB, DATE_INIT_OBRA, MEDIDOR\n",
            "\t)VALUES(%s)\n");

    public static String createInsertSQLQuery(Event event){

        if( isEmpty(event) ){
            return "";
        }

        StringBuffer sb = new StringBuffer();

        sb.append( join(getSQLInt(event.getIdEvent()),  ",\n ") );
        sb.append( join(getSQLStr(event.getDate()),     ",\n ") );
        sb.append( join(getSQLInt(event.getIdUser()),   ",\n ") );
        sb.append( join(getSQLStr(event.getLatitud()),  ",\n ") );
        sb.append( join(getSQLStr(event.getLongitud()), ",\n ") );
        sb.append( join(getSQLInt(event.getState()),    ",\n ") );
        sb.append( join(getSQLInt(event.getTypeEvent()),",\n ") );

        sb.append( join(getSQLInt(event.getIdDepartament()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodDepartament()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameDepartament()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdCitie()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodCitie()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameCitie()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSector()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSector()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSector()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtraUbicacion()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdInspector()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodInspector()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameInspector()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherInspector()),  ",\n ") );

        sb.append( join(getSQLStr(event.getStree()),  ",\n ") );
        sb.append( join(getSQLStr(event.getNumber()),  ",\n ") );
        sb.append( join(getSQLStr(event.getReferencia()),  ",\n ") );
        sb.append( join(getSQLStr(event.getObservation()),  ",\n ") );
        sb.append( join(getSQLInt(event.getNumberPicture()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerDtc1()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerDtc1()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerDtc1()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerDtc1()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerDtc2()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerDtc2()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerDtc2()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerDtc2()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerDtc3()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerDtc3()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerDtc3()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerDtc3()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerDtc4()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerDtc4()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerDtc4()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerDet4()),  ",\n ") );

        sb.append( join(getSQLStr(event.getOrderService()),  ",\n ") );
        sb.append( join(getSQLStr(event.getNumberOs()),  ",\n ") );
        sb.append( join(getSQLStr(event.getDateFin()),  ",\n ") );
        sb.append( join(getSQLStr(event.getObsPreliminar()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerObr1()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerObr1()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerObr1()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerObr1()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerObr2()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerObr2()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerObr2()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerObr2()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdSpinnerObr3()),    ",\n ") );
        sb.append( join(getSQLStr(event.getCodSpinnerObr3()),   ",\n ") );
        sb.append( join(getSQLStr(event.getNameSpinnerObr3()),  ",\n ") );
        sb.append( join(getSQLStr(event.getOtherSpinnerObr3()),  ",\n ") );

        sb.append( join(getSQLInt(event.getIdWeb()),    ",\n ") );
        sb.append( join(getSQLStr(event.getDateInitObra()),  ",\n ") );
        sb.append( join(getSQLStr(event.getMedidor())) );

        return String.format(INSERT, sb.toString());
    }
}
