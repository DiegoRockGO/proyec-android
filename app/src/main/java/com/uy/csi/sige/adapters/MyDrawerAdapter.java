package com.uy.csi.sige.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.DrawerItem;

import java.util.List;

/**
 * Created by Denisse on 04/08/2016.
 */
public class MyDrawerAdapter  extends ArrayAdapter<DrawerItem> {

private Context context;
private int layoutResourceId;
private List<DrawerItem> listaItem;

public MyDrawerAdapter(Context context, int layoutResourceId, List<DrawerItem> listaItem) {
        super(context, layoutResourceId,listaItem);
        this.context=context;
        this.layoutResourceId=layoutResourceId;
        this.listaItem=listaItem;

        }

public long getItemId(int position) {
        return listaItem.get(position).getIdOpcion();
        }


@Override
public View getView(int position, View convertView, ViewGroup parent) {

        ItemDrawerHolder holder  ;
        View row = convertView;


        if(row == null)  {

        LayoutInflater inflater= LayoutInflater.from(context);
        row = inflater.inflate(layoutResourceId, parent, false);
        holder = new ItemDrawerHolder();

        holder.txtOpcion = (TextView)row.findViewById(R.id.txt_opcion_drawer);
        holder.imgIcon=(ImageView)row.findViewById(R.id.img_icon_drawer);



        row.setTag(holder);
        }  else {
        holder = (ItemDrawerHolder)row.getTag();
        }



        DrawerItem item = listaItem.get(position);


        holder.txtOpcion.setText(item.getNameOpcion());
        holder.imgIcon.setImageResource(item.getImgResId());

        if(item.isSelected()){
        row.setBackgroundResource(R.drawable.background_gradient_red);
        }else{
        row.setBackgroundResource(R.drawable.background_transparent_sin_margen);
        }



        return row;

        }

public static class ItemDrawerHolder   {


    public TextView txtOpcion;
    public ImageView imgIcon;

}
}
