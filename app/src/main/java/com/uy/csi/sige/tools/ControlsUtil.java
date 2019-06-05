package com.uy.csi.sige.tools;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.fragments.FragmentBase;

import java.lang.reflect.Field;
import java.util.List;
import static com.uy.csi.sige.tools.StringUtil.*;

public class ControlsUtil<T> {

    public static final String TAG = "ControlsUtil";

    private Context applicationContext;
    private FragmentBase fragmentBase;

    public ControlsUtil(Context applicationContext, FragmentBase fragmentBase) {
        this.applicationContext = applicationContext;
        this.fragmentBase = fragmentBase;
    }

    public ControlsUtil(Context applicationContext){
        this.applicationContext = applicationContext;
    }

    public ControlsUtil(FragmentBase fragmentBase){
        this.fragmentBase = fragmentBase;
    }

    public Object getObjectField( T object, String fieldName ) throws NoSuchFieldException, IllegalAccessException {
        Field f = getClass().getDeclaredField( fieldName );
        return f.get( object );
    }

    public void cargarSpinner(Spinner spinner, List<T> values, T selectedObject){

        int posicion = 0;
        if( isEmpty( spinner ) || isEmpty(values) ){
            return;
        }

        values = new ListUtil<T>().addInPosition(0,
                (T) ClassCommons.execStaticMethod("generateUnselectOption", selectedObject.getClass()),
                values );

        for( T value : values ){
            if( equiv( value, selectedObject ) ){
                break;
            }
            posicion++;
        }

        ArrayAdapter adapter = new ArrayAdapter( fragmentBase,
                android.R.layout.simple_spinner_dropdown_item,
                values);

        spinner.setAdapter( adapter );
        spinner.setSelection( posicion );
    }



    public void loadSpinner(List<ItemSpinner> lstValores, Spinner spinner, Integer value){
        int posicion = 0;
        if (lstValores != null && lstValores.size() > 0) {

            ArrayAdapter<ItemSpinner> adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.background_spinner, lstValores);

            adaptador.setDropDownViewResource(R.layout.spinner_row);
            spinner.setAdapter(adaptador);
            for (int i = 0; i < lstValores.size(); i++) {
                ItemSpinner vlr = (ItemSpinner) spinner.getItemAtPosition(i);
                if (vlr.getIdItem()== value) {
                    posicion = i;
                    break;
                }
            }
            spinner.setSelection(posicion);
        }
    }

    public static void visibility(int visibility, View... views){
        if(views == null){
            return;
        }

        for(View view: views){
            view.setVisibility(visibility);
        }
    }

    public static void clearTexts(EditText... texts){
        if( isEmpty(texts) ){
            return;
        }

        for(EditText et : texts){
            et.setText("");
        }
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
}
