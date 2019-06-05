package com.uy.csi.sige.interfaces;

import android.database.SQLException;

import com.uy.csi.sige.entity.DataSend;

import java.util.List;

/**
 * Created by USUARIO on 21/08/2016.
 */
public interface DataSendDAO {

    public Integer save(DataSend data)throws SQLException;
    public Integer delete(Integer id,Integer user);
    public List<DataSend> listDataSend(Integer user, Integer state);
    public Integer countDataSend(Integer user,Integer type);
}
