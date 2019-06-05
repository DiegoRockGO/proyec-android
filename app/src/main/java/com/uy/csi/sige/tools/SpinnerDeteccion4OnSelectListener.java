package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;

import com.uy.csi.sige.entity.ItemSpinner;

import static com.uy.csi.sige.tools.ControlsUtil.visibility;

public class SpinnerDeteccion4OnSelectListener implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SpnDet3OnSelectList";

    private SharedPreferences preferences;

    public SpinnerDeteccion4OnSelectListener(final SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ResourceUtil resource = new ResourceUtil(preferences);
        ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);

        resource.putInt( ConstanteText.KEY_ID_SPINNER_DETECCION_4, item.getIdItem());
        resource.putStr( ConstanteText.KEY_COD_SPINNER_DETECCION_4, item.getCodigo());
        resource.putStr( ConstanteText.KEY_NAME_SPINNER_DETECCION_4, item.getDescription());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
