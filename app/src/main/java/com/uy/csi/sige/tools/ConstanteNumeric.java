package com.uy.csi.sige.tools;

/**
 * Created by Denisse on 03/08/2016.
 */
public class ConstanteNumeric {

    public static final int NO_EXITE= -1;
    public static final int SIN_PERMISO= -2;
    public static final int OUT_FATHER= -2;
    public static final int OPEN= 1;//no enviado
    public static final int CLOSE= 0;//enviado

    public static final int ADD= 1;
    public static final int UPDATE= 2;
    public static final int DATA_SENDING= 3;

    public static final int OPC_EVENT = 1;
    public static final int OPC_SEND= 2;
    public static final int OPC_CLEAN_DATA_UPLOADED = 3;
    public static final int OPC_SANEAMIENTO = 4;
    public static final int OPC_CLOSE_SESION = 5;

    public static final int DLG_AVISO= 4;
    public static final int DLG_CONFIRM= 5;
    public static final int WS_AUTENTICATION= 6;
    public static final int DLG_CLOSE_SESION= 7;
    public static final int DLG_ELIMINAR_DATA_SINCRONIZADA= 100;
    public static final int DLG_OUT_APP= 8;
    public static final int DLG_SEND= 9;
    public static final int DLG_BACK= 10;
    public static final int DLG_DELETE_PICTURE= 11;
    public static final int DLG_OPEN_SETTINGS= 15;
    public static final int DLG_CANCEL= 0;
    public static final int GPS= 19;

    public static final int TYPE_IMAGE= 12;
    public static final int CAMERA= 13;
    public static final int OK_SAVE_PICTURE=14;


    public static final int TYPE_DETECCION=16;
    public static final int TYPE_OBRA=17;
    public static final int TYPE_INSPECTOR=18;

    public static final int TYPE_LOCATION=21;

    public static final int ID_OTHER_INSPECTOR=100;

    public static final int DET_FUGA=1001;
    public static final int DET_FRAUDE=1002;
    public static final int DET_CNX_MEDIDOR=1003;
    public static final int DET_GRAN_CSMDR=1004;
    public static final int DET_ELMT_RED=1005;
    public static final int DET_OTRO=1006;

    public static final int DET_SELECT_FRAUDE_OTRO =11103;
    public static final int DET_SELECT_CON_DOM_MED_OTRO = 10000020;

    public static final int ID_OTHER_TYPE_DETECCION_3=50;
    public static final int ID_OTHER_TYPE_OBRA_1=71;
    public static final int ID_OTHER_TYPE_OBRA_2=76;
    public static final int ID_OTHER_ELMT_RD_OBRA_2=75;
    public static final int ID_OTHER_TYPE_OBRA_3=91;

    public static final int ID_DETECCION_GRAN_CONSUMIDOR_1=39;
    public static final int ID_DETECCION_GRAN_CONSUMIDOR_2=-3;
    public static final int LENGHT_NAME_RANDOM= 42;

    public static final int GROUP_0 = 0;
    public static final int GROUP_1 = 1;


    public static final int EXCEPTION_NO_EXISTE= -1;
    public static final int EXCEPTION_SIN_PERMISO_MOVIL= -2;
    public static final int EXCEPTION_ERROR_SERVIDOR= -4;
    public static final int EXCEPTION_ERROR_JSON_SERVER= -9;
    public static final int EXCEPTION_SERVIDOR_OFF= -10;
    public static final int EXCEPTION_JSON_LOCAL= -11;
    public static final int EXCEPTION_RETRY= -12;
    public static final int EXCEPTION_TIME_OUT= -13;

    public static final int UBIC_OTRO_INDIC_NOMBRE= 22202;
    public static final int UBIC_MALDONADO= 22200;
    public static final int SUCCESSFULL=1;
    public static final int FAIL=-1;

    public static final Integer VAL_PAR_DISTRITO_OTRO = 5;
    public static final Integer VAL_PAR_SANEAMIENTO_OTRO = 5;
    public static final Integer VAL_PAR_SANEMIANETO_INSP_PREV = 1;
    public static final Integer VAL_PAR_SANEMIANETO_INST_INT = 2;
    public static final Integer VAL_PAR_SANEMIANETO_EJEC_CNX_DOM = 3;
    public static final Integer VAL_PAR_SANEMIANETO_INSP = 4;
    public static final Integer VAL_PAR_SANEMIANETO_OTRO = 5;

    public static final Integer VAL_PAR_TIP_CNX_ALCANTARILLADO_CNX_PRE = 1;
    public static final Integer VAL_PAR_TIP_CNX_ALCANTARILLADO_CAM_VER = 2;
    public static final Integer VAL_PAR_TIP_CNX_ALCANTARILLADO_COL_CAL = 3;
    public static final Integer VAL_PAR_TIP_CNX_ALCANTARILLADO_COL_VER = 4;
    public static final Integer VAL_PAR_TIP_CNX_ALCANTARILLADO_EFL_DEC = 5;

    public static final Integer VAL_PAR_DIAMETRO_CLCTO_MAY_500 = 10;

    public static final Integer VAL_PAR_INSP_SNMT_OTRO = 6;

    public static final Integer ESTADO_ACTIVO = 1;
    public static final Integer ESTADO_ENVIADO = 4;

}
