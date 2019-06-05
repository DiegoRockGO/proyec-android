package com.uy.csi.sige.tools;

/**
 * Created by Denisse on 03/08/2016.
 */
public class ConstanteText {

    public static final String URL_REST = "http://3.17.133.197:8080/sevenc/rest";
//    public static final String URL_REST = "http://192.168.0.101:8080/sevenc/rest";
//    public static final String URL_REST = "http://192.168.0.6:8080/sevenc/rest";


//    public static final String URL_REST = "http://192.168.1.12:8080/sevenc/rest";
    public static final String VERSION_APP = "1.0";

    public static final String URL_REST_SAVE_IMAGE = "/evento/saveImage/";
    public static final String RESOURCE_IS_USUARIO = "/evento/isUsuario/";
    public static final String RESOURCE_LIST_OBRAS = "/evento/listObras/";
    public static final String RESOURCE_SCRIPTS =  StringUtil.join("/conf/listScripts/", VERSION_APP) ;
    public static final String RESOURCE_CONFIGURACIONES = "/conf/listConf";

    public static final String DIRECTORY_DATA_BASE_APP="/data/com.uy.csi.sige/databases/";


    public static final String NAME_FRAGMENT_DIALOG= "dialog";
    public static final String NAME_FRAGMENT_PROGRESS= "progress";

        public static final String IMAGEN_DIRECTORY="//.SIGE//IMAGEN_FILE//";
    public static final String IMAGEN_THUMBNAIL="//.SIGE//THUMBNAIL//";
    public static final String DATA_BASE_DIRECTORY= "//.SIGE//BD_FILE//";
    public static final String DATA_BASE_DIRECTORY_DOWNLOAD= "//Rank//Base_datos//";

    public static final String FORMATO_PHOTO="ddMMyyyy_HHmmss";
    public static final String SEPARATOR="/";


    public static final String PREFIJO_FOTO= "IMG";

    public static final String EXTENSION_FOTO= ".jpg";

    public static final String NAME_SPINNER_INSPECTOR= "INSPECTOR";
    public static final String NAME_SPINNER_DEPARTAMENT= "DEPARTAMENT";
    public static final String NAME_SPINNER_CITIE= "CITIE";
    public static final String NAME_SPINNER_SECTOR= "SECTOR";
    public static final String NAME_SPINNER_DETECCION_1= "SPINNER_DETECCION_1";
    public static final String NAME_SPINNER_DETECCION_2= "SPINNER_DETECCION_2";
    public static final String NAME_SPINNER_DETECCION_3= "SPINNER_DETECCION_3";
    public static final String NAME_SPINNER_OBRA_1= "SPINNER_OBRA_1";
    public static final String NAME_SPINNER_OBRA_2= "SPINNER_OBRA_2";
    public static final String NAME_SPINNER_OBRA_3= "SPINNER_OBRA_3";
    public static final String NAME_SPINNER_OS= "SPINNER_ORDER_SERVICE";


    public static final String NAME_ID= "ID_";
    public static final String NAME_COD= "COD_";
    public static final String NAME_NAME= "NAME_";
    public static final String NAME_OTHER= "OTHER_";

    public static final String NAME_SP_SESSION= "SP_SESION";
    public static final String NAME_SP_PICTURE= "SP_PICTURE";
    public static final String NAME_SP_USER= "SP_USER";
    public static final String NAME_SP_EVENT= "SP_EVENT";//"SP_PROGRESS"
    public static final String NAME_SP_PROGRESS= "SP_PROGRESS";

    public static final String KEY_ID_USER= "ID_USER";
    public static final String KEY_NAME_USER= "NAME_USER";
    public static final String KEY_LAST_NAME_USER= "NAME_LAST_NAME_USER";
    public static final String KEY_SESION= "IN_SESION";
    public static final String KEY_POSITION_MENU= "POSITION_MENU";
    public static final String KEY_ID_MENU= "ID_MENU";
    public static final String KEY_SCREEN_SIZE= "SCREEN_SIZE";
    public static final String KEY_ID_DEVICE= "ID_DEVICE";
    public static final String KEY_ID_PICTURE= "ID_PICTURE";
    public static final String KEY_TYPE_PROCESS= "TYPE_PROCESS";
    public static final String KEY_NAME_PICTURE= "NAME_PICTURE";


