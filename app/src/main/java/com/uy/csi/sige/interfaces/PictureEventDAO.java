package com.uy.csi.sige.interfaces;

import com.uy.csi.sige.entity.PictureEvent;

import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public interface PictureEventDAO {

    public int save(PictureEvent picture) throws Exception;
    public int delete(Integer id,Integer idevent,Integer state);
    public List<PictureEvent> loadPictureList(Integer idevent,Integer state);
    public int update(Integer id,Integer state);
   // public int countPicture(Integer idevent,Integer state );
    public int countPictureDeteccion();
    public int countPictureObra();
}
