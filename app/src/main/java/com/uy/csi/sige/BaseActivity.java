package com.uy.csi.sige;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    public EditText _getEditText(int id ){
        return (EditText) findViewById( id );
    }

    public TextView _getTextView(int id ){
        return (TextView) findViewById( id );
    }

    public Spinner _getSpinner(int id ){
        return (Spinner) findViewById( id );
    }

    public Button _getButton(int id ){
        return (Button) findViewById( id );
    }

}
