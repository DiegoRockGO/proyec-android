package com.uy.csi.sige.services;

import android.content.Context;

import com.uy.csi.sige.dao.ItemSpinnerDaoImpl;
import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.interfaces.ItemSpinnerDAO;

import java.util.List;

/**
 * Created by dtrinidad on 05/08/2016.
 */
public class ItemSpinnerService {

    private ItemSpinnerDAO itemSpinnerDAO;

    public ItemSpinnerService(Context context){
        itemSpinnerDAO=new ItemSpinnerDaoImpl(context);
    }

    public int save(ItemSpinner item)throws Exception{
       return itemSpinnerDAO.save(item);
    }
    public List<ItemSpinner> loadItemSpinnerList(Integer idfather,Integer type,String hint){
       return  itemSpinnerDAO.loadItemSpinnerList(idfather, type,hint);
    }

    public List<ItemSpinner> loadItemSpinnerList(Integer idfather,Integer type,String hint, int group){
        return  itemSpinnerDAO.loadItemSpinnerList(idfather, type,hint, group);
    }
}
