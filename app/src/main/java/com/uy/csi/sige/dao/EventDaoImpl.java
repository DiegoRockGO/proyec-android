package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.interfaces.EventDAO;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DateFormat;
import com.uy.csi.sige.tools.DbTools;
import static com.uy.csi.sige.tools.StringUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */

public class EventDaoImpl extends DbTools implements EventDAO{

    private Context context;

    public  EventDaoImpl(Context context){
        this.context=context;
    }

    @Override
    public int save(Event event) throws Exception {
        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues valores = new ContentValues();

            //valores.put("ID_EVENT", event.getIdEvent());
            valores.put("DATE", event.getDate());
            valores.put("STATE", ConstanteNumeric.ADD);
            valores.put("TYPE_EVENT",event.getTypeEvent());
            valores.put("ID_USER", event.getIdUser());
            valores.put("LATITUD", event.getLatitud());
            valores.put("LONGITUD", event.getLongitud());

            valores.put("ID_DEPARTAMENT", event.getIdDepartament()!=0?event.getIdDepartament():ConstanteNumeric.NO_EXITE);
            valores.put("COD_DEPARTAMENT", event.getCodDepartament());
            valores.put("NAME_DEPARTAMENT", event.getNameDepartament());

            valores.put("ID_CITIE", event.getIdCitie()!=0?event.getIdCitie():ConstanteNumeric.NO_EXITE);
            valores.put("COD_CITIE", event.getCodCitie());
            valores.put("NAME_CITIE", event.getNameCitie());

            valores.put("ID_SECTOR", event.getIdSector()!=0?event.getIdSector():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SECTOR", event.getCodSector());
            valores.put("NAME_SECTOR", event.getNameSector());

            valores.put("ID_INSPECTOR", event.getIdInspector()!=0?event.getIdInspector():ConstanteNumeric.NO_EXITE);
            valores.put("COD_INSPECTOR", event.getCodInspector());
            valores.put("NAME_INSPECTOR", event.getNameInspector());
            valores.put("OTHER_INSPECTOR", event.getOtherInspector());

            valores.put("STREE", event.getStree());
            valores.put("NUMBER", event.getNumber());
            valores.put("REFERENCIA", event.getReferencia());
            valores.put("OBSERVATION", event.getObservation());
            valores.put("NUMBER_PICTURE", event.getNumberPicture());

            valores.put("ID_SPINNER_DTC1", event.getIdSpinnerDtc1()!=0?event.getIdSpinnerDtc1():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SPINNER_DTC1", event.getCodSpinnerDtc1());
            valores.put("NAME_SPINNER_DTC1", event.getNameSpinnerDtc1());
            valores.put("OTHER_SPINNER_DTC1", event.getOtherSpinnerDtc1());

            valores.put("ID_SPINNER_DTC2", event.getIdSpinnerDtc2()!=0?event.getIdSpinnerDtc2():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SPINNER_DTC2", event.getCodSpinnerDtc2());
            valores.put("NAME_SPINNER_DTC2", event.getNameSpinnerDtc2());
            valores.put("OTHER_SPINNER_DTC2", event.getOtherSpinnerDtc2());

            valores.put("ID_SPINNER_DTC3", event.getIdSpinnerDtc3()!=0?event.getIdSpinnerDtc3():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SPINNER_DTC3", event.getCodSpinnerDtc3());
            valores.put("NAME_SPINNER_DTC3", event.getNameSpinnerDtc3());
            valores.put("OTHER_SPINNER_DTC3", event.getOtherSpinnerDtc3());

            valores.put("ORDER_SERVICE", event.getOrderService());
            valores.put("NUMBER_OS", event.getNumberOs());
            valores.put("DATE_FIN", event.getDateFin());
            valores.put("OBS_PRELIMINAR", event.getObsPreliminar());
            valores.put("LOCATION", event.getLocation());

            valores.put("ID_SPINNER_OBR1", event.getIdSpinnerObr1()!=0?event.getIdSpinnerObr1():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SPINNER_OBR1", event.getCodSpinnerObr1());
            valores.put("NAME_SPINNER_OBR1", event.getNameSpinnerObr1());
            valores.put("OTHER_SPINNER_OBR1", event.getOtherSpinnerObr1());

            valores.put("ID_SPINNER_OBR2", event.getIdSpinnerObr2()!=0?event.getIdSpinnerObr2():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SPINNER_OBR2", event.getCodSpinnerObr2());
            valores.put("NAME_SPINNER_OBR2", event.getNameSpinnerObr2());
            valores.put("OTHER_SPINNER_OBR2", event.getOtherSpinnerObr2());

            valores.put("ID_SPINNER_OBR3", event.getIdSpinnerObr3()!=0?event.getIdSpinnerObr3():ConstanteNumeric.NO_EXITE);
            valores.put("COD_SPINNER_OBR3", event.getCodSpinnerObr3());
            valores.put("NAME_SPINNER_OBR3", event.getNameSpinnerObr3());
            valores.put("OTHER_SPINNER_OBR3", event.getOtherSpinnerObr3());
            valores.put("DATE_ADD", DateFormat.toString(DateFormat.FORMATO_DD_MM_AA_HHMMSS, new Date()));
            valores.put("ID_WEB",event.getIdWeb());
            valores.put("DATE_INIT_OBRA",event.getDateInitObra());

            valores.put(Event.COL_NAME.OTRO_SECTOR, event.getOtraUbicacion());
            valores.put(Event.COL_NAME.ID_SPINNER_DTC4, event.getIdSpinnerDtc4());
            valores.put(Event.COL_NAME.COD_SPINNER_DTC4, event.getCodSpinnerDtc4());
            valores.put(Event.COL_NAME.NAME_SPINNER_DTC4, event.getNameSpinnerDtc4());
            valores.put(Event.COL_NAME.MEDIDOR, event.getMedidor());

            long pos = db.insert(DaoManager.TABLE_EVENT, null, valores);
            if (pos == -1)
                rows = 0;
            else
                rows = (int) pos;
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            rows = -2;
            e.printStackTrace();
            throw new Exception("DHR " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return rows;
    }

    @Override
    public int update(Event event) {
        int rows ;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {


            String where=" 1 = 1";


            List<String> paramsList =new ArrayList<>();

            ContentValues valores = new ContentValues();

            if(event.getIdEvent()>0  ){
                where+=" AND ID_EVENT = ?";
                paramsList.add(String.valueOf(event.getIdEvent()));
            }


            String [] params=new String[paramsList.size()];
            params = paramsList.toArray(params);

            valores.put("DATE", event.getDate());
            valores.put("STATE", event.getState());
            valores.put("OBSERVATION", event.getObservation());
            valores.put("NUMBER_PICTURE", event.getNumberPicture());

            if(event.getTypeEvent() == ConstanteNumeric.TYPE_DETECCION){
                valores.put("ID_DEPARTAMENT", event.getIdDepartament());
                valores.put("COD_DEPARTAMENT", event.getCodDepartament());
                valores.put("NAME_DEPARTAMENT", event.getNameDepartament());

                valores.put("ID_CITIE", event.getIdCitie());
                valores.put("COD_CITIE", event.getCodCitie());
                valores.put("NAME_CITIE", event.getNameCitie());

                valores.put("ID_SECTOR", event.getIdSector());
                valores.put("COD_SECTOR", event.getCodSector());
                valores.put("NAME_SECTOR", event.getNameSector());

                valores.put("ID_INSPECTOR", event.getIdInspector());
                valores.put("COD_INSPECTOR", event.getCodInspector());
                valores.put("NAME_INSPECTOR", event.getNameInspector());
                valores.put("OTHER_INSPECTOR", event.getOtherInspector());

                valores.put("STREE", event.getStree());
                valores.put("NUMBER", event.getNumber());
                valores.put("REFERENCIA", event.getReferencia());


                valores.put("ID_SPINNER_DTC1", event.getIdSpinnerDtc1());
                valores.put("COD_SPINNER_DTC1", event.getCodSpinnerDtc1());
                valores.put("NAME_SPINNER_DTC1", event.getNameSpinnerDtc1());
                valores.put("OTHER_SPINNER_DTC1", event.getOtherSpinnerDtc1());

                valores.put("ID_SPINNER_DTC2", event.getIdSpinnerDtc2());
                valores.put("COD_SPINNER_DTC2", event.getCodSpinnerDtc2());
                valores.put("NAME_SPINNER_DTC2", event.getNameSpinnerDtc2());
                valores.put("OTHER_SPINNER_DTC2", event.getOtherSpinnerDtc2());

                valores.put("ID_SPINNER_DTC3", event.getIdSpinnerDtc3());
                valores.put("COD_SPINNER_DTC3", event.getCodSpinnerDtc3());
                valores.put("NAME_SPINNER_DTC3", event.getNameSpinnerDtc3());
                valores.put("OTHER_SPINNER_DTC3", event.getOtherSpinnerDtc3());

                valores.put(Event.COL_NAME.OTRO_SECTOR, event.getOtraUbicacion());
                valores.put(Event.COL_NAME.OTHER_SPINNER_DET_4, event.getOtherSpinnerDet4());

                valores.put(Event.COL_NAME.ID_SPINNER_DTC4, event.getIdSpinnerDtc4());
                valores.put(Event.COL_NAME.COD_SPINNER_DTC4, event.getCodSpinnerDtc4());
                valores.put(Event.COL_NAME.NAME_SPINNER_DTC4, event.getNameSpinnerDtc4());
                valores.put(Event.COL_NAME.MEDIDOR, event.getMedidor());


            }else if(event.getTypeEvent() == ConstanteNumeric.TYPE_OBRA){

                valores.put("DATE_FIN", event.getDateFin());


                valores.put("ID_SPINNER_OBR1", event.getIdSpinnerObr1());
                valores.put("COD_SPINNER_OBR1", event.getCodSpinnerObr1());
                valores.put("NAME_SPINNER_OBR1", event.getNameSpinnerObr1());
                valores.put("OTHER_SPINNER_OBR1", event.getOtherSpinnerObr1());

                valores.put("ID_SPINNER_OBR2", event.getIdSpinnerObr2());
                valores.put("COD_SPINNER_OBR2", event.getCodSpinnerObr2());
                valores.put("NAME_SPINNER_OBR2", event.getNameSpinnerObr2());
                valores.put("OTHER_SPINNER_OBR2", event.getOtherSpinnerObr2());

                valores.put("ID_SPINNER_OBR3", event.getIdSpinnerObr3());
                valores.put("COD_SPINNER_OBR3", event.getCodSpinnerObr3());
                valores.put("NAME_SPINNER_OBR3", event.getNameSpinnerObr3());
                valores.put("OTHER_SPINNER_OBR3", event.getOtherSpinnerObr3());
                valores.put("DATE_INIT_OBRA", event.getDateInitObra());
                //DATE_INIT_OBRA

                if(event.getLatitud() !=null){
                    valores.put("LATITUD", event.getLatitud());

                }if(event.getLongitud() !=null){
                    valores.put("LONGITUD", event.getLongitud());
                }
            }


            long pos=db.update(DaoManager.TABLE_EVENT, valores, where, params);
            System.out.println("actualizando " + DaoManager.TABLE_EVENT + "  " + pos);
            if (pos == -1)
                rows = 0;
            else
                rows = (int) pos;
            db.close();
        } catch (Exception e) {
            rows = 0;
            e.printStackTrace();
        }finally {

            db.close();
        }
        return rows;
    }

    @Override
    public List<Event> loadEventList(Integer idusua,Integer state,Integer type,Integer sincro) {
        List<Event> eventList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {
            StringBuilder consulta=new StringBuilder("SELECT ID_EVENT,DATE ,ID_USER,LATITUD ,LONGITUD ,STATE ,TYPE_EVENT,  ");
            consulta.append(" ID_DEPARTAMENT,COD_DEPARTAMENT,NAME_DEPARTAMENT,ID_CITIE,COD_CITIE,NAME_CITIE,");
            consulta.append(" ID_SECTOR,COD_SECTOR,NAME_SECTOR,ID_INSPECTOR,COD_INSPECTOR,NAME_INSPECTOR,OTHER_INSPECTOR ,");
            consulta.append(" STREE,NUMBER,REFERENCIA,OBSERVATION,NUMBER_PICTURE,");
            consulta.append(" ID_SPINNER_DTC1,COD_SPINNER_DTC1,NAME_SPINNER_DTC1,OTHER_SPINNER_DTC1 ,");
            consulta.append(" ID_SPINNER_DTC2,COD_SPINNER_DTC2,NAME_SPINNER_DTC2,");
            consulta.append(" ID_SPINNER_DTC3,COD_SPINNER_DTC3,NAME_SPINNER_DTC3,");
            consulta.append(" ORDER_SERVICE,NUMBER_OS,");
            consulta.append(" DATE_FIN,OBS_PRELIMINAR,");
            consulta.append(" ID_SPINNER_OBR1,COD_SPINNER_OBR1,NAME_SPINNER_OBR1,OTHER_SPINNER_OBR1,");
            consulta.append(" ID_SPINNER_OBR2,COD_SPINNER_OBR2,NAME_SPINNER_OBR2,OTHER_SPINNER_OBR2, ");
            consulta.append(" ID_SPINNER_OBR3,COD_SPINNER_OBR3,NAME_SPINNER_OBR3,OTHER_SPINNER_OBR3,LOCATION,");
            consulta.append(" OTHER_SPINNER_DTC2 ,OTHER_SPINNER_DTC3,ID_WEB,DATE_INIT_OBRA, ");
            consulta.append( join(Event.COL_NAME.OTRO_SECTOR, ", ") );
            consulta.append( join(Event.COL_NAME.OTHER_SPINNER_DET_4, ", ") );
            consulta.append( join(Event.COL_NAME.ID_SPINNER_DTC4, ", ") );
            consulta.append( join(Event.COL_NAME.COD_SPINNER_DTC4, ", ") );
            consulta.append( join(Event.COL_NAME.NAME_SPINNER_DTC4, ", ") );
            consulta.append( join(Event.COL_NAME.MEDIDOR ) );

            consulta.append(" FROM EVENT WHERE 1 = 1");

            if(idusua !=null    ){
                consulta.append(" AND ID_USER = ").append(idusua);

            }  if(state !=null  ){
                consulta.append(" AND STATE = ").append(state);

            }if(type !=null  ){
                consulta.append(" AND TYPE_EVENT = ").append(type);

            }if(sincro!=null){
                if(type == ConstanteNumeric.TYPE_OBRA){
                    consulta.append(" AND DATE_FIN IS NOT NULL AND ifnull(length(DATE_FIN), 0) > 0") ;
                    consulta.append(" AND STATE NOT IN (").append(sincro).append(")");
                }else {
                    consulta.append(" AND STATE NOT IN (").append(sincro).append(")");
                }
            }

            System.out.println("SQL list EVENT=>"+consulta.toString());
            Cursor c = db.rawQuery(consulta.toString(), null);


            if (c.moveToFirst()) {
                do {
                    int id = !c.isNull(0) ? c.getInt(0) : 0;
                    String fch = !c.isNull(1) ? c.getString(1) : "";
                    int idusr = !c.isNull(2) ? c.getInt(2) : ConstanteNumeric.NO_EXITE;
                    String lat = !c.isNull(3) ? c.getString(3) : "0.0";
                    String lot = !c.isNull(4) ? c.getString(4) : "0.0";
                    int std = !c.isNull(5) ? c.getInt(5) : ConstanteNumeric.ADD;
                    int tp = !c.isNull(6) ? c.getInt(6) : ConstanteNumeric.TYPE_DETECCION;

                    int iddpt = !c.isNull(7) ? c.getInt(7) : ConstanteNumeric.NO_EXITE;
                    String coddpt = !c.isNull(8) ? c.getString(8) : "";
                    String nmdpt = !c.isNull(9) ? c.getString(9) : "";

                    int idcte = !c.isNull(10) ? c.getInt(10) : ConstanteNumeric.NO_EXITE;
                    String codcte = !c.isNull(11) ? c.getString(11) : "";
                    String nmcte = !c.isNull(12) ? c.getString(12) : "";

                    int idsctr = !c.isNull(13) ? c.getInt(13) : ConstanteNumeric.NO_EXITE;
                    String codsctr = !c.isNull(14) ? c.getString(14) : "";
                    String nmsctr = !c.isNull(15) ? c.getString(15) : "";

                    int idinspct = !c.isNull(16) ? c.getInt(16) : ConstanteNumeric.NO_EXITE;
                    String codinspct = !c.isNull(17) ? c.getString(17) : "";
                    String nminspct = !c.isNull(18) ? c.getString(18) : "";
                    String othrinspct = !c.isNull(19) ? c.getString(19) : "";

                    String strt = !c.isNull(20) ? c.getString(20) : "";
                    String nmbr = !c.isNull(21) ? c.getString(21) : "";
                    String rfrnc = !c.isNull(22) ? c.getString(22) : "";
                    String obs = !c.isNull(23) ? c.getString(23) : "";
                    int nmbpct = !c.isNull(24) ? c.getInt(24) : 0;

                    int iddtc1 = !c.isNull(25) ? c.getInt(25) : ConstanteNumeric.NO_EXITE;
                    String coddtc1 = !c.isNull(26) ? c.getString(26) : "";
                    String nmdtc1 = !c.isNull(27) ? c.getString(27) : "";
                    String othrdtc1 = !c.isNull(28) ? c.getString(28) : "";

                    int iddtc2 = !c.isNull(29) ? c.getInt(29) : ConstanteNumeric.NO_EXITE;
                    String coddtc2 = !c.isNull(30) ? c.getString(30) : "";
                    String nmdtc2 = !c.isNull(31) ? c.getString(31) : "";

                    int iddtc3 = !c.isNull(32) ? c.getInt(32) : ConstanteNumeric.NO_EXITE;
                    String coddtc3 = !c.isNull(33) ? c.getString(33) : "";
                    String nmdtc3 = !c.isNull(34) ? c.getString(34) : "";

                    String ordsrv = !c.isNull(35) ? c.getString(35) : "";
                    String nmsrv = !c.isNull(36) ? c.getString(36) : "";
                    String dtfin = !c.isNull(37) ? c.getString(37) : "";
                    String obspre = !c.isNull(38) ? c.getString(38) : "";

                    int idobr1 = !c.isNull(39) ? c.getInt(39) : ConstanteNumeric.NO_EXITE;
                    String codobr1 = !c.isNull(40) ? c.getString(40) : "";
                    String nmobr1 = !c.isNull(41) ? c.getString(41) : "";
                    String othrobr1 = !c.isNull(42) ? c.getString(42) : "";

                    int idobr2 = !c.isNull(43) ? c.getInt(43) : ConstanteNumeric.NO_EXITE;
                    String codobr2 = !c.isNull(44) ? c.getString(43) : "";
                    String nmobr2 = !c.isNull(45) ? c.getString(45) : "";
                    String othrobr2 = !c.isNull(46) ? c.getString(46) : "";

                    int idobr3 = !c.isNull(47) ? c.getInt(47) : ConstanteNumeric.NO_EXITE;
                    String codobr3 = !c.isNull(48) ? c.getString(48) : "";
                    String nmobr3 = !c.isNull(49) ? c.getString(49) : "";
                    String othrobr3 = !c.isNull(50) ? c.getString(50) : "";

                    String lctn = !c.isNull(51) ? c.getString(51) : "";
                    String othrdtc2 = !c.isNull(52) ? c.getString(52) : "";
                    String othrdtc3 = !c.isNull(53) ? c.getString(53) : "";
                    int idwb = !c.isNull(54) ? c.getInt(54) : ConstanteNumeric.NO_EXITE;
                    String fchInit = !c.isNull(55) ? c.getString(55) : "";


                    Event ev = new Event();
                    ev.setIdEvent(id);
                    ev.setDate(fch);
                    ev.setLatitud(lat);
                    ev.setLongitud(lot);
                    ev.setState(std);
                    ev.setTypeEvent(tp);
                    ev.setIdUser(idusr);

                    ev.setIdDepartament(iddpt);
                    ev.setCodDepartament(coddpt);
                    ev.setNameDepartament(nmdpt);
                    ev.setIdCitie(idcte);
                    ev.setCodCitie(codcte);
                    ev.setNameCitie(nmcte);
                    ev.setIdSector(idsctr);
                    ev.setCodSector(codsctr);
                    ev.setNameSector(nmsctr);
                    ev.setIdInspector(idinspct);
                    ev.setCodInspector(codinspct);
                    ev.setNameInspector(nminspct);
                    ev.setOtherInspector(othrinspct);
                    ev.setStree(strt);
                    ev.setNumber(nmbr);
                    ev.setReferencia(rfrnc);
                    ev.setObservation(obs);
                    ev.setNumberPicture(nmbpct);
                    ev.setIdSpinnerDtc1(iddtc1);
                    ev.setCodSpinnerDtc1(coddtc1);
                    ev.setNameSpinnerDtc1(nmdtc1);
                    ev.setOtherSpinnerDtc1(othrdtc1);
                    ev.setIdSpinnerDtc2(iddtc2);
                    ev.setCodSpinnerDtc2(coddtc2);
                    ev.setNameSpinnerDtc2(nmdtc2);
                    ev.setIdSpinnerDtc3(iddtc3);
                    ev.setCodSpinnerDtc3(coddtc3);
                    ev.setNameSpinnerDtc3(nmdtc3);
                    ev.setOrderService(ordsrv);
                    ev.setNumberOs(nmsrv);
                    ev.setDateFin(dtfin);
                    ev.setObsPreliminar(obspre);
                    ev.setIdSpinnerObr1(idobr1);
                    ev.setCodSpinnerObr1(codobr1);
                    ev.setNameSpinnerObr1(nmobr1);
                    ev.setOtherSpinnerObr1(othrobr1);
                    ev.setIdSpinnerObr2(idobr2);
                    ev.setCodSpinnerObr2(codobr2);
                    ev.setNameSpinnerObr2(nmobr2);
                    ev.setOtherSpinnerObr2(othrobr2);
                    ev.setIdSpinnerObr3(idobr3);
                    ev.setCodSpinnerObr3(codobr3);
                    ev.setNameSpinnerObr3(nmobr3);
                    ev.setOtherSpinnerObr3(othrobr3);
                    ev.setLocation(lctn);
                    ev.setOtherSpinnerDtc2(othrdtc2);
                    ev.setOtherSpinnerDtc3(othrdtc3);
                    ev.setIdWeb(idwb);
                    ev.setDateInitObra(fchInit);

                    ev.setOtraUbicacion(getStr(c, 56, ""));
                    ev.setOtherSpinnerDet4(getStr(c, 57, ""));

                    ev.setIdSpinnerDtc4(getInt(c, 58, ConstanteNumeric.NO_EXITE));
                    ev.setCodSpinnerDtc4(getStr(c, 59, ConstanteText.EMPTY_VALUE));
                    ev.setNameSpinnerDtc4(getStr(c, 60, ConstanteText.EMPTY_VALUE));
                    ev.setMedidor(getStr(c, 61, ConstanteText.EMPTY_VALUE));

                    ev.setPictureEventList(loadPictureList(ev.getIdEvent(), null));
                    System.out.println("Event date=>"+ev.getDate());
                    System.out.println("name inspector=>"+ev.getNameInspector());
                    eventList.add(ev);
                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
          e.printStackTrace();
        }finally {
            db.close();
        }
        return eventList;
    }


    @Override
    public int delete(Integer id, Integer idusua) {
        return 0;
    }

    @Override
    public int existEvent(Integer web) {
        int count=-1;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try{
            StringBuilder sql=new StringBuilder("SELECT ID_WEB FROM EVENT WHERE 1 = 1 ");

            if(web!=null){
                sql.append(" AND ID_WEB = ").append(web);
            }

            Cursor mCount= db.rawQuery(sql.toString(), null);
            mCount.moveToFirst();
            count= mCount.getInt(0);
            mCount.close();
            //db.close();
           // return count;


        }catch (Exception e){
            //e.printStackTrace();

        }finally {
            System.out.println("cerrando existEvent");
            db.close();
        }

        return count;
    }

    public List<PictureEvent> loadPictureList(Integer idevent, Integer state) {
        List<PictureEvent> pictureEventList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {
            StringBuilder consulta=new StringBuilder("SELECT ID_PCTR ,NAME ,LATITUD, LONGITUD ,DATE , DESCRIPTION,ID_EVENT,STATE  FROM PICTURE_EVENT WHERE 1 = 1 ");
            if(idevent !=null  && idevent>ConstanteNumeric.NO_EXITE  ){
                consulta.append(" AND ID_EVENT = ").append(idevent);

            }  if(state !=null  && state>ConstanteNumeric.NO_EXITE){
                consulta.append(" AND STATE = ").append(state);

            }

            Cursor c = db.rawQuery(consulta.toString(), null);


            if (c.moveToFirst()) {
                do {
                    int idFtoe = !c.isNull(0) ? c.getInt(0) : 0;
                    String nombreFtoe = !c.isNull(1) ? c.getString(1) : "";
                    String latitud = !c.isNull(2) ? c.getString(2) : "";
                    String longitud = !c.isNull(3) ? c.getString(3) : "";
                    String fecha = !c.isNull(4) ? c.getString(4) : "";
                    String desc = !c.isNull(5) ? c.getString(5) : " ";
                    int idev = !c.isNull(6) ? c.getInt(6) : ConstanteNumeric.NO_EXITE;
                    int std = !c.isNull(7) ? c.getInt(7) : ConstanteNumeric.OPEN;

                    PictureEvent  pt = new PictureEvent();

                    pt.setIdPctr(idFtoe);
                    pt.setName(nombreFtoe);
                    pt.setLatitud(latitud);
                    pt.setLongitud(longitud);
                    pt.setDate(fecha);
                    pt.setDescription(desc);
                    pt.setIdEvent(idev);
                    pt.setState(std);

                    pictureEventList.add(pt);
                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            db.close();
        }
        return pictureEventList;
    }

    @Override
    public int updateState(Integer id, Integer state) {
        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {

            StringBuilder sql=new StringBuilder(" UPDATE EVENT SET STATE = " + ConstanteNumeric.DATA_SENDING + " WHERE 1=1 ");
            sql.append(" AND TYPE_EVENT = " + ConstanteNumeric.TYPE_DETECCION);
            sql.append(" OR ID_EVENT IN (SELECT ID_EVENT FROM EVENT WHERE DATE_FIN IS NOT NULL AND ifnull(length(DATE_FIN), 0) > 0 ");
            sql.append(" AND TYPE_EVENT ="+ ConstanteNumeric.TYPE_OBRA + " )");

            db.beginTransaction();
            db.execSQL(sql.toString());
            rows = 1;
            db.setTransactionSuccessful();

        } catch (Exception e) {
            rows = -1;
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        System.out.println("row de ACTUALIZAR EVENT STATE::"+rows);
        return rows;
    }

    @Override
    public void deleteDataUploaded(){

        List<Integer> eventos = getIdEventsSending();
        if( !isEmpty(eventos) ){
            for( Integer eId : eventos ){
                removeFotosByEvent( eId );
            }
        }

        removeEventsSending();
    }


    private void removeFotosByEvent(Integer idEvent){
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {

            String consulta = String.format("DELETE FROM PICTURE_EVENT WHERE ID_EVENT=%d", idEvent);

            db.beginTransaction();
            db.execSQL(consulta);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private void removeEventsSending(){
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {

            String consulta = String.format("DELETE FROM EVENT WHERE STATE=%d", ConstanteNumeric.DATA_SENDING);

            db.beginTransaction();
            db.execSQL(consulta);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    private List<Integer> getIdEventsSending(){

        DaoManager dm = new DaoManager( context );
        List<Integer> idEventos = new ArrayList<>();
        SQLiteDatabase db = dm.getReadableDatabase();

        try{
            String sql = String.format("SELECT ID_EVENT FROM EVENT WHERE STATE=%d", ConstanteNumeric.DATA_SENDING);
            Cursor c = db.rawQuery(sql, null);

            if( c.moveToFirst() ){
                do{
                    idEventos.add(getInt(c, 0, -1));
                }while( c.moveToNext() );
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }

        return idEventos;
    }

}
