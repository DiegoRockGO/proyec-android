package com.uy.csi.sige.services;

import android.content.Context;
import android.database.SQLException;

import com.uy.csi.sige.dao.DataSendDaoImpl;
import com.uy.csi.sige.entity.DataSend;
import com.uy.csi.sige.interfaces.DataSendDAO;

import java.util.List;

/**
 * Created by USUARIO on 21/08/2016.
 */
public class DataSendService {

    private DataSendDAO dataSendDAO;

    public DataSendService(Context context){
        dataSendDAO=new DataSendDaoImpl(context);
    }

    public Integer save(DataSend data) throws SQLException {
        return dataSendDAO.save(data);
    }
    public Integer delete(Integer id,Integer user){
        return dataSendDAO.delete(id,user);
    }

    public List<DataSend> listDataSend(Integer user, Integer state){
        return dataSendDAO.listDataSend(user, state);
    }

    public Integer countDataSend(Integer user,Integer type){
        return  dataSendDAO.countDataSend(user,type);
    }
}
