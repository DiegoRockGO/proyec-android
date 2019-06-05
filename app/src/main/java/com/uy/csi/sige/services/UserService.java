package com.uy.csi.sige.services;

import android.content.Context;

import com.uy.csi.sige.dao.UserDaoImpl;
import com.uy.csi.sige.entity.User;
import com.uy.csi.sige.interfaces.UserDAO;
import com.uy.csi.sige.tools.ConstanteNumeric;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class UserService {

    private UserDAO userDAO;

    public UserService(Context context){
        userDAO=new UserDaoImpl(context);
    }


    public int save(User usua) throws Exception{
        return userDAO.save(usua);
    }

    public User userLoggedIn(){
        return loadUser(null, null, ConstanteNumeric.OPEN, null);
    }

    public User loadUser(String nick, String psw,Integer session, Integer idusua){
        return userDAO.loadUser(nick, psw, session, idusua);
    }

    public int update(Integer idusua, Integer sesion ){
        return userDAO.update(idusua, sesion);
    }

}
