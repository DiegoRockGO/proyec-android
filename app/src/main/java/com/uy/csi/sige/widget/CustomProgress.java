package com.uy.csi.sige.widget;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.uy.csi.sige.R;

/**
 * Created by Denisse on 03/08/2016.
 */
public class CustomProgress extends DialogFragment {

    CircleProgressBar circleProgressBar;



    public void setArgs(String message, Integer idImgProgress ,Integer totalList,Integer nroActual,Boolean indeterminate ) {
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putInt("id_img_progress", idImgProgress);
        args.putInt("total_list", totalList);
        args.putInt("nro_actual", nroActual);
        args.putBoolean("indeterminate", indeterminate);
        setArguments(args);
    }



    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =LayoutInflater.from(getActivity());
        View viewDialog=inflater.inflate(R.layout.custom_progress, null);


        TextView txtMsj= (TextView) viewDialog.findViewById(R.id.txt_msj_progress);
        circleProgressBar = (CircleProgressBar) viewDialog.findViewById(R.id.custom_progressBar);
        String message = getArguments().getString("message");
        Boolean indeterminate = getArguments().getBoolean("indeterminate");
        txtMsj.setText(message);


        circleProgressBar.setProgressWithAnimation(100);
        //circleProgressBar.setIndeterminate(indeterminate);

        builder.setView(viewDialog);
        AlertDialog dialog=builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return dialog;
    }


    //public abstract void cancel();
    public void dismiss(){

        //circleProgressBar.stopAnimation();
        super.dismiss();

    }

}
