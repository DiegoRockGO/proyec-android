package com.uy.csi.sige.dao;

import android.content.Context;
import android.util.Log;
import com.uy.csi.sige.entity.User;
import com.uy.csi.sige.interfaces.UserDAO;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.Propiedades;

import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class UserDaoImpl  extends GenericDao<User> implements UserDAO{

    private static String TAG = "UserDaoImpl";

    private Context context;

    public UserDaoImpl(Context context){
        super( User.class, context, "User" );
        this.context=context;
    }

    @Override
    public int save(User usua) throws Exception {
        try{
            insert( usua );
        }catch(Exception e){
            Log.e(TAG, "save: ", e);
            return ConstanteNumeric.FAIL;
        }

        return ConstanteNumeric.SUCCESSFULL;
    }

    @Override
    public User loadUser(String nick, String psw, Integer session, Integer idusua) {

        try {

            Propiedades p = new Propiedades();

            if( !isEmpty( nick ) ){
                p.agregarPropiedadAnd( "nickName", nick, "=" );
            }

            if( !isEmpty( psw ) ){
                p.agregarPropiedadAnd( "pswd", psw, "=" );
            }

            if( !isEmpty( session ) ){
                p.agregarPropiedadAnd( "estado", session, "=" );
            }

            if( !isEmpty( idusua ) ){
                p.agregarPropiedadAnd( "id", idusua, "=" );
            }

            List<User> users = buscarPorPropiedades( p );

            if( !isEmpty( users ) ){
                return users.get(0);
            }

        } catch (Exception e) {
            Log.e(TAG, "loadUser: ", e);
        }

        return null;
    }

    @Override
    public int update(Integer idusua, Integer sesion) {

        Propiedades p = new Propiedades();
        p.agregarPropiedadAnd("id", idusua, "=");

        List<User> users = buscarPorPropiedades(p);
        if( !isEmpty(users) ){
            User user = users.get(0);
            user.setEstado( sesion );

            try {
                updateRows( user, p );
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Log.e(TAG, "update: ", e);
                return ConstanteNumeric.FAIL;
            }

        }

        return ConstanteNumeric.SUCCESSFULL;
    }
}
