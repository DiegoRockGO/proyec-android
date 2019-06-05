package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.uy.csi.sige.entity.ItemSpinner;
import static com.uy.csi.sige.tools.ControlsUtil.*;
import static com.uy.csi.sige.tools.StringUtil.join;

public class SpinnerDeteccion3OnSelectListener implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SpnDet3OnSelectList";

    private SharedPreferences preferences;
    private EditText txtDeteccion4;

    public SpinnerDeteccion3OnSelectListener(final EditText txtDeteccion4, final SharedPreferences preferences) {
        this.preferences = preferences;
        this.txtDeteccion4 = txtDeteccion4;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ResourceUtil resource = new ResourceUtil(preferences);
        ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);

        resource.putInt( join(ConstanteText.NAME_ID, ConstanteText.NAME_SPINNER_DETECCION_3), item.getIdItem());
        resource.putStr( join(ConstanteText.NAME_COD, ConstanteText.NAME_SPINNER_DETECCION_3), item.getCodigo());
        resource.putStr( join(ConstanteText.NAME_NAME, ConstanteText.NAME_SPINNER_DETECCION_3), item.getDescription());

        visibility(View.GONE, txtDeteccion4);

        Log.i( TAG, join("Select3 ID: ", item.getIdItem() + "") );
        Log.i( TAG, join("Select3 CODIGO: ", item.getCodigo()) );
        Log.i( TAG, join("CONSTANT VALIDATION: ", ConstanteNumeric.DET_SELECT_CON_DOM_MED_OTRO + "") );

        if( item.getIdItem() == ConstanteNumeric.DET_SELECT_CON_DOM_MED_OTRO){
            visibility( View.VISIBLE, txtDeteccion4 );
            return;
        }

        clearTexts(txtDeteccion4);
        resource.putStr( ConstanteText.TXT_NAME_DETECCION_4, ConstanteText.EMPTY_VALUE );
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
