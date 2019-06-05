package com.uy.csi.sige.tools;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.uy.csi.sige.widget.CustomDialogMessage;

import static com.uy.csi.sige.tools.StringUtil.*;

public class DialogUtil {

    public static void showDialog( String msg,
                                   String nameFragmentModal,
                                   FragmentManager fragmentManager){

        final CustomDialogMessage dialog = new CustomDialogAdapter();
        dialog.setArgs( "Alerta",
                msg,
                "Aceptar",
                "",
                true,
                false,
                false,
                "" );
        showDialogFragment( dialog, nameFragmentModal, fragmentManager );
    }

    public static void showConfirm( String msg,
                                   String nameFragmentModal,
                                   FragmentManager fragmentManager,
                                   DialogActions dialogActions){

        final CustomDialogMessage dialog = new CustomDialogAdapter( dialogActions );
        dialog.setArgs( "Alerta",
                msg,
                "Aceptar",
                "Cancelar",
                true,
                true,
                false,
                "" );
        showDialogFragment( dialog, nameFragmentModal, fragmentManager );
    }

    public static void showDialog( String msg,
                                   String nameFragmentModal,
                                   FragmentManager fragmentManager,
                                   DialogActions dialogActions){

        final CustomDialogMessage dialog = new CustomDialogAdapter( dialogActions );
        dialog.setArgs( "Alerta",
                msg,
                "Aceptar",
                "",
                true,
                false,
                false,
                "" );
        showDialogFragment( dialog, nameFragmentModal, fragmentManager );
    }

    public static void showDialogFragment( DialogFragment newFragment,
                                           String nameFragmentModal,
                                           FragmentManager fragmentManager ) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag( nameFragmentModal );

        if ( !isEmpty(prev) ) { ft.remove(prev); }

        ft.addToBackStack( nameFragmentModal );
        newFragment.show( ft, nameFragmentModal );
    }


    @SuppressLint("ValidFragment")
    private static class CustomDialogAdapter extends CustomDialogMessage{

        DialogActions dialogActions;

        public CustomDialogAdapter(){}
        public CustomDialogAdapter( DialogActions dialogActions ){
            this.dialogActions = dialogActions;
        }

        @Override
        public void confirm() {
            dismiss();
            if(  !isEmpty( dialogActions ) ){
                dialogActions.confirm();
            }
        }

        @Override
        public void cancel() {
            dismiss();
        }

        @Override
        public void central() {}
    }
}
