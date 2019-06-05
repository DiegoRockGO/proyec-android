package com.uy.csi.sige.interfaces;

import com.uy.csi.sige.entity.PictureTemporal;

import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public interface PictureTemporalDAO {

    public int save(PictureTemporal picture) throws Exception;
    public int update(Integer id,Integer state,String description);
    public int delete(Integer id,Integer state);
    public List<PictureTemporal> loadPictureList( Integer state);
}
