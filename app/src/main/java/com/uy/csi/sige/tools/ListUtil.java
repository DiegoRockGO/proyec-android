package com.uy.csi.sige.tools;

import java.util.ArrayList;
import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;

public class ListUtil<T> {

    public List<T> addInPosition(int position, T object, List<T> objects){

        List<T> valores = new ArrayList<>();

        if( !isEmpty(objects) ){

            for( int i=0; i<position ; i++ ){
                valores.add( objects.get(i) );
            }

            valores.add( object );

            for( int i=position; i<objects.size(); i++ ){
                valores.add( objects.get(i) );
            }

        }else{
            valores.add( object );
        }

        return valores;
    }

}
