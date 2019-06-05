package com.uy.csi.sige.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String TAG = "TAG";
    private String DB_PATH = "";
    private String DB_NAME = "";
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DatabaseHelper(Context context, String dbPath, String dbName){
        super(context, dbName, null, 1);
        DB_PATH = dbPath + "/databases/";
        DB_NAME = dbName;
        this.mContext = context;
    }

    public void createDataBase(String dbName) throws IOException{
        boolean mDataBaseExist = checkDataBase();
        Log.d(TAG,"create DB in Helper. Data exists?"+mDataBaseExist);
        if(!mDataBaseExist)
        {
            Log.d(TAG,"get Writable in DatabaseHelper");
            this.getWritableDatabase();
            try
            {
                Log.d(TAG,"copy Database");
                copyDataBase(dbName);
            }
            catch (IOException mIOException)
            {Log.d(TAG,"copy not succeed");
                throw new Error("ErrorCopyingDataBase");

            }
        }
    }

    private boolean checkDataBase()
    {
        SQLiteDatabase mCheckDataBase = null;
        try
        {
            String myPath = "/data/data/com.uy.csi.sige/databases/" + DaoManager.DATA_BASE;
            mCheckDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }
        catch(SQLiteException mSQLiteException)
        {
            Log.e(TAG, "DatabaseNotFound " + mSQLiteException.toString());
        }

        if(mCheckDataBase != null)
        {
            mCheckDataBase.close();
        }
        return mCheckDataBase != null;
    }

    private void copyDataBase(String dbName) throws IOException
    {
        Log.d(TAG,"copy");
        InputStream mInput = mContext.getResources().getAssets().open(DB_NAME);

        String outFileName = DB_PATH + dbName;
        Log.d(TAG,"Output:"+outFileName);
        File createOutFile = new File(outFileName);
        if(!createOutFile.exists()){
            createOutFile.mkdir();
        }
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException{
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.v(TAG, "UpgradingDatabase, This will drop current database and will recreate it");
    }
}
