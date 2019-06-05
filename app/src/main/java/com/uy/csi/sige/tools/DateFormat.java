package com.uy.csi.sige.tools;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dtrinidad on 11/08/2016.
 */
public class DateFormat implements Serializable {

    public static final String FORMATO_DD_MM_AA_HHMMSS= "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_DD_MM_AA= "dd/MM/yyyy";


    public static String toString(String formato,Date fecha){
        try{
            System.out.println("fecha parsear=>"+fecha);
            if(fecha!=null ){
                SimpleDateFormat formatter = new SimpleDateFormat(formato);

                return formatter.format(fecha);
            }

            return "";
        }catch (Exception e) {
            return "";
        }

    }

    public static Date toDate(String formato,String fecha){
        try{

            if(fecha!=null  && fecha.length()>0){
                SimpleDateFormat formatter = new SimpleDateFormat(formato);
                return formatter.parse(fecha);
            }

            return new Date();
        }catch (Exception e) {
            return new Date();
        }

    }

}
