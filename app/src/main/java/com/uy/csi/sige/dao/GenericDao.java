package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.tools.ClassCommons;
import com.uy.csi.sige.tools.Propiedades;

import java.util.ArrayList;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

public class GenericDao<T> extends ClassCommons {

    private Class<T> clazz;

    private String[] fieldsName;
    private String[] camposBD;
    private String tableName;
    private String classname;
    private Context context;


    public GenericDao(Class<T> clazz, Context context, String classname){
        super(clazz);
        this.clazz = clazz;
        this.context = context;
        this.classname = classname;

        this.fieldsName = readAttributes();

        initCamposBD();
        initTableName();
    }

    public List<T> obtenerTodos() {
        Propiedades p = new Propiedades();
        return (List<T>) _buscarPorPropiedades(p);
    }

    public List<T> buscarPorPropiedades(Propiedades propiedades) {
        return (List<T>) _buscarPorPropiedades(propiedades);
    }

    private List<T> obtenerPorConsulta(String consulta) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        List<T> values = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor cursor = db.rawQuery( consulta, null);
        if( cursor.moveToFirst() ){
            do{
                T object = getObject( cursor );
                values.add( object );
            }while( cursor.moveToNext() );
        }
        cursor.close();
        db.close();

        return values;
    }

    private Object _buscarPorPropiedades(Propiedades propiedades) {

        String sql = String.format("SELECT %s FROM %s WHERE 1=1 ", arrayInComas(camposBD),  tableName);
        Object res = null;

        try {

            sql = join(sql, processWhere(propiedades));

            if( !propiedades.includeDeletes() ){
                sql = join(sql, "AND ESTADO!=3 \n");
            }

            Log.i(TAG, "_buscarPorPropiedades: " + sql);

            res = obtenerPorConsulta( sql );

        }catch(Exception e){
            Log.e(TAG, "_buscarPorPropiedades: ", e);
        }

        return res;
    }

    private ContentValues generateContentValues( Object obj ) throws NoSuchFieldException, IllegalAccessException {
        ContentValues valuesTable = new ContentValues();

        for( String fieldName : fieldsName ){

            Object fieldValue = getValueInField(obj, fieldName);
            Log.i(TAG, "generateContentValues gcv: " + fieldName + "\t" + fieldValue);

            if( isString(fieldValue) ){
                String data = toStr(fieldValue);
                if( !isEmpty( data ) ){
                    valuesTable.put( toTableName(fieldName), toStr(fieldValue) );
                }
                continue;
            }

            if( isInt(fieldValue) ){
                Integer data = toInt(fieldValue);
                if( !isEmpty( data ) ){
                    valuesTable.put( toTableName(fieldName), toInt(fieldValue) );
                }
                continue;
            }

            if( isDouble(fieldValue) ){
                Double data = toDouble( fieldValue );
                if( !isEmpty( data ) ){
                    valuesTable.put( toTableName(fieldName), toDouble(fieldValue) );
                }
                continue;
            }

//            valuesTable.putNull( toTableName(fieldName) );
        }

        return valuesTable;
    }

    public void insert( T obj ) throws NoSuchFieldException, IllegalAccessException {

        ContentValues valuesTable = generateContentValues( obj );
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        db.insert( tableName, null,  valuesTable);
        db.close();

    }

    public void updateRows( T obj, Propiedades prop ) throws NoSuchFieldException, IllegalAccessException {

        ContentValues valuesTable = generateContentValues( obj );
        String where = join("1=1 ", processWhere(prop) ) ;

        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        db.update( tableName, valuesTable, where, null );

        db.close();
    }

    public void execSQL( String sql ){
        Log.i(TAG, "execSQL: " + sql);
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        db.execSQL( sql );
        db.close();
    }

    private String processWhere( Propiedades prop){

        String sql = "";

        List<Propiedades.Propiedad> propiedadesAnd = prop.getListaPropiedadesAnd();
        sql = join(sql, processPropiedades(propiedadesAnd, "AND"));

        List<Propiedades.Propiedad> propiedadesOr = prop.getListPropertiesOr();
        sql = join(sql, processPropiedades(propiedadesOr, "OR"));

        return sql;
    }

    private String processPropiedades(List<Propiedades.Propiedad> propsAnd, String operator){

        StringBuilder sbSubQuery = new StringBuilder();
        if( !isEmpty( propsAnd ) ){

            sbSubQuery.append( join(operator, " (1=1 ") );

            for(Propiedades.Propiedad propAnd : propsAnd){

                sbSubQuery.append( join(" ", operator , " "))
                        .append( toTableName(propAnd.getCampo()) )
                        .append( propAnd.getOperador() );

                if( propAnd.getValor() instanceof String ){
                    sbSubQuery.append( join("'", toBlank(propAnd.getValor()), "'") );
                    continue;
                }
                sbSubQuery.append( toBlank(propAnd.getValor()) );

            }

            sbSubQuery.append( ") " );
        }

        return sbSubQuery.toString();
    }

    private T getObject(Cursor cursor) throws IllegalAccessException, InstantiationException, NoSuchFieldException {

        T object = clazz.newInstance();
        int columnsCount = cursor.getColumnCount();
        toObject(object, cursor, columnsCount);
        return object;

    }

    private void toObject(T object, Cursor cursor, int columnCount) throws NoSuchFieldException, IllegalAccessException {

        if( isEmpty( object ) ){
            return;
        }

        for( int i=0; i<columnCount; i++ ){
            if( cursor.getType( i ) == Cursor.FIELD_TYPE_INTEGER ){
                setValueInField( object, fieldsName[ i ], Integer.parseInt(cursor.getInt( i ) + "") );
                continue;
            }

            if( cursor.getType( i ) == Cursor.FIELD_TYPE_STRING ){
                setValueInField( object, fieldsName[ i ], cursor.getString( i ) );
                continue;
            }

            if( cursor.getType( i ) == Cursor.FIELD_TYPE_FLOAT ){
                // Parseando a double
                setValueInField( object, fieldsName[ i ], Double.parseDouble( cursor.getFloat( i ) + "" ) );
                continue;
            }
        }
    }

    public void initCamposBD(){
        int i = 0;
        camposBD = new String[ fieldsName.length ];

        for( String campoName : fieldsName ){
            camposBD[i++] = toTableName( campoName );
        }
    }

    public void initTableName(){
        tableName = toTableName( classname );
    }
}