    public static final String KEY_NAME_EVENT= "EVENT";
    public static final String KEY_ID_EVENT= "ID_EVENT";
    public static final String KEY_DATE= "DATE";
    public static final String KEY_LATITUD= "LATITUD";
    public static final String KEY_LONGITUD= "LONGITUD";
    public static final String KEY_STATE= "STATE";
    public static final String KEY_TYPE_EVENT= "TYPE_EVENT";
    public static final String KEY_STREET= "STREET";
    public static final String KEY_NUMBER= "NUMBER";
    public static final String KEY_REFERENCIA= "REFERENCIA";
    public static final String KEY_OBSERVATION= "OBSERVATION";
    public static final String KEY_DATE_FIN= "DATE_FIN";
    public static final String KEY_OBSERVATION_PRE= "OBS_PRELIMINAR";
    public static final String KEY_DATE_INIT= "DATE_INIT_OBRA";
    public static final String KEY_OTRA_UBICACION = "OTRA_UBICACION";

    public static final String KEY_ID_DEPARTAMENT= "ID_DEPARTAMENT";
    public static final String KEY_COD_DEPARTAMENT= "COD_DEPARTAMENT";
    public static final String KEY_NAME_DEPARTAMENT= "NAME_DEPARTAMENT";

    public static final String KEY_ID_CITIE= "ID_CITIE";
    public static final String KEY_COD_CITIE= "COD_CITIE";
    public static final String KEY_NAME_CITIE= "NAME_CITIE";

    public static final String KEY_ID_SECTOR= "ID_SECTOR";
    public static final String KEY_COD_SECTOR= "COD_SECTOR";
    public static final String KEY_NAME_SECTOR= "NAME_SECTOR";

    public static final String KEY_ID_INSPECTOR= "ID_INSPECTOR";
    public static final String KEY_COD_INSPECTOR= "COD_INSPECTOR";
    public static final String KEY_NAME_INSPECTOR= "NAME_INSPECTOR";
    public static final String KEY_OTHER_INSPECTOR= "OTHER_INSPECTOR";

    public static final String KEY_ID_SPINNER_DETECCION_1= "ID_SPINNER_DETECCION_1";
    public static final String KEY_COD_SPINNER_DETECCION_1= "COD_SPINNER_DETECCION_1";
    public static final String KEY_NAME_SPINNER_DETECCION_1= "NAME_SPINNER_DETECCION_1";
    public static final String KEY_OTHER_SPINNER_DETECCION_1= "OTHER_SPINNER_DETECCION_1";


    public static final String KEY_ID_SPINNER_DETECCION_2= "ID_SPINNER_DETECCION_2";
    public static final String KEY_COD_SPINNER_DETECCION_2= "COD_SPINNER_DETECCION_2";
    public static final String KEY_NAME_SPINNER_DETECCION_2= "NAME_SPINNER_DETECCION_2";
    public static final String KEY_OTHER_SPINNER_DETECCION_2= "OTHER_SPINNER_DETECCION_2";

    public static final String KEY_ID_SPINNER_DETECCION_3= "ID_SPINNER_DETECCION_3";
    public static final String KEY_COD_SPINNER_DETECCION_3= "COD_SPINNER_DETECCION_3";
    public static final String KEY_NAME_SPINNER_DETECCION_3= "NAME_SPINNER_DETECCION_3";
    public static final String KEY_OTHER_SPINNER_DETECCION_3= "OTHER_SPINNER_DETECCION_3";

