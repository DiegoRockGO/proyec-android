package com.uy.csi.sige.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.Construccion;
import com.uy.csi.sige.entity.Event;
import com.uy.csi.sige.fragments.FragmentEvents;
import com.uy.csi.sige.tools.Archivo;
import com.uy.csi.sige.tools.ConstanteNumeric;
import com.uy.csi.sige.tools.ConstanteText;

import java.util.List;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class MyConstruccionAdapter extends ArrayAdapter<Construccion> {

    public static final String TAG = "RANK: MyConstAdapter";

    private List<Construccion> itemsData;
    private Context context;
    private int layoutResourceId;
    private ListView views;

    public MyConstruccionAdapter(Context context, int layoutResourceId, List<Construccion> itemsData, ListView views) {
        super(context, layoutResourceId, itemsData);
        this.itemsData = itemsData;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.views = views;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NewsHolder holder;
        View row = convertView;


        if (row == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new NewsHolder();

            holder.txtDestino = (TextView) row.findViewById(R.id.txtDestino);
            holder.txtEstado = (TextView) row.findViewById(R.id.txtEstado);
            holder.btnEliminarConstruccion = (Button) row.findViewById(R.id.btnEliminarConstruccion);

            row.setTag(holder);

        } else {
            holder = (NewsHolder) row.getTag();
        }

        final Construccion item = itemsData.get(position);
        
        holder.txtDestino.setText( item.getDestino() );
        holder.txtEstado.setText( item.getEstadoStr() );

        Log.i(TAG, "getView: Calling: " + itemsData.size() + " - position: " + position );

        return row;

    }

    @Override
    public int getCount() {
        return itemsData == null ? 0 : itemsData.size();
    }



    public static class NewsHolder {

        public TextView txtDestino;
        public TextView txtEstado;
        public Button btnEliminarConstruccion;

    }



    public void updateHeight(){
        ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams) views.getLayoutParams();
        listViewParams.height = 65 * itemsData.size();;
    }


}
