package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.services.ItemSpinnerService;

import static com.uy.csi.sige.tools.StringUtil.*;
import static com.uy.csi.sige.tools.ControlsUtil.*;


import java.util.List;

public class SpinnerUbicacionOnSelectListener implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "SpinUbicOnSelectList";

    private final Spinner spnChild;
    private final String lblSpinner;
    private final String nameSpinner;
    private final Integer valueChild;
    private ItemSpinnerService itemSpinnerService;
    private ControlsUtil spinnerUtil;
    private SharedPreferences preferences;
    private EditText otraSector;

    public SpinnerUbicacionOnSelectListener(final Spinner spnChild, final String lblSpinner, final Integer valueChild, final String nameSpinner, final ItemSpinnerService itemSpinnerService, final ControlsUtil spinnerUtil, final SharedPreferences preferences) {
        this.spnChild = spnChild;
        this.lblSpinner = lblSpinner;
        this.valueChild = valueChild;
        this.itemSpinnerService = itemSpinnerService;
        this.spinnerUtil = spinnerUtil;
        this.preferences = preferences;
        this.nameSpinner = nameSpinner;
    }

    public SpinnerUbicacionOnSelectListener(EditText otraSector, final Spinner spnChild, final String lblSpinner, final Integer valueChild, final String nameSpinner, final ItemSpinnerService itemSpinnerService, final ControlsUtil spinnerUtil, final SharedPreferences preferences) {
        this.spnChild = spnChild;
        this.lblSpinner = lblSpinner;
        this.valueChild = valueChild;
        this.itemSpinnerService = itemSpinnerService;
        this.spinnerUtil = spinnerUtil;
        this.preferences = preferences;
        this.nameSpinner = nameSpinner;
        this.otraSector = otraSector;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ResourceUtil ru = new ResourceUtil(preferences);
        ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);
        List<ItemSpinner> itemList = itemSpinnerService.loadItemSpinnerList(item.getIdItem(), ConstanteNumeric.TYPE_LOCATION, lblSpinner);

        preferences.edit().putInt(ConstanteText.NAME_ID + nameSpinner, item.getIdItem()).commit();
        preferences.edit().putString(ConstanteText.NAME_COD + nameSpinner, item.getCodigo()).commit();
        preferences.edit().putString(ConstanteText.NAME_NAME + nameSpinner, item.getDescription()).commit();

        try{
            spinnerUtil.loadSpinner(itemList, spnChild, valueChild);
            visibility( View.VISIBLE, spnChild );
        }catch(Exception e){
            Log.e(TAG, "onItemSelected: No se pudo seleccionar el Spinner " + spnChild, e);
        }

        if( !isEmpty(otraSector) &&
                in(item.getIdItem(), ConstanteNumeric.UBIC_OTRO_INDIC_NOMBRE, ConstanteNumeric.UBIC_MALDONADO) ){
            visibility(View.VISIBLE, otraSector);
            visibility( View.GONE, spnChild );
            ru.putInt( ConstanteText.KEY_ID_SECTOR, ConstanteNumeric.NO_EXITE );
            return;
        }

        if( !isEmpty(otraSector) ){
            clearTexts(otraSector);
            visibility(View.GONE, otraSector);
            ru.putStr(ConstanteText.KEY_OTRA_UBICACION, ConstanteText.EMPTY_VALUE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
