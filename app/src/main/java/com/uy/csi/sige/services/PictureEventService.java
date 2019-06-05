package com.uy.csi.sige.services;

import android.content.Context;

import com.uy.csi.sige.dao.PictureEventDaoImpl;
import com.uy.csi.sige.entity.PictureEvent;
import com.uy.csi.sige.interfaces.PictureEventDAO;

import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class PictureEventService {

    private PictureEventDAO pictureEventDAO;

    public PictureEventService(Context context){
        pictureEventDAO=new PictureEventDaoImpl(context);
    }


    public int save(PictureEvent picture) throws Exception{
        return pictureEventDAO.save(picture);
    }
    public int delete(Integer id,Integer idevent,Integer state){
        return pictureEventDAO.delete(id, idevent, state);
    }
    public List<PictureEvent> loadPictureList(Integer idevent,Integer state){
        return pictureEventDAO.loadPictureList(idevent, state);
    }

    public int update(Integer id,Integer state){
        return  pictureEventDAO.update(id, state);
    }

   /* public int countPicture(Integer idevent,Integer state ){
        return pictureEventDAO.countPicture(idevent, state);
    }*/

    public int countPictureDeteccion(){
       return pictureEventDAO.countPictureDeteccion();
    }
    public int countPictureObra(){
        return pictureEventDAO.countPictureObra();
    }

}
