package com.uy.csi.sige.dto;

import android.content.Context;
import android.content.res.Resources;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uy.csi.sige.services.ItemSpinnerService;
import com.uy.csi.sige.tools.ControlsUtil;

public class FormEvento {

    protected TextView txtTituloFormulario;
    protected Context applicationContext;
    protected Resources resources;

    protected ItemSpinnerService itemSpinnerService;

    protected ControlsUtil spu;


    public FormEvento(Context applicationContext, Resources resources){
        this.resources = resources;
        this.applicationContext = applicationContext;
        this.itemSpinnerService = new ItemSpinnerService(applicationContext);
        this.spu = new ControlsUtil(applicationContext);
    }

    public TextView getTxtTituloFormulario() {
        return txtTituloFormulario;
    }

    public void setTxtTituloFormulario(TextView txtTituloFormulario) {
        this.txtTituloFormulario = txtTituloFormulario;
    }

}
