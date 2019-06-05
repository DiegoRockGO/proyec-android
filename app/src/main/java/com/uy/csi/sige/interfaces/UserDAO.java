package com.uy.csi.sige.interfaces;

import com.uy.csi.sige.entity.User;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public interface UserDAO {

    int save(User usua) throws Exception;
    User loadUser(String nick, String psw,Integer session, Integer idusua);
    int update(Integer idusua, Integer sesion );
}