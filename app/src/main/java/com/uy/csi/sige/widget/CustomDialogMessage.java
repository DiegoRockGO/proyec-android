package com.uy.csi.sige.widget;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uy.csi.sige.R;

/**
 * Created by Denisse on 03/08/2016.
 */
public  abstract class CustomDialogMessage extends DialogFragment {



    public void setArgs(String title, String message,String namePositive ,String nameNegative ,Boolean showPositiveButton,Boolean showNegativeButton,Boolean showCentralButton,String nameCentral) {
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("title", title);
        args.putString("name_positive", namePositive);
        args.putString("name_negative", nameNegative);
        args.putBoolean("show_positive_button", showPositiveButton);
        args.putBoolean("show_negative_button", showNegativeButton);
        args.putBoolean("show_central_button", showCentralButton);
        args.putString("name_central", nameCentral);

        //args.putBoolean("show_line",showLine);
        setArguments(args);
    }


    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Resources res = getActivity().getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =LayoutInflater.from(getActivity());
        View viewDialog=inflater.inflate(R.layout.custom_dialog_message, null);

        TextView txtTitulo= (TextView) viewDialog.findViewById(R.id.txt_titulo_dialog);
        TextView txtMsj= (TextView) viewDialog.findViewById(R.id.txt_msj_dialog);
        Button buttonPositive= (Button) viewDialog.findViewById(R.id.btn_aceptar_pop_up);
        Button buttonNegative= (Button) viewDialog.findViewById(R.id.btn_close_pop_up);
        Button buttonCentral= (Button) viewDialog.findViewById(R.id.btn_central_pop_up);
        View viewLine=(View) viewDialog.findViewById(R.id.line_div_button);
        View viewLineCentral=(View) viewDialog.findViewById(R.id.line_div_button_central);

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String namePositive = getArguments().getString("name_positive");
        String nameNegative = getArguments().getString("name_negative");
        String nameCentral = getArguments().getString("name_central");
        Boolean showPositiveButton = getArguments().getBoolean("show_positive_button");
        Boolean showNegativeButton = getArguments().getBoolean("show_negative_button");
        Boolean showCentralButton = getArguments().getBoolean("show_central_button");


        txtTitulo.setText(title);
        txtMsj.setText(message);
        buttonPositive.setText(namePositive);
        buttonNegative.setText(nameNegative);
        buttonCentral.setText(nameCentral);

        if(showPositiveButton){
            buttonPositive.setVisibility(View.VISIBLE);
            buttonPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm();

                }
            });
        }else{
            buttonPositive.setVisibility(View.GONE);
        }

        if(showNegativeButton){
            viewLine.setVisibility(View.VISIBLE);
            buttonNegative.setVisibility(View.VISIBLE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                    // dismiss();

                }
            });
        }else {
            viewLine.setVisibility(View.GONE);
            buttonNegative.setVisibility(View.GONE);
        }


        if(showCentralButton){
            viewLineCentral.setVisibility(View.VISIBLE);
            buttonCentral.setVisibility(View.VISIBLE);
            buttonCentral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    central();


                }
            });
        }else {
            viewLineCentral.setVisibility(View.GONE);
            buttonCentral.setVisibility(View.GONE);
        }


        builder.setView(viewDialog);


        return builder.create();
    }



    public abstract void confirm();

    public abstract void cancel();

    public abstract void central();

}
