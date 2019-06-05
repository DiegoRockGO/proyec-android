package com.uy.csi.sige.interfaces;

import com.uy.csi.sige.entity.ItemSpinner;

import java.util.List;

/**
 * Created by dtrinidad on 05/08/2016.
 */
public interface ItemSpinnerDAO {

    public int save(ItemSpinner item)throws Exception;
    public List<ItemSpinner> loadItemSpinnerList(Integer idfather,Integer type,String hint);
    public List<ItemSpinner> loadItemSpinnerList(Integer idfather,Integer type,String hint, int group);



}
