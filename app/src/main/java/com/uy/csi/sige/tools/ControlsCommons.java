package com.uy.csi.sige.tools;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.uy.csi.sige.widget.HorizontalListView;

public class ControlsCommons {

    public static final String TAG = "RANK: ControlsCommons";

    private View view;
    private Class<?> formulario;
    private Activity activity;

    public ControlsCommons(Class<?> formulario, Activity activity) {
        this.formulario = formulario;
        this.activity = activity;
    }

    public static String _text( EditText editText ){
        return editText.getText().toString();
    }

    public static Integer _int( EditText editText ){
        String text = _text( editText );

        if( !StringUtil.isEmpty( text ) ){
            return Integer.parseInt( text );
        }

        return null;
    }

    public static Integer _int( Spinner view ){
        return view.getSelectedItemPosition();
    }

    public static Integer _int( Switch view ){

        if( view.isChecked() ){
            return 1;
        }

        return 0;
    }

    public static Double _dbl( EditText editText ){
        String text = _text( editText );

        if( !StringUtil.isEmpty( text ) ){
            return Double.parseDouble( text );
        }

        return null;
    }

    public static String _text( Spinner spn ){
        return spn.getSelectedItem().toString();
    }

    public static Integer _index( Spinner spn ){
        return spn.getSelectedItemPosition();
    }


    public ControlsCommons(View view, Class<?> formulario, Activity activity) {
        this.view = view;
        this.formulario = formulario;
        this.activity = activity;
    }

    public Button getButton(String id ){
        return (Button) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public ListView getListView(String id ){
        return (ListView) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public View getView(String id ){
        return (View) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public HorizontalListView getHorizontalListView(String id ){
        return (HorizontalListView) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public ImageButton getImageButton(String id ){
        return (ImageButton) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public Spinner getSpinner(String id ){
        return (Spinner) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public LinearLayout getLinearLayout(String id ){
        return (LinearLayout) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public EditText getEditText(String id ){
        return (EditText) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public TextView getTextView(String id ){
        return (TextView) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public RadioGroup getRadioGroup(String id ){
        return (RadioGroup) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public Switch getSwitch(String id ){
        return (Switch) activity.findViewById( (Integer)getObjectControl( id ) );
    }

    public Object getObjectControl( String fieldName ){

        ClassCommons cc = new ClassCommons( formulario );
        try {
            return cc.getValueInStaticField( fieldName );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "getObjectControl: ", e);
        }

        return null;
    }

}
