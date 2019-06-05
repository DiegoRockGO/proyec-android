package com.uy.csi.sige.tools;

import android.database.Cursor;
import android.util.Log;

import java.util.Map;
import static com.uy.csi.sige.tools.StringUtil.*;


public class DbTools {

    private static final String TAG = "DbTools";

    public static final String EMPTY_VALUE="''";
    public static final String NULL_VALUE="NULL";


    public static String addColumnsInTable(String tableName, Map<String, String> keyPair){

        if( isEmpty(keyPair) ){
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, String> key :keyPair.entrySet()){
            sb.append( addColumnInTable(tableName, key.getKey(), key.getValue()) );
            sb.append("; \n");
        }

        return sb.toString();

    }

    public static String addColumnInTable(String tableName, String columnName, String typeColumn, String defaultValue){
        return join("ALTER TABLE ", tableName, " ADD COLUMN ", columnName, " DEFAULT ", defaultValue);
    }

    public static String addColumnInTable(String tableName, String columnName, String typeColumn){
        return addColumnInTable(tableName, columnName, typeColumn, NULL_VALUE);
    }

    public static String getStr(Cursor cursor, int index, String defaultString){
        String valueReturn = defaultString;
        try{
            valueReturn = cursor.getString(index);
        }catch(Exception e){
            Log.e(TAG, "getStr: No se pudo obtener la cadena", e);
        }

        return valueReturn;
    }

    public static int getInt(Cursor cursor, int index, Integer defaultInteger){
        Integer valueReturn = defaultInteger;
        try{
            valueReturn = cursor.getInt(index);
        }catch(Exception e){
            Log.e(TAG, "getStr: No se pudo obtener el entero", e);
        }

        return valueReturn;
    }

    /**
     * Retorna la cadena del valor representado en una cadena SQL
     * @param value
     * @return
     */
    public static String getSQLStr(String value){
        if( isEmpty(value) ){
            return ConstanteText.SQL_EMPTY_VALUE;
        }

        return join(ConstanteText.SQL_COMILLA, value, ConstanteText.SQL_COMILLA);
    }

    /**
     * Retorna la representacion de un número en SQL
     * @param value
     * @return
     */
    public static String getSQLInt(int value){
        return value + "";
    }

    public static Long _long( Cursor c, int columnIndex ){

        if( c.isNull( columnIndex ) ){
            return null;
        }

        return c.getLong( columnIndex );

    }

    public static Integer _int( Cursor c, int columnIndex ){

        if( c.isNull( columnIndex ) ){
            return null;
        }

        return c.getInt( columnIndex );

    }

    public static Double _double( Cursor c, int columnIndex ){

        if( c.isNull( columnIndex ) ){
            return null;
        }

        return c.getDouble( columnIndex );
    }

    public static String _str( Cursor c, int columnIndex ){

        if( c.isNull( columnIndex ) ){
            return null;
        }

        return c.getString( columnIndex );
    }

}
