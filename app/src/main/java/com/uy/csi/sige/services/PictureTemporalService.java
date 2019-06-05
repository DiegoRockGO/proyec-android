package com.uy.csi.sige.services;

import android.content.Context;

import com.uy.csi.sige.dao.PictureTemporalDaoImpl;
import com.uy.csi.sige.entity.PictureTemporal;
import com.uy.csi.sige.interfaces.PictureTemporalDAO;

import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class PictureTemporalService {

    private PictureTemporalDAO pictureTemporalDAO;

    public PictureTemporalService(Context context){
        pictureTemporalDAO=new PictureTemporalDaoImpl(context);
    }


    public int save(PictureTemporal picture) throws Exception{
     return pictureTemporalDAO.save(picture);
    }
    public int update(Integer id,Integer state,String description){
        return pictureTemporalDAO.update(id, state, description);
    }
    public int delete(Integer id,Integer state){
        return pictureTemporalDAO.delete(id,state);
    }

    public List<PictureTemporal> loadPictureList( Integer state){
        return pictureTemporalDAO.loadPictureList(state);
    }

}
