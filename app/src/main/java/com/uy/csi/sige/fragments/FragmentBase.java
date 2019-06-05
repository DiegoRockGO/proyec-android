package com.uy.csi.sige.fragments;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.uy.csi.sige.entity.Configuracion;
import com.uy.csi.sige.tools.ClassCommons;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.StringUtil;
import com.uy.csi.sige.widget.HorizontalListView;

public class FragmentBase extends FragmentActivity {

    public static final String TAG = "FragmentBase";

    protected View view;

    private Class<?> idForm;

    public void setIdForm( Class<?> idForm ){ this.idForm = idForm; }

    public TextView getTextView( int id ){
        return (TextView) findViewById( id );
    }

    public Spinner getSpinner( int id ){ return (Spinner) findViewById( id ); }

    public String getTextValueIn( EditText editText ){
        return editText.getText().toString();
    }

    public Double getDoubleValueIn( EditText editText ){
        String valueText = editText.getText().toString();
        if( StringUtil.isEmpty( valueText ) ){
            return null;
        }

        return Double.parseDouble( valueText );
    }

    public Integer getIntegerValueIn( EditText editText ){
        String valueText = editText.getText().toString();
        if( StringUtil.isEmpty( valueText ) ){
            return null;
        }

        return Integer.parseInt( valueText );
    }

    public Integer getValueIn( Spinner spinner ){
        return ((Configuracion)spinner.getSelectedItem()).getKey();
    }

    public String getBooleanIn( Switch aSwitch ){
        if( aSwitch.isChecked() ){
            return ConstanteText.SI;
        }
        return ConstanteText.NO;
    }

    public Button getButton(String id ){
        return (Button) findViewById( (Integer)getObjectControl( id ) );
    }

    public View getView(String id ){
        return (View) findViewById( (Integer)getObjectControl( id ) );
    }

    public HorizontalListView getHorizontalListView(String id ){
        return (HorizontalListView) findViewById( (Integer)getObjectControl( id ) );
    }

    public ImageButton getImageButton(String id ){
        return (ImageButton) findViewById( (Integer)getObjectControl( id ) );
    }

    public Spinner getSpinner( String id ){
        return (Spinner) findViewById( (Integer)getObjectControl( id ) );
    }

    public LinearLayout getLinearLayout(String id ){
        return (LinearLayout) findViewById( (Integer)getObjectControl( id ) );
    }

    public EditText getEditText(String id ){
        return (EditText) findViewById( (Integer)getObjectControl( id ) );
    }

    public RadioGroup getRadioGroup(String id ){
        return (RadioGroup) findViewById( (Integer)getObjectControl( id ) );
    }

    public Switch getSwitch(String id ){
        return (Switch) findViewById( (Integer)getObjectControl( id ) );
    }

    public Object getObjectControl( String fieldName ){

        ClassCommons cc = new ClassCommons( idForm );
        try {
            return cc.getValueInStaticField( fieldName );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "getObjectControl: ", e);
        }

        return null;
    }

    public void setView(View view){
        this.view = view;
    }

    public void setComponents( View view, Class<?> idForm ){

        setView( view );
        setIdForm( idForm );

    }
}
