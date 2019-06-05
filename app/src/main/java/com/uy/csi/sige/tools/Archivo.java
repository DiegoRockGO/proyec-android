package com.uy.csi.sige.tools;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.uy.csi.sige.db.DaoManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class Archivo implements Serializable{

    public static void eliminarArchivo(String path) {
        File file = new File(path);
        if (file.exists()) {
            eliminarArchivo(file);
        }
    }

    public static boolean eliminarArchivo(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                eliminarArchivo(files[i]);
            }
        } else {
            return file.delete();
        }
        return file.delete();
    }

    public static String pathStorage(){
        String pathStorage= Environment.getExternalStorageDirectory().getAbsolutePath();


        return pathStorage;
    }

    public static File createFileSystem(String name){

        File exportDir = new File(pathStorage() ,name);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        System.out.println("***************CREANDO FILE EN ******************"+exportDir.getAbsolutePath());
        return exportDir;
    }

    public static Double getScreenSize(WindowManager windowManager, SharedPreferences sharedPreferences){
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        sharedPreferences.edit().putInt("SCREEN_WIDTH",width).commit();
        sharedPreferences.edit().putInt("SCREEN_HEIGHT",height).commit();

        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x + y);

        return screenInches;
    }

    public static String appenCero(int number){
        if(number>0 && number<10){
            return "00"+number;
        }else if(number>=10 && number<100){
            return "0"+number;
        }else{
            return ""+number;
        }
    }

    public static void savePicture(Bitmap bitmap,String pathDestine){

        try{
            File file = new File(pathDestine);
            OutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        }catch (FileNotFoundException e) {
             e.printStackTrace();
        } catch (IOException e) {
             e.printStackTrace();
        }
    }


    public static Bitmap cropImage(File fileImage,SharedPreferences sharedPreferences,ExifInterface exifInterface,File fileDestino) {

        int max_width=sharedPreferences.getInt("SCREEN_WIDTH",150);
        int max_height=sharedPreferences.getInt("SCREEN_HEIGHT",150);
        System.out.println("********Max with=>"+max_width+"/"+max_height);
        System.out.println("********Tamaño de imagen=>"+exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));

        if(Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH))< max_width){
            max_width=Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));

        }

        max_height=max_width/2;
        Bitmap unscaledBitmap = decodeResource( max_width, max_width,fileImage,exifInterface);


        Bitmap scaledBitmap = createScaledBitmap(unscaledBitmap, max_width, max_width);
        unscaledBitmap.recycle();



        savePicture(scaledBitmap,fileDestino.getPath());
        return scaledBitmap;
    }

    public static Bitmap decodeResource( int dstWidth, int dstHeight,File fileImage,ExifInterface exifInterface) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileImage.getPath(), options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth,dstHeight);
        Bitmap unscaledBitmap = BitmapFactory.decodeFile(fileImage.getPath(), options);

        Matrix matrix = new Matrix();
        System.out.println("***********************ORIENTACION IMAGENN !!!!!!!!!!!=>" + exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
        switch (exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION)){
            case ExifInterface.ORIENTATION_ROTATE_180+"" :{matrix.postRotate(-180);}break;
            case ExifInterface.ORIENTATION_TRANSPOSE+"" :
            case ExifInterface.ORIENTATION_ROTATE_90+"" :
            case ExifInterface.ORIENTATION_TRANSVERSE+"" :{matrix.postRotate(90);}break;
            case ExifInterface.ORIENTATION_ROTATE_270+"" : {matrix.postRotate(270);}break;


        }

        unscaledBitmap = Bitmap.createBitmap(unscaledBitmap, 0, 0, unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), matrix, true);

        return unscaledBitmap;
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                dstWidth, dstHeight);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {
       return new Rect(0, 0, srcWidth, srcHeight);

    }

    public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {

        //return new Rect(0, 0, dstWidth, dstHeight);

        final float srcAspect = (float)srcWidth / (float)srcHeight;
        final float dstAspect = (float)dstWidth / (float)dstHeight;

        if (srcAspect > dstAspect) {
            return new Rect(0, 0, dstWidth, (int)(dstWidth / srcAspect));
        } else {
            return new Rect(0, 0, (int)(dstHeight * srcAspect), dstHeight);
        }

    }

    public static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight) {


        final float srcAspect = (float)srcWidth / (float)srcHeight;
        final float dstAspect = (float)dstWidth / (float)dstHeight;

        if (srcAspect > dstAspect) {
            return srcWidth / dstWidth;
        } else {
            return srcHeight / dstHeight;
        }

    }

    public static Bitmap convertBitmap(String path)   {

        Bitmap bitmap=null;
        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
        bfOptions.inDither=false;                     //Disable Dithering mode
        bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
        bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage=new byte[32 * 1024];


        File file=new File(path);
        FileInputStream fs=null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if(fs!=null) {
                bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally{
            if(fs!=null) {
                try {
                    fs.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }

    public static String appendSepartor(String cadena,String separator){
        if(cadena.length()>0){
            cadena+=separator;
        }


        return cadena;
    }

    public static String nameRamdon(int lengthName){
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < lengthName; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String name = sb.toString();

        return name;
    }

    public static File createFile(String path,String name) throws IOException {

        File exportDir=createFileSystem(path);
        File file = new File(exportDir,name);
        file.createNewFile();

        return file;

    }

    public static File copyDataBase(String nameDB,String path) throws IOException, Exception {

        File dbFile = new File(Environment.getDataDirectory() + ConstanteText.DIRECTORY_DATA_BASE_APP + DaoManager.DATA_BASE);

       // File exportDir=createFileSystem(path);
       // File file = new File(exportDir,nameDB);
       // file.createNewFile();
        File file=createFile(path,nameDB);

        copyFile(dbFile, file);
        return file;

    }

    public static void  copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

    public static Double calculateProgress(Double actual,Double progressByItem ){
        return (actual+progressByItem);
    }



    public static Double calculatePorcentajeByItem(Double total, Integer cantItems) {
        return (total / cantItems) * 1.0;
    }
}
