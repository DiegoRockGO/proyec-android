package com.uy.csi.sige.tools;

import com.uy.csi.sige.entity.Configuracion;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static final String TAG = "StringUtil";

    private static String MAYUSC[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static boolean startWith(String source, String findText){
        if( isEmpty(source) ){
            return false;
        }

        if( isEmpty(findText) ){
            return false;
        }

        return source.startsWith(findText);
    }

    public static boolean startsWith(String source, String... findTexts){

        if( isEmpty(findTexts) ){
            return false;
        }

        for( String findText: findTexts ){
            if( startWith(source, findText) ){
                return true;
            }
        }

        return false;
    }

    public static boolean isEmpty(Object obj){
        if(obj==null){
            return true;
        }

        if(obj instanceof String){
            return obj.toString().isEmpty();
        }

        if(obj instanceof List){
            return ((List) obj).isEmpty();
        }

        if(obj instanceof ArrayList){
            return ((ArrayList) obj).isEmpty();
        }

        return false;
    }

    public static boolean isEmpty(byte[] data){

        if(data==null){
            return true;
        }

        if(data.length==0){
            return true;
        }

        return false;
    }

    public static Integer toInt(Object object){
        return Integer.parseInt(toStr(object));
    }

    public static Double toDouble(Object object){
        return Double.parseDouble(toStr(object));
    }

    public static String toStr(Object obj){
        return toStr(obj, null);
    }

    public static String toBlank( Object obj ){
        if( isEmpty(obj) ){
            return "";
        }

        return obj.toString();
    }

    public static String toStr(Object obj, String defaultString){
        if( isEmpty(obj) ){
            return defaultString;
        }

        return obj.toString();
    }

    public static String join(String... args){
        if(args==null){
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for(String str: args){
            sb.append(str);
        }
        return sb.toString();
    }

    public static boolean in(int src, int... targets){
        for(Integer target : targets){
            if( src==target ){
                return true;
            }
        }

        return false;
    }

    public static boolean in(String src, String... targets){
        for(String target : targets){
            if( src.equals(target) ){
                return true;
            }
        }

        return false;
    }

    public static boolean equiv(Object source, Object target){

        if( isEmpty(source) && isEmpty(target) ){
            return true;
        }

        if( isEmpty(source) ){
            return false;
        }

        if( isEmpty(target) ){
            return false;
        }

        if( source instanceof String ){
            return source.toString().equals(target.toString());
        }

        if( source instanceof Integer ){
            return ((Integer) source).compareTo((Integer) target) == 0;
        }

        if( source instanceof Configuracion ){
            return ((Configuracion) source).equals((Configuracion) target);
        }

        return false;
    }

    public static String toTableName( String fieldName ){
        return toTableName( fieldName, 1);
    }

    private static String toTableName( String fieldName, int pos){
        if( pos == fieldName.length() ){
            return fieldName.toUpperCase();
        }

        String letter = String.valueOf( fieldName.charAt(pos++) );
        if( in(letter, MAYUSC) ){
            fieldName = fieldName.replace(letter, join("_", letter.toLowerCase()));
        }

        return toTableName( fieldName, pos );
    }

    public static String arrayInComas( String[] values){

        if(values==null){
            return "";
        }

        StringBuilder sb = new StringBuilder("");
        for( String value : values ){
            sb.append(",").append(value);
        }
        sb.append(" ");

        return sb.toString().substring(1);
    }
}
