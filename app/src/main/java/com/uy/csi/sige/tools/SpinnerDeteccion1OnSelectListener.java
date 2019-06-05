package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.uy.csi.sige.entity.ItemSpinner;
import com.uy.csi.sige.services.ItemSpinnerService;

import java.util.List;

import static com.uy.csi.sige.tools.StringUtil.*;
import static com.uy.csi.sige.tools.ConstanteNumeric.*;

public class SpinnerDeteccion1OnSelectListener implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "SpnDet1OnSelectList";

    private ItemSpinnerService itemSpinnerService;
    private ControlsUtil spinnerUtil;
    private SharedPreferences preferences;
    private Resources resources;

    private Spinner spDeteccion2;
    private Spinner spDeteccion3;
    private Spinner spDeteccion4;

    private EditText txtDeteccion1;
    private EditText txtDeteccion2;
    private EditText txtDeteccion3;
    private EditText txtMedidor;

    public SpinnerDeteccion1OnSelectListener(final Spinner spDeteccion2,
                                             final Spinner spDeteccion3,
                                             final Spinner spDeteccion4,
                                             final EditText txtDeteccion1,
                                             final EditText txtDeteccion2,
                                             final EditText txtDeteccion3,
                                             final EditText txtMedidor,
                                             final ItemSpinnerService itemSpinnerService,
                                             final ControlsUtil spinnerUtil,
                                             final SharedPreferences preferences,
                                             final Resources resources) {
        this.spDeteccion2 = spDeteccion2;
        this.spDeteccion3 = spDeteccion3;
        this.txtDeteccion1 = txtDeteccion1;
        this.txtDeteccion2 = txtDeteccion2;
        this.txtDeteccion3 = txtDeteccion3;
        this.itemSpinnerService = itemSpinnerService;
        this.spinnerUtil = spinnerUtil;
        this.preferences = preferences;
        this.resources = resources;
        this.spDeteccion4 = spDeteccion4;
        this.txtMedidor = txtMedidor;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ItemSpinner item = (ItemSpinner) parent.getItemAtPosition(position);

        Log.i(TAG, String.format("Codigo: %s", item.getCodigo()));
        Log.i(TAG, String.format("ID Item: %s", item.getIdItem()));

        preferences.edit().putInt(ConstanteText.NAME_ID + ConstanteText.NAME_SPINNER_DETECCION_1, item.getIdItem()).commit();
        preferences.edit().putString(ConstanteText.NAME_COD + ConstanteText.NAME_SPINNER_DETECCION_1, item.getCodigo()).commit();
        preferences.edit().putString(ConstanteText.NAME_NAME + ConstanteText.NAME_SPINNER_DETECCION_1, item.getDescription()).commit();

        ControlsUtil.visibility(View.GONE, spDeteccion2, spDeteccion3, spDeteccion4);
        ControlsUtil.visibility(View.GONE, txtDeteccion1, txtDeteccion2, txtDeteccion3, txtMedidor);

        if (item.getIdItem() == ConstanteNumeric.DET_OTRO) {
            ControlsUtil.visibility(View.VISIBLE, txtDeteccion1);
            return;
        }

        if (item.getIdItem() > 0) {

            String hint = getHint( item.getIdItem() );
            List<ItemSpinner> itemList = itemSpinnerService.loadItemSpinnerList(item.getIdItem(), ConstanteNumeric.TYPE_DETECCION, hint, GROUP_0);

            if (itemList != null && itemList.size() > 1) {
                spDeteccion2.setVisibility(View.VISIBLE);
                spinnerUtil.loadSpinner(itemList, spDeteccion2, preferences.getInt(ConstanteText.KEY_ID_SPINNER_DETECCION_2, ConstanteNumeric.NO_EXITE));
            }

            // Validando si es gran consumidor o conexión con medidor
            if( in(item.getIdItem(), DET_CNX_MEDIDOR, DET_GRAN_CSMDR) ){
                String hintLevel2 = getHintLevel2( item.getIdItem() );
                List<ItemSpinner> itemListLevel2 = itemSpinnerService.loadItemSpinnerList(item.getIdItem(), ConstanteNumeric.TYPE_DETECCION, hintLevel2, GROUP_1);

                if (itemListLevel2 != null && itemListLevel2.size() > 1) {
                    spDeteccion4.setVisibility(View.VISIBLE);
                    spinnerUtil.loadSpinner(itemListLevel2, spDeteccion4, preferences.getInt(ConstanteText.KEY_ID_SPINNER_DETECCION_4, ConstanteNumeric.NO_EXITE));
                }
            }

            if( in(item.getIdItem(), DET_FUGA, DET_FRAUDE) ){
                ControlsUtil.visibility(View.VISIBLE, txtMedidor);
                txtMedidor.setHint( "Medidor/es de referencia" );
                return;
            }

            if( in(item.getIdItem(), DET_CNX_MEDIDOR) ){
                ControlsUtil.visibility(View.VISIBLE, txtMedidor);
                txtMedidor.setHint( "N° de medidor" );
                return;
            }

        }

        txtDeteccion1.setText("");
        txtMedidor.setText("");
        preferences.edit().putString(ConstanteText.NAME_OTHER + ConstanteText.NAME_SPINNER_DETECCION_1, "").commit();
        preferences.edit().putString(ConstanteText.TXT_NRO_MEDIDOR, ConstanteText.EMPTY_VALUE).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getHint(int item){

        if( item == ConstanteNumeric.DET_FUGA ){
            return "Ubicación fuga";
        }

        if( item == ConstanteNumeric.DET_FRAUDE ){
            return "Tipo posible de fraude";
        }

        if( item == ConstanteNumeric.DET_CNX_MEDIDOR ){
            return "Material";
        }

        if( item == ConstanteNumeric.DET_GRAN_CSMDR ){
            return "Tipo de gran consumidor";
        }

        if( item == ConstanteNumeric.DET_ELMT_RED ){
            return "Tipos de elementos de red";
        }

        return " - seleccione - ";
    }

    public String getHintLevel2(int item){

        if( item == ConstanteNumeric.DET_CNX_MEDIDOR ){
            return "Diámetro";
        }

        if( item == ConstanteNumeric.DET_GRAN_CSMDR ){
            return "Diámetro conexión";
        }

        return " - seleccione - ";
    }
}
