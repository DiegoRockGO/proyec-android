package com.uy.csi.sige.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.uy.csi.sige.FormEventActivity;
import com.uy.csi.sige.R;
import com.uy.csi.sige.entity.PictureTemporal;
import com.uy.csi.sige.fragments.FragmentSaneamiento;
import com.uy.csi.sige.image.LruCacheManager;
import com.uy.csi.sige.tools.StringUtil;
import com.uy.csi.sige.widget.HorizontalListView;

import java.util.List;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class MyHorizontalListViewAdapter extends BaseAdapter {

    private List<PictureTemporal> listPicture;

    private int resource;
    private LruCacheManager lruCacheManager;
    private LayoutInflater inflater  ;




    private FormEventActivity activity;
    private FragmentSaneamiento activitySaneamiento;

    private int iconPictureEmpty;


    public MyHorizontalListViewAdapter(Context context, int resource, List<PictureTemporal> listPicture,LruCacheManager lruCacheManager
            ,FormEventActivity activity,int iconPictureEmpty){
        inflater = LayoutInflater.from(context);

        this.resource = resource;
        this.listPicture = listPicture;
        this.lruCacheManager=lruCacheManager;
        this.activity=activity;
        this.iconPictureEmpty=iconPictureEmpty;

    }

    public MyHorizontalListViewAdapter(Context context, int resource, List<PictureTemporal> listPicture, LruCacheManager lruCacheManager
            , FragmentSaneamiento activity, int iconPictureEmpty){
        inflater = LayoutInflater.from(context);

        this.resource = resource;
        this.listPicture = listPicture;
        this.lruCacheManager=lruCacheManager;
        this.activitySaneamiento=activity;
        this.iconPictureEmpty=iconPictureEmpty;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        ViewHolder holder;

        if (view == null) {

            view = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.imgThumbnail = (ImageView) view.findViewById(R.id.img_thumbnail);
            holder.txtDescription = (TextView) view.findViewById(R.id.txt_description);
            holder.btnDelete = (ImageButton) view.findViewById(R.id.button_delete_picture);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        PictureTemporal fto = listPicture.get(position);
        final int idfoto = fto.getIdPctr();
        if (idfoto > 0) {
            try{
                Bitmap image=lruCacheManager.getBitmapFromMemCache(fto.getName());

                if(image != null) {
                    holder.imgThumbnail.setImageBitmap(image);
                }

                holder.txtDescription.setVisibility(View.VISIBLE);
                holder.txtDescription.setText(fto.getDescription());
                holder.btnDelete.setVisibility(View.VISIBLE);

                if( !StringUtil.isEmpty(activity) ){
                    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {activity.deletePicture(idfoto);

                        }
                    });
                }else{
                    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {activitySaneamiento.deletePicture(idfoto);

                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }catch (OutOfMemoryError e){
                Log.e("MyHorizontalList", "OutOfMemoryError");
                e.printStackTrace();
            }
        } else {
            Log.e("MyHorizontalList", "Imagen vacia");
            holder.imgThumbnail.setImageResource(iconPictureEmpty);
            holder.txtDescription.setVisibility(View.GONE);
            holder.txtDescription.setText("");
            holder.btnDelete.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public int getCount() {
        return   listPicture.size();
    }

    @Override
    public Object getItem(int position) {
        return (PictureTemporal) listPicture.get(position);

    }

    @Override
    public long getItemId(int position) {
        PictureTemporal pictureTemporal = listPicture.get(position);

        return pictureTemporal.getIdPctr();
    }

    private class ViewHolder {
        public ImageView imgThumbnail;
        public TextView txtDescription;
        public ImageButton btnDelete;
    }
}
