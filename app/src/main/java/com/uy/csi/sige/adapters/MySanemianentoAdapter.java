package com.uy.csi.sige.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.entity.Saneamiento;
import com.uy.csi.sige.fragments.FragmentEvents;
import com.uy.csi.sige.fragments.FragmentSaneamientoList;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;

import java.util.List;


public class MySanemianentoAdapter extends ArrayAdapter<Saneamiento> {

    private List<Saneamiento> itemsData;
    private Context context;
    private int layoutResourceId;
    private ListView listView;


    private int initialx = 0;
    private int currentx = 0;

    private int padding = 0;
    private boolean mSwiping = false; // detects if user is swiping on ACTION_UP
    private boolean mItemPressed = false; // Detects if user is currently holding down a view
    private int paddingMin = 45;

    private FragmentSaneamientoList fragmentEvents;


    public MySanemianentoAdapter(Context context, int layoutResourceId, List<Saneamiento> itemsData, ListView listView, FragmentSaneamientoList fragmentEvents) {
        super(context, layoutResourceId, itemsData);
        this.itemsData = itemsData;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.listView = listView;
        this.fragmentEvents = fragmentEvents;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder holder;
        View row = convertView;


        if (row == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new NewsHolder();

            holder.viewEvent = (View) row.findViewById(R.id.view_event);
            holder.txtType = (TextView) row.findViewById(R.id.item_type_event);
            holder.txtFecha = (TextView) row.findViewById(R.id.item_date_event);
            holder.txtCant = (TextView) row.findViewById(R.id.item_nro);
            holder.txtOsNumber = (TextView) row.findViewById(R.id.item_os_number);


            row.setTag(holder);

        } else {
            holder = (NewsHolder) row.getTag();
        }

        final Saneamiento item = itemsData.get(position);
        final Integer id = item.getId();
//        final Integer type = item.getTypeEvent();
//        System.out.println("Tipo=>"+item.getTypeEvent());
//        holder.txtType.setText(type== ConstanteNumeric.TYPE_OBRA?context.getResources().getString(R.string.name_obra):context.getResources().getString(R.string.name_deteccion));
        holder.txtType.setText("SANEAMIENTO");
        holder.txtFecha.setText((!item.getFecha().isEmpty() ? item.getFecha() : "-"));
        holder.txtCant.setText(Archivo.appenCero(position + 1));


        holder.txtOsNumber.setVisibility(View.GONE);
        if (item.getEstado() == ConstanteNumeric.ESTADO_ACTIVO) {
            holder.viewEvent.setBackgroundResource(R.drawable.background_green_light);
        } else if (item.getEstado() == ConstanteNumeric.ESTADO_ENVIADO) {
            holder.viewEvent.setBackgroundResource(R.drawable.background_blue_light);
        } else {
            holder.viewEvent.setBackgroundResource(R.drawable.background_gradient_red);
        }

        row.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = MotionEventCompat.getActionMasked(event);
                int mSwipeSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();

                switch (action) {
                    case (MotionEvent.ACTION_DOWN): {
                        initialx = (int) event.getX();
                        if (mItemPressed) {
                            return false;
                        }
                        mItemPressed = true;


                    }
                    return true;

                    case (MotionEvent.ACTION_MOVE): {
                        currentx = (int) event.getX() + (int) v.getTranslationX();
                        padding = currentx - initialx;
                        float deltaXAbs = Math.abs(padding);

                        if (!mSwiping) {
                            if (deltaXAbs > mSwipeSlop) {
                                mSwiping = true;
                                listView.requestDisallowInterceptTouchEvent(true);
                            }
                        }

                    }

                    return true;
                    case (MotionEvent.ACTION_UP):
                        if (!mItemPressed) {


                        } else {

//                            if (item.getEstado() != ConstanteNumeric.DATA_SENDING) {
//                                fragmentEvents.loadFormEvent(context, ConstanteNumeric.UPDATE, ConstanteNumeric.UPDATE, item);
//                            } else {
//                                fragmentEvents.showMessage(context.getResources().getString(R.string.titulo_atencion), context.getResources().getString(R.string.msj_not_edit),
//                                        context.getResources().getString(R.string.name_button_aceptar), "", true, false, ConstanteNumeric.DLG_AVISO, null, context,
//                                        false, "", ConstanteText.NAME_FRAGMENT_DIALOG);
//
//                            }


                        }

                        mItemPressed = false;
                        listView.setEnabled(true);
                        if (mSwiping) {

                            mSwiping = false;

                        } else {

                            return false;
                        }


                    case (MotionEvent.ACTION_CANCEL): {
                        if (mItemPressed) {

                        }

                        mItemPressed = false;

                        v.setPadding(0, 0, 0, 0);

                    }
                    return true;

                    default:
                        return false;
                }
            }
        });

        return row;

    }


    public static class NewsHolder {

        public TextView txtFecha;
        public TextView txtCant;
        public TextView txtType;
        public TextView txtOsNumber;


        public View viewEvent;

    }


}
