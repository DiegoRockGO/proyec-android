package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.services.ItemSpinnerService;

import java.util.List;

import static com.uy.csi.sige.tools.ControlsUtil.*;
import static com.uy.csi.sige.tools.StringUtil.*;


public class SpinnerDeteccion2OnSelectListener implements AdapterView.OnItemSelectedListener {


    private SharedPreferences preferences;
    private EditText txtDeteccion2;
    private EditText txtDeteccion4;
    private Spinner spnDeteccion3;
    private Resources res;
    private ControlsUtil spinnerUtil;
    private ItemSpinnerService itemSpinnerService;

    public SpinnerDeteccion2OnSelectListener(final Spinner spnDeteccion3,
                                             final EditText txtDeteccion2,
                                             final EditText txtDeteccion4,
                                             final SharedPreferences preferences,
                                             final Resources res,
                                             final ItemSpinnerService itemSpinnerService,
                                             final ControlsUtil spinnerUtil) {
        this.txtDeteccion2 = txtDeteccion2;
        this.preferences = preferences;
        this.spnDeteccion3 = spnDeteccion3;
        this.res = res;
        this.itemSpinnerService = itemSpinnerService;
        this.spinnerUtil = spinnerUtil;
        this.txtDeteccion4 = txtDeteccion4;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);
        preferences.edit().putInt(ConstanteText.NAME_ID + ConstanteText.NAME_SPINNER_DETECCION_2, item.getIdItem()).commit();
        preferences.edit().putString(ConstanteText.NAME_COD + ConstanteText.NAME_SPINNER_DETECCION_2, item.getCodigo()).commit();
        preferences.edit().putString(ConstanteText.NAME_NAME + ConstanteText.NAME_SPINNER_DETECCION_2, item.getDescription()).commit();

        visibility(View.GONE, txtDeteccion2, txtDeteccion4);
        visibility(View.GONE, spnDeteccion3);

        if (item.getIdItem() == ConstanteNumeric.DET_SELECT_FRAUDE_OTRO || item.getIdItem() == ConstanteNumeric.ID_OTHER_TYPE_DETECCION_3) {
            visibility(View.VISIBLE, txtDeteccion2);
            return;
        }

        String hint = item.getIdItem() > 0 ? res.getString(R.string.name_type_of) + " " + item.getDescription() : "--Seleccione--";
        List<ItemSpinner> itemList = itemSpinnerService.loadItemSpinnerList(item.getIdItem(), ConstanteNumeric.TYPE_DETECCION, hint);
        if( !isEmpty(itemList) && itemList.size() > 1 ){
            visibility(View.VISIBLE, spnDeteccion3);
            spinnerUtil.loadSpinner(itemList, spnDeteccion3, preferences.getInt(ConstanteText.KEY_ID_SPINNER_DETECCION_3, ConstanteNumeric.NO_EXITE));
        }

        clearTexts(txtDeteccion2);
        preferences.edit().putString(ConstanteText.NAME_OTHER + ConstanteText.NAME_SPINNER_DETECCION_2, "").commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
