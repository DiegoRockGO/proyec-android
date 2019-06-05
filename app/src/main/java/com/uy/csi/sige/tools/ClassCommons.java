package com.uy.csi.sige.tools;

import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.uy.csi.sige.tools.StringUtil.*;

public class ClassCommons {

    public static final String TAG = "ClassCommons";

    private Class<?> clazz;
    private String[] exceptionsName = {"$change", "serialVersionUID"};

    public ClassCommons(Class<?> clazz){
        this.clazz = clazz;
    }


    public static Object execStaticMethod(String method, Class<?> innerClazz){

        try {
            Method methodExec = innerClazz.getMethod(method, null);
            return methodExec.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Log.e(TAG, "execMethod: ", e);
        }

        return null;
    }

    public Object toObject( JSONObject jsonObject ) throws IllegalAccessException, InstantiationException, NoSuchFieldException {

        if( isEmpty(jsonObject) ){
            return null;
        }

        String[] fieldsName = readAttributes();
        Object obj = clazz.newInstance();

        for( String fieldName: fieldsName ){

            if( isInt(fieldName) ){
                setValueInField( obj, fieldName, JSONCommons.getInt( jsonObject, fieldName ) );
                continue;
            }

            if( isString(fieldName) ){
                setValueInField( obj, fieldName, JSONCommons.getString( jsonObject, fieldName ) );
                continue;
            }

            if( isDouble(fieldName) ){
                setValueInField( obj, fieldName, JSONCommons.getDouble( jsonObject, fieldName ) );
                continue;
            }

            if( isBoolean(fieldName) ){
                setValueInField( obj, fieldName, JSONCommons.getBoolean( jsonObject, fieldName ) );
                continue;
            }
        }

        return obj;

    }

    public boolean isInt(String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField( fieldName );
        return field.getType().isAssignableFrom( Integer.class );
    }

    public boolean isString(String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField( fieldName );
        return field.getType().isAssignableFrom( String.class );
    }

    public boolean isBoolean(String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField( fieldName );
        return field.getType().isAssignableFrom( Boolean.class );
    }

    public boolean isDouble(String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField( fieldName );
        return field.getType().isAssignableFrom( Double.class );
    }


    public String[] readAttributes(){
        Field[] fields = clazz.getDeclaredFields();
//        String[] fieldsName = new String[ fields.length - 2 ];
        String[] fieldsName = new String[ fields.length ];

        int i=0;
        for( Field field : fields ){
            if( !in(field.getName(), exceptionsName) ){
                fieldsName[i++] = field.getName();
            }
        }

        String[] fieldsResult = new String[ i ];
        for( int j=0; j<i; j++ ){
            fieldsResult[j] = fieldsName[j];
        }

        return fieldsResult;
    }

    public void setValueInField( Object object, String fieldName, Object value ) throws NoSuchFieldException, IllegalAccessException {

        Field declaredField = clazz.getDeclaredField(fieldName);
        boolean accessible = declaredField.isAccessible();
        declaredField.setAccessible( true );

        declaredField.set( object, value );
        declaredField.setAccessible( accessible );
    }

    public Object getValueInField( Object object, String fieldName ) throws NoSuchFieldException, IllegalAccessException {

        Field declaredField = clazz.getDeclaredField(fieldName);
        boolean accessible = declaredField.isAccessible();
        declaredField.setAccessible( true );
        Object valueReturn = declaredField.get(object);
        declaredField.setAccessible( accessible );
        return valueReturn;

    }

    public Object getValueInStaticField( String fieldName ) throws NoSuchFieldException, IllegalAccessException {
        return getValueInField( null, fieldName );
    }


    public boolean isInt(Object obj) throws NoSuchFieldException {
        if( isEmpty(obj) ){
            return false;
        }
        return obj instanceof Integer;
    }

    public boolean isString(Object obj) throws NoSuchFieldException {
        if( isEmpty(obj) ){
            return false;
        }
        return obj instanceof String;
    }

    public boolean isDouble(Object obj) throws NoSuchFieldException {
        if( isEmpty(obj) ){
            return false;
        }
        return obj instanceof Double;
    }

    private String getClassName( Field field ){
        return field.getType().getClass().getName();
    }

}
