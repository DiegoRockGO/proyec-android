package com.uy.csi.sige.interfaces;

import com.uy.csi.sige.entity.Event;

import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public interface EventDAO {

    public int save(Event event) throws Exception;
    public int update(Event event);
    public List<Event> loadEventList(Integer idusua,Integer state,Integer type,Integer sincro);
    public int delete(Integer id,Integer idusua);
    public int existEvent(Integer web);
    public int updateState(Integer id,Integer state);
    void deleteDataUploaded();
}