    public static final String KEY_ID_SPINNER_DETECCION_4= "ID_SPINNER_DETECCION_4";
    public static final String KEY_COD_SPINNER_DETECCION_4= "COD_SPINNER_DETECCION_4";
    public static final String KEY_NAME_SPINNER_DETECCION_4= "NAME_SPINNER_DETECCION_4";
    public static final String KEY_OTHER_SPINNER_DETECCION_4= "OTHER_SPINNER_DETECCION_4";

    public static final String KEY_ID_SPINNER_OBRA_1= "ID_SPINNER_OBRA_1";
    public static final String KEY_COD_SPINNER_OBRA_1= "COD_SPINNER_OBRA_1";
    public static final String KEY_NAME_SPINNER_OBRA_1= "NAME_SPINNER_OBRA_1";
    public static final String KEY_OTHER_SPINNER_OBRA_1= "OTHER_SPINNER_OBRA_1";

    public static final String KEY_ID_SPINNER_OBRA_2= "ID_SPINNER_OBRA_2";
    public static final String KEY_COD_SPINNER_OBRA_2= "COD_SPINNER_OBRA_2";
    public static final String KEY_NAME_SPINNER_OBRA_2= "NAME_SPINNER_OBRA_2";
    public static final String KEY_OTHER_SPINNER_OBRA_2= "OTHER_SPINNER_OBRA_2";

    public static final String KEY_ID_SPINNER_OBRA_3= "ID_SPINNER_OBRA_3";
    public static final String KEY_COD_SPINNER_OBRA_3= "COD_SPINNER_OBRA_3";
    public static final String KEY_NAME_SPINNER_OBRA_3= "NAME_SPINNER_OBRA_3";
    public static final String KEY_OTHER_SPINNER_OBRA_3= "OTHER_SPINNER_OBRA_3";

    public static final String KEY_ORDER_SERVICE= "ORDER_SERVICE";
    public static final String KEY_NUMBER_OS= "NUMBER_OS";
    public static final String KEY_LOCATION= "LOCATION";

    public static final String GRUPO_DEPARTAMENTO = "DEPARTAMENTO";
    public static final String GRUPO_SANEAMIENTO = "SANEAMIENTO";
    public static final String GRUPO_DIAMETRO_CNX = "DIAMETRO_CNX";
    public static final String GRUPO_MATERIAL_CLCTOR = "MATERIAL_CLCTOR";
    public static final String GRUPO_DIAMETRO_CLCTO = "DIAMETRO_CLCTO";
    public static final String GRUPO_PROFUNDIDAD_CLCT = "PROFUNDIDAD_CLCT";
    public static final String GRUPO_INTERFERENCIAS = "INTERFERENCIAS";
    public static final String GRUPO_INSP_SANEAMIENTO = "INSP_SANEAMIENTO";

    public static final String NAME_ITEM_SELECCIONE = "Seleccione";

    public static final String TXT_NAME_DETECCION_4 = "EDIT_TEXT_DETECCION_4";
    public static final String TXT_NRO_MEDIDOR = "TXT_NRO_MEDIDOR";
    public static final String EMPTY_VALUE = "";
    public static final String SQL_COMILLA = "'";
    public static final String SQL_EMPTY_VALUE = StringUtil.join(SQL_COMILLA, SQL_COMILLA);
    public static final String EMPTY_COORDENATE = "0,0";

    public static final String SI="SI";
    public static final String NO="NO";
    public static final String EN_PROCESO="EN_PROCESO";


    public static final String TABLA_SANEAMIENTO = "SANEAMIENTO";

    public static final String DIALOG_TITLE_CONFIRMACION="Confirmación";

    public static final String[] CATEGORIAS = { "Muy buena", "Buena", "Mediana", "Económica", "Muy económica" };
    public static final String[] ESTADOS = { "Excelente", "Bueno", "Regular", "Malo", "Muy malo" };
    public static final String[] DEPARTAMENTOS = { "Montevideo", "Canelones", "Florida", "Durazno", "Tucarembó", "Otros" };




}
