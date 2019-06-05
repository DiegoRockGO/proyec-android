package com.uy.csi.sige.services;

import android.content.Context;

import com.uy.csi.sige.dao.EventDaoImpl;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.interfaces.EventDAO;

import java.util.List;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class EventService {

    private EventDAO eventDAO;

    public EventService(Context context){
        eventDAO=new EventDaoImpl(context);
    }

    public int save(Event event) throws Exception{
        return eventDAO.save(event);
    }
    public int update(Event event){
        return eventDAO.update(event);
    }
    public List<Event> loadEventList(Integer idusua,Integer state,Integer type,Integer sincro){
        return eventDAO.loadEventList(idusua, state, type, sincro);
    }

    public int delete(Integer id,Integer idusua){
        return eventDAO.delete(id, idusua);
    }

    public int existEvent(Integer web){return  eventDAO.existEvent(web);}

    public int updateState(Integer id,Integer state){
        return eventDAO.updateState(id, state);
    }


}
