package com.uy.csi.sige.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.tools.StringUtil;

import java.util.HashMap;
import java.util.Map;

import static com.uy.csi.sige.tools.DbTools.*;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class DaoManager extends SQLiteOpenHelper {

    public static final String TAG = "DaoManager";

    public static final String DATA_BASE = "SIGE";
    public static final String DIRECTORY_DATA_BASE_APP = "/data/com.uy.csi.sige/databases/";
    public static final String DATA_BASE_DIRECTORY = "rank/database/";
    public static final int DATA_BASE_VERSION =1;

    public static final String TABLE_USER="USER";
    public static final String TABLE_PICTURE_TEMPORAL="PICTURE_TEMPORAL";
    public static final String TABLE_PICTURE_EVENT="PICTURE_EVENT";
    public static final String TABLE_EVENT="EVENT";
    public static final String TABLE_ITEM_SPINNER="ITEM_SPINNER";
    public static final String TABLE_DATA_SEND="DATA_SEND";


    public String SQL_USER = "CREATE TABLE IF NOT EXISTS USER(ID INTEGER PRIMARY KEY,NOMBRE TEXT, APELLIDOS  TEXT, NICKNAME TEXT, PASSWORD TEXT,ESTADO INTEGER)";
    public String SQL_PICTURE_TEMPORAL="CREATE TABLE IF NOT EXISTS PICTURE_TEMPORAL(ID_PCTR INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LATITUD TEXT, LONGITUD TEXT,DATE TEXT, DESCRIPTION TEXT,STATE INTEGER)";
    public static final String SQL_PICTURE_EVENT="CREATE TABLE IF NOT EXISTS PICTURE_EVENT(ID_PCTR INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LATITUD TEXT, LONGITUD TEXT,DATE TEXT, DESCRIPTION TEXT,ID_EVENT INTEGER,STATE INTEGER)";
    public static final String SQL_EVENT = StringUtil.join("CREATE TABLE IF NOT EXISTS EVENT( \n" ,
            "\t     ID_EVENT INTEGER PRIMARY KEY AUTOINCREMENT,\n",
            "\t     DATE TEXT,\n",
            "\t     ID_USER INTEGER,\n",
            "\t     LATITUD TEXT,\n",
            "\t     LONGITUD TEXT,\n",
            "\t     STATE INTEGER,\n",
            "\t     TYPE_EVENT INTEGER,\n",
            "\t     ID_DEPARTAMENT INTEGER,\n",
            "\t     COD_DEPARTAMENT TEXT,\n",
            "\t     NAME_DEPARTAMENT TEXT,\n",
            "\t     ID_CITIE INTEGER,\n",
            "\t     COD_CITIE TEXT,\n",
            "\t     NAME_CITIE TEXT,\n",
            "\t     ID_SECTOR INTEGER,\n",
            "\t     COD_SECTOR TEXT,\n",
            "\t     NAME_SECTOR TEXT,\n",
            "\t     ", Event.COL_NAME.OTRO_SECTOR, " TEXT,\n",
            "\t     ID_INSPECTOR INTEGER,\n",
            "\t     COD_INSPECTOR TEXT,\n",
            "\t     NAME_INSPECTOR TEXT,\n",
            "\t     OTHER_INSPECTOR TEXT,\n",
            "\t     STREE TEXT,\n",
            "\t     NUMBER TEXT,\n",
            "\t     REFERENCIA TEXT,\n",
            "\t     OBSERVATION TEXT,\n",
            "\t     NUMBER_PICTURE INTEGER,\n",
            "\t     ID_SPINNER_DTC1 INTEGER,\n",
            "\t     COD_SPINNER_DTC1 TEXT,\n",
            "\t     NAME_SPINNER_DTC1,\n",
            "\t     OTHER_SPINNER_DTC1 TEXT,\n",
            "\t     ID_SPINNER_DTC2 INTEGER,\n",
            "\t     COD_SPINNER_DTC2 TEXT,\n",
            "\t     NAME_SPINNER_DTC2 TEXT,\n",
            "\t     OTHER_SPINNER_DTC2 TEXT,\n",
            "\t     ID_SPINNER_DTC3 INTEGER,\n",
            "\t     COD_SPINNER_DTC3 TEXT,\n",
            "\t     NAME_SPINNER_DTC3 TEXT,\n",
            "\t     OTHER_SPINNER_DTC3 TEXT,\n",
            "\t     ", Event.COL_NAME.ID_SPINNER_DTC4, " INTEGER,\n",
            "\t     ", Event.COL_NAME.COD_SPINNER_DTC4, " TEXT,\n",
            "\t     ", Event.COL_NAME.NAME_SPINNER_DTC4, " TEXT,\n",
            "\t     ", Event.COL_NAME.OTHER_SPINNER_DET_4, " TEXT,\n",
            "\t     ORDER_SERVICE TEXT,\n",
            "\t     NUMBER_OS TEXT,\n",
            "\t     DATE_FIN TEXT,\n",
            "\t     OBS_PRELIMINAR TEXT,\n",
            "\t     ID_SPINNER_OBR1 INTEGER,\n",
            "\t     COD_SPINNER_OBR1 TEXT,\n",
            "\t     NAME_SPINNER_OBR1 TEXT,\n",
            "\t     OTHER_SPINNER_OBR1 TEXT,\n",
            "\t     ID_SPINNER_OBR2 INTEGER,\n",
            "\t     COD_SPINNER_OBR2 TEXT,\n",
            "\t     NAME_SPINNER_OBR2 TEXT,\n",
            "\t     OTHER_SPINNER_OBR2 TEXT,\n",
            "\t     ID_SPINNER_OBR3 INTEGER,\n",
            "\t     COD_SPINNER_OBR3 TEXT,\n",
            "\t     NAME_SPINNER_OBR3 TEXT,\n",
            "\t     OTHER_SPINNER_OBR3 TEXT,\n",
            "\t     LOCATION TEXT,\n",
            "\t     DATE_ADD TEXT,\n",
            "\t     ID_WEB INTEGER,\n",
            "\t     DATE_INIT_OBRA TEXT,\n",
            "\t     ", Event.COL_NAME.MEDIDOR, " TEXT\n",
            ")");
    public String SQL_ITEM_SPINNER = "CREATE TABLE IF NOT EXISTS ITEM_SPINNER(ID_ITEM INTEGER PRIMARY KEY,CODIGO TEXT, DESCRIPTION  TEXT, ID_FATHER INTEGER,TYPE INTEGER,STATE INTEGER, GROUP_COL INTEGER)";
    private  String SQL_DATA_SEND="CREATE TABLE IF NOT EXISTS DATA_SEND (ID_DATA INTEGER PRIMARY KEY AUTOINCREMENT,NOMBRE TEXT,STATE INTEGER,FECHA TEXT,ID_USER INTEGER,CONSTRAINT UNQ_NAME_PCTR UNIQUE (NOMBRE) )";



    private String SQL_TRIGGER_DELETE_EVENT=" CREATE TRIGGER IF NOT EXISTS TG_DELETE_EVENT BEFORE DELETE ON EVENT FOR EACH ROW BEGIN " +
             " DELETE  FROM PICTURE_EVENT WHERE ID_EVENT= OLD.ID_EVENT; " +
            " END ";


    // ACTUALIZACION DE BD 29072018
    private String ADDCOL_OTRA_DIRECCION = addColumnInTable(TABLE_EVENT, Event.COL_NAME.OTRO_SECTOR, "VARCHAR");
    private String ADDCOL_OTHER_SPINNER_DET_4 = addColumnInTable(TABLE_EVENT, Event.COL_NAME.OTHER_SPINNER_DET_4, "VARCHAR");
    private String UPDATE_LAS_PIEDRAS = "UPDATE ITEM_SPINNER SET DESCRIPTION='Las Piedras - La Paz' WHERE DESCRIPTION='La Piedra - La Paz'";


    public static final String SQL_CONSTRUCCION = "CREATE TABLE IF NOT EXISTS construccion (\n" +
            "\t id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t id_padron integer,\n" +
            "\t destino text,\n" +
            "\t nivel integer,\n" +
            "\t habitaciones integer,\n" +
            "\t servicios TEXT,\n" +
            "\t categoria integer,\n" +
            "\t categoria_str TEXT,\n" +
            "\t estado integer,\n" +
            "\t estado_str TEXT,\n" +
            "\t area_edif real,\n" +
            "\t anio integer,\n" +
            "\t cubierta TEXT,\n" +
            "\t estado_registro integer,\n" +
            "\t fecha_registro integer,\n" +
            "\t fecha_modificacion integer\n"+
            ")";

    public static final String SQL_PADRON = "CREATE TABLE IF NOT EXISTS padron (\n" +
            "\t id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t padron integer,\n" +
            "\t nroReferencia TEXT,\n" +
            "\t regimen TEXT,\n" +
            "\t area_terreno real,\n" +
            "\t departamento integer,\n" +
            "\t departamento_str TEXT,\n" +
            "\t area_edificada real,\n" +
            "\t localidad TEXT,\n" +
            "\t unidad TEXT,\n" +
            "\t direccion TEXT,\n" +
            "\t esquina TEXT,\n" +
            "\t vis_int_inm integer,\n" +
            "\t categoria integer,\n" +
            "\t categoria_str TEXT,\n" +
            "\t desc_categoria TEXT,\n" +
            "\t estado integer,\n" +
            "\t estado_str TEXT,\n" +
            "\t desc_estado TEXT,\n" +
            "\t reformas integer,\n" +
            "\t area_reforma real,\n" +
            "\t patologia integer,\n" +
            "\t humedades integer,\n" +
            "\t desc_humedades TEXT,\n" +
            "\t grietas integer,\n" +
            "\t desc_grietas TEXT,\n" +
            "\t inst_sanitaria integer,\n" +
            "\t inst_electrica integer,\n" +
            "\t observacion text,\n" +
            "\t usuario integer,\n" +
            "\t usuario_str integer,\n" +
            "\t fecha_registro integer,\n" +
            "\t fecha_modificacion integer,\n" +
            "\t estado_registro integer\n" +
            ")";

    public static final String QUERY_PADRON = "SELECT\n" +
            "\tid,\n" +
            "\tpadron,\n" +
            "\tnroReferencia,\n" +
            "\tregimen,\n" +
            "\tarea_terreno,\n" +
            "\tdepartamento,\n" +
            "\tdepartamento_str,\n" +
            "\tarea_edificada,\n" +
            "\tlocalidad,\n" +
            "\tunidad,\n" +
            "\tdireccion,\n" +
            "\tesquina,\n" +
            "\tvis_int_inm,\n" +
            "\tcategoria,\n" +
            "\tcategoria_str,\n" +
            "\tdesc_categoria,\n" +
            "\testado,\n" +
            "\testado_str,\n" +
            "\tdesc_estado,\n" +
            "\treformas,\n" +
            "\tarea_reforma,\n" +
            "\tpatologia,\n" +
            "\thumedades,\n" +
            "\tdesc_humedades,\n" +
            "\tgrietas,\n" +
            "\tdesc_grietas,\n" +
            "\tinst_sanitaria,\n" +
            "\tinst_electrica,\n" +
            "\tobservacion,\n" +
            "\tusuario,\n" +
            "\tusuario_str,\n" +
            "\tfecha_registro,\n" +
            "\tfecha_modificacion,\n" +
            "\testado_registro\n" +
            "FROM\n" +
            "\tpadron ";

    public static final String QUERY_CONSTRUCCION = "SELECT\n" +
            "\tid,\n" +
            "\tid_padron,\n" +
            "\tdestino,\n" +
            "\tnivel,\n" +
            "\thabitaciones,\n" +
            "\tservicios,\n" +
            "\tcategoria,\n" +
            "\tcategoria_str,\n" +
            "\testado,\n" +
            "\testado_str,\n" +
            "\tarea_edif,\n" +
            "\tanio,\n" +
            "\tcubierta,\n" +
            "\testado_registro,\n" +
            "\tfecha_registro,\n" +
            "\tfecha_modificacion\n" +
            "FROM\n" +
            "\tconstruccion ";

    public DaoManager(Context context) {
        super(context, DATA_BASE, null, DATA_BASE_VERSION);

    }

    public void createColumnsEvents_v3(){

        Log.i(TAG, "V3. Cambios Inicio");

        createColumnDDL( TABLE_EVENT, Event.COL_NAME.ID_SPINNER_DTC4, "INTEGER" );
        createColumnDDL( TABLE_EVENT, Event.COL_NAME.COD_SPINNER_DTC4, "TEXT" );
        createColumnDDL( TABLE_EVENT, Event.COL_NAME.NAME_SPINNER_DTC4, "TEXT" );
        createColumnDDL( TABLE_EVENT, Event.COL_NAME.MEDIDOR, "TEXT" );

        Log.i(TAG, "V3. Cambios Fin");
    }

    public void upgradeVersion3(){
        getWritableDatabase().execSQL(UPDATE_LAS_PIEDRAS);

        getWritableDatabase().execSQL( SQL_PADRON );
        getWritableDatabase().execSQL( SQL_CONSTRUCCION );
    }

    private void createColumnDDL(String tableName, String columnName, String typeColumn){
        try{
            getWritableDatabase().execSQL( addColumnInTable(tableName, columnName, typeColumn) );
            Log.i(TAG, StringUtil.join("\t creating ", columnName, "\t done..."));
        }catch(Exception e){
            Log.e(TAG, StringUtil.join("\t creating ", columnName, "\t fail..."));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USER);
        db.execSQL(SQL_PICTURE_TEMPORAL);
        db.execSQL(SQL_PICTURE_EVENT);
        db.execSQL(SQL_EVENT);
        db.execSQL(SQL_ITEM_SPINNER);
        db.execSQL(SQL_DATA_SEND);
        db.execSQL(SQL_TRIGGER_DELETE_EVENT);
        db.execSQL( SQL_PADRON );
        db.execSQL( SQL_CONSTRUCCION );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_USER);
        db.execSQL(SQL_PICTURE_TEMPORAL);
        db.execSQL(SQL_PICTURE_EVENT);
        db.execSQL(SQL_EVENT);
        db.execSQL(SQL_ITEM_SPINNER);
        db.execSQL(SQL_DATA_SEND);
        db.execSQL(SQL_TRIGGER_DELETE_EVENT);

        Log.i(TAG, "onCreate: Creando campos adicionales");
        db.execSQL(ADDCOL_OTRA_DIRECCION);
        db.execSQL(ADDCOL_OTHER_SPINNER_DET_4);

        db.execSQL( SQL_PADRON );
        db.execSQL( SQL_CONSTRUCCION );

        createColumnsEvents_v3();
        upgradeVersion3();
    }

    public void upgradeVersions(){

        try{
            Log.i(TAG, "onCreate: Cambio v1");
            getWritableDatabase().execSQL(ADDCOL_OTHER_SPINNER_DET_4);
            getWritableDatabase().execSQL(ADDCOL_OTRA_DIRECCION);
        }catch(Exception e){
            Log.e(TAG, "Err v1: Campos adicionales existen", e);
        }

        try{
            Log.i(TAG, "onCreate: Cambio v2");
            getWritableDatabase().execSQL(ADDCOL_OTHER_SPINNER_DET_4);
        }catch(Exception e){
            Log.e(TAG, "Err v2: Campos adicionales existen", e);
        }

        createColumnsEvents_v3();

        upgradeVersion3();

    }
}
