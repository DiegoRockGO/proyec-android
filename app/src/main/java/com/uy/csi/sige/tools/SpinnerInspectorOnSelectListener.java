package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.services.ItemSpinnerService;

import java.util.List;

public class SpinnerInspectorOnSelectListener implements AdapterView.OnItemSelectedListener {

    private final int idOther;
    private final EditText editText;
    private final String nameSpinner;
    private SharedPreferences preferences;

    public SpinnerInspectorOnSelectListener(final int idOther, final EditText editText, final String nameSpinner, final SharedPreferences preferences) {
        this.preferences = preferences;
        this.nameSpinner = nameSpinner;
        this.idOther = idOther;
        this.editText = editText;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);
        preferences.edit().putInt(ConstanteText.NAME_ID + nameSpinner, item.getIdItem()).commit();
        preferences.edit().putString(ConstanteText.NAME_COD + nameSpinner, item.getCodigo()).commit();
        preferences.edit().putString(ConstanteText.NAME_NAME + nameSpinner, item.getDescription()).commit();

        if (item.getIdItem() == idOther) {
            editText.setVisibility(View.VISIBLE);
            return;
        }

        editText.setVisibility(View.GONE);
        editText.setText("");
        preferences.edit().putString(ConstanteText.NAME_OTHER + nameSpinner, "").commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
