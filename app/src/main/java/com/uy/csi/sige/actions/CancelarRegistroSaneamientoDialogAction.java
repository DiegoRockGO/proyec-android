package com.uy.csi.sige.actions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.uy.csi.sige.MenuMainActivity;
import com.uy.csi.sige.dao.SaneamientoDao;
import com.uy.csi.sige.dao.SaneamientoDaoImpl;
import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.fragments.FragmentSaneamiento;
import com.uy.csi.sige.fragments.FragmentSaneamientoList;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;
import com.uy.csi.sige.tools.DialogActions;

public class CancelarRegistroSaneamientoDialogAction implements DialogActions {

    private FragmentActivity fragmentActivity;

    public CancelarRegistroSaneamientoDialogAction(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public void confirm() {

        Intent intent = new Intent(fragmentActivity, MenuMainActivity.class);
        intent.putExtra(ConstanteText.KEY_ID_MENU, ConstanteNumeric.OPC_SANEAMIENTO);
        intent.putExtra(ConstanteText.KEY_POSITION_MENU, ConstanteNumeric.OPC_SANEAMIENTO);
        fragmentActivity.startActivity(intent);
        fragmentActivity.finish();
        fragmentActivity.overridePendingTransition(0, 0);

    }

    @Override
    public void cancel() {

    }

    @Override
    public void central() {

    }
}
