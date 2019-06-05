package com.uy.csi.sige.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uy.csi.sige.db.DaoManager;
import com.uy.csi.sige.entity.ItemSpinner;

import com.uy.csi.sige.interfaces.ItemSpinnerDAO;
import com.uy.csi.sige.tools.ConstanteNumeric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dtrinidad on 05/08/2016.
 */
public class ItemSpinnerDaoImpl implements ItemSpinnerDAO {

    private Context context;

    public ItemSpinnerDaoImpl(Context context){
        this.context=context;
    }

    @Override
    public int save(ItemSpinner item) throws Exception {
        int rows = 0;
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues valores = new ContentValues();

            valores.put("ID_ITEM", item.getIdItem());
            valores.put("CODIGO", item.getCodigo());
            valores.put("DESCRIPTION", item.getDescription());
            valores.put("ID_FATHER",item.getIdFather());
            valores.put("TYPE", item.getType());
            valores.put("STATE", ConstanteNumeric.OPEN);
            valores.put("GROUP_COL", item.getGroupCol());


            long pos = db.insert(DaoManager.TABLE_ITEM_SPINNER, null, valores);
            System.out.println("REGISTRADO TABLE_ITEM_SPINNER " + pos);
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
    public List<ItemSpinner> loadItemSpinnerList(Integer idfather, Integer type,String hint) {
        List<ItemSpinner> itemSpinnerList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {
            StringBuilder consulta=new StringBuilder("SELECT ID_ITEM,CODIGO,DESCRIPTION,ID_FATHER,TYPE,STATE FROM ITEM_SPINNER WHERE 1 = 1 ");
            if(idfather !=null    ){
                consulta.append(" AND ID_FATHER = ").append(idfather);

            }  if(type !=null  ){
                consulta.append(" AND TYPE = ").append(type);

            }
            System.out.println("SQL ItemSpinner event ==>"+consulta.toString());
            Cursor c = db.rawQuery(consulta.toString(), null);
            ItemSpinner  it = new ItemSpinner();
            //it.setIdItem(ConstanteNumeric.HEADER_ITEM);
            it.setIdItem(ConstanteNumeric.NO_EXITE);
            it.setCodigo(ConstanteNumeric.NO_EXITE + "");
            it.setDescription(hint);
            itemSpinnerList.add(it);

            if (c.moveToFirst()) {
                do {
                    int id = !c.isNull(0) ? c.getInt(0) : 0;
                    String cod = !c.isNull(1) ? c.getString(1) : "";
                    String des = !c.isNull(2) ? c.getString(2) : "";
                    int idfth = !c.isNull(3) ? c.getInt(3) : ConstanteNumeric.NO_EXITE;
                    int tp = !c.isNull(4) ? c.getInt(4) : ConstanteNumeric.TYPE_LOCATION;
                    int std = !c.isNull(5) ? c.getInt(5) : ConstanteNumeric.OPEN;

                       it = new ItemSpinner();
                    it.setIdItem(id);
                    it.setCodigo(cod);
                    it.setDescription(des);
                    it.setIdFather(idfth);
                    it.setType(tp);
                    it.setState(std);

                    itemSpinnerList.add(it);
                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            db.close();
        }
        return itemSpinnerList;
    }

    @Override
    public List<ItemSpinner> loadItemSpinnerList(Integer idfather, Integer type, String hint, int group) {
        List<ItemSpinner> itemSpinnerList = new ArrayList<>();
        DaoManager usdbh = new DaoManager(context);
        SQLiteDatabase db = usdbh.getReadableDatabase();
        try {
            StringBuilder consulta=new StringBuilder("SELECT ID_ITEM,CODIGO,DESCRIPTION,ID_FATHER,TYPE,STATE FROM ITEM_SPINNER WHERE 1 = 1 ");
            if(idfather !=null    ){
                consulta.append(" AND ID_FATHER = ").append(idfather);

            }  if(type !=null  ){
                consulta.append(" AND TYPE = ").append(type);

            }

            consulta.append(" AND GROUP_COL=").append(group);

            System.out.println("SQL ItemSpinner event ==>"+consulta.toString());
            Cursor c = db.rawQuery(consulta.toString(), null);
            ItemSpinner  it = new ItemSpinner();
            //it.setIdItem(ConstanteNumeric.HEADER_ITEM);
            it.setIdItem(ConstanteNumeric.NO_EXITE);
            it.setCodigo(ConstanteNumeric.NO_EXITE + "");
            it.setDescription(hint);
            itemSpinnerList.add(it);

            if (c.moveToFirst()) {
                do {
                    int id = !c.isNull(0) ? c.getInt(0) : 0;
                    String cod = !c.isNull(1) ? c.getString(1) : "";
                    String des = !c.isNull(2) ? c.getString(2) : "";
                    int idfth = !c.isNull(3) ? c.getInt(3) : ConstanteNumeric.NO_EXITE;
                    int tp = !c.isNull(4) ? c.getInt(4) : ConstanteNumeric.TYPE_LOCATION;
                    int std = !c.isNull(5) ? c.getInt(5) : ConstanteNumeric.OPEN;

                    it = new ItemSpinner();
                    it.setIdItem(id);
                    it.setCodigo(cod);
                    it.setDescription(des);
                    it.setIdFather(idfth);
                    it.setType(tp);
                    it.setState(std);

                    itemSpinnerList.add(it);
                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            db.close();
        }
        return itemSpinnerList;
    }
}